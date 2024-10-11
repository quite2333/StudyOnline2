package com.studyonline2.www.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyonline2.www.dto.UserDTO;
import com.studyonline2.www.exception.BusinessException;
import com.studyonline2.www.mapper.MatchMapper;
import com.studyonline2.www.model.domain.Match;
import com.studyonline2.www.model.domain.Score;
import com.studyonline2.www.model.domain.User;
import com.studyonline2.www.service.MatchService;
import com.studyonline2.www.service.ScoreService;
import com.studyonline2.www.service.UserService;
import com.studyonline2.www.utils.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.service.impl
 * @ClassName=MatchServiceImpl
 * @Description:
 * @date 2024/10/11 12:06
 * @jdk version used: JDK1.8
 **/
@Service
@Slf4j
public class MatchServiceImpl extends ServiceImpl<MatchMapper, Match> implements MatchService {
    @Resource
    private ScoreService scoreService;
    @Resource
    private UserService userService;

    @Override
    public Boolean cancel(Integer userid) {
        boolean ans = this.remove(new LambdaQueryWrapper<Match>()
                .eq(Match::getId, userid)
        );
        return ans;
    }

    @Override
    public HashMap match(Integer userid, Integer times) {
        Score userScore = scoreService.getScore(userid);
        if (userScore == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Long count = this.lambdaQuery()
                .eq(Match::getId, userid)
                .count();
        if (count == 0) {
            this.save(new Match(userid, userScore.getScore(), times, 0));
        }
        HashMap ans = asyncMatch(userid, userScore, times);
        return ans;
    }

    //    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public HashMap asyncMatch(Integer userid, Score userScore, Integer times) {
        int i = 1;
        boolean matched = false;
        HashMap<String, Object> map = MapUtil.newHashMap();
        Match match = null;
        boolean status;
        while (!matched && i < 30) {
            Match thisMatch = this.lambdaQuery()
                    .eq(Match::getId, userid)
                    .last("LOCK IN SHARE MODE")
                    .one();
            if (thisMatch == null) {
                break;
            }
            match = this.lambdaQuery()
                    .eq(Match::getTargetid, userid)
                    .one();
            if (match != null) {
                status = this.lambdaUpdate()
                        .eq(Match::getId, userid)
                        .set(Match::getTargetid, match.getId())
                        .update();
                if (!status) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            } else {
//                查找机制实现
                match = this.lambdaQuery()
                        .ne(Match::getId, userid)
                        .eq(Match::getTargetid, 0)
                        .le(Match::getScore, userScore.getScore() + 10 * i)
                        .ge(Match::getScore, userScore.getScore() - 10 * i)
                        .last("FOR UPDATE")
                        .one();

                if (match != null) {
                    status = this.lambdaUpdate()
                            .eq(Match::getId, userid)
                            .set(Match::getTargetid, match.getId())
                            .update();
                    if (!status) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                }
            }
            i++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        if (match != null) {
            Map<String, Object> targetMatchMap = BeanUtil.beanToMap(match);
            User user = userService.searchUser(String.valueOf(match.getId()));
            UserDTO safeUser = UserDTO.getSafeUser(user);
            Map<String, Object> targetUserInfo = BeanUtil.beanToMap(safeUser);
            map.put("targetMatch", targetMatchMap);
            map.put("targetUserInfo", targetUserInfo);
        }else{
            cancel(userid);
        }
        return map;
    }
}
