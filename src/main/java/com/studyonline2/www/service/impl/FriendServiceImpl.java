package com.studyonline2.www.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyonline2.www.dto.UserDTO;
import com.studyonline2.www.exception.BusinessException;
import com.studyonline2.www.mapper.FriendMapper;
import com.studyonline2.www.mapper.UserMapper;
import com.studyonline2.www.model.domain.Friend;
import com.studyonline2.www.model.domain.User;
import com.studyonline2.www.service.FriendService;
import com.studyonline2.www.utils.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.service.impl
 * @ClassName=FriendServiceImpl
 * @Description:
 * @date 2024/10/8 17:07
 * @jdk version used: JDK1.8
 **/
@Service
@Slf4j
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {
    @Resource
    private UserMapper userMapper;

    /**
     * 若对方已申请，则成立好友关系
     * 若我方已申请
     * 若不为好友关系，则重置申请
     * 否则新建申请
     *
     * @param userid
     * @param addid
     * @return
     */
    @Override
    public Boolean apply(Integer userid, Integer addid) {
        if (userid <= 0 || addid <= 0 || userid == addid) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean ans = Boolean.FALSE;
        Friend one = this.lambdaQuery()
                .eq(Friend::getId1, addid)
                .eq(Friend::getId2, userid)
                .one();
        if (one != null) {
            ans = this.lambdaUpdate()
                    .eq(Friend::getId1, addid)
                    .eq(Friend::getId2, userid)
                    .set(Friend::getFlag, 1)
                    .update();
            return ans;
        }
        one = this.lambdaQuery()
                .eq(Friend::getId1, userid)
                .eq(Friend::getId2, addid)
                .one();
        if (one != null) {
            if (one.getFlag() != 1) {
                ans = this.lambdaUpdate()
                        .eq(Friend::getId1, addid)
                        .eq(Friend::getId2, userid)
                        .set(Friend::getFlag, 0)
                        .update();
            }
        } else {
            Friend friend = new Friend(userid, addid, 0);
            ans = this.save(friend);
        }
        return ans;
    }

    /**
     * @param userid
     * @param applyid
     * @return
     */
    @Override
    public Boolean agree(Integer userid, Integer applyid) {
        if (userid <= 0 || applyid <= 0 || userid == applyid) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean ans = this.lambdaUpdate()
                .eq(Friend::getId1, applyid)
                .eq(Friend::getId2, userid)
                .set(Friend::getFlag, 1)
                .update();
        return ans;
    }

    /**
     * @param userid
     * @param applyid
     * @return
     */
    @Override
    public Boolean refuse(Integer userid, Integer applyid) {
        if (userid <= 0 || applyid <= 0 || userid == applyid) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean ans = this.lambdaUpdate()
                .eq(Friend::getId1, applyid)
                .eq(Friend::getId2, userid)
                .set(Friend::getFlag, -1)
                .update();
        return ans;
    }

    /**
     * @param userid
     * @param deleteid
     * @return
     */
    @Override
    public Boolean delete(Integer userid, Integer deleteid) {
        if (userid <= 0 || deleteid <= 0 || userid == deleteid) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<Friend> wrapper = new LambdaQueryWrapper<>();
        boolean ans = this.remove(wrapper
                .eq(Friend::getId1, userid)
                .eq(Friend::getId2, deleteid)
                .or()
                .eq(Friend::getId1, deleteid)
                .eq(Friend::getId2, userid)
        );
        return ans;
    }

    /**获取好友申请数量
     * @param userid
     * @return
     */
    @Override
    public Long getApplyNum(Integer userid) {
        if (userid <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long ans = this.lambdaQuery()
                .eq(Friend::getId2, userid)
                .eq(Friend::getFlag, 0)
                .count();
        return ans;
    }

    /**
     * @param userid
     * @return
     */
    @Override
    public List<UserDTO> getfriends(Integer userid) {
        if (userid <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Friend> friendList = this.lambdaQuery()
                .eq(Friend::getFlag, 1)
                .and(wrapper -> wrapper
                        .eq(Friend::getId1, userid)
                        .or()
                        .eq(Friend::getId2, userid))
                .list();
        List<Integer> friendids = friendList
                .stream()
                .map(friend -> friend.getId1().equals(userid) ? friend.getId2() : friend.getId1())
                .collect(Collectors.toList());
        List<UserDTO> users = friendids.stream().map(id -> {
            User user = userMapper.selectById(id);
            UserDTO safeUser = UserDTO.getSafeUser(user);
            return safeUser;
        }).collect(Collectors.toList());

        return users;
    }

    /**
     * @param userid
     * @return
     */
    @Override
    public List<UserDTO> showApplyList(Integer userid) {
        if (userid <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Friend> applyList = this.lambdaQuery()
                .eq(Friend::getId2, userid)
                .eq(Friend::getFlag, 0)
                .list();
        List<Integer> applyids = applyList
                .stream()
                .map(friend -> friend.getId1())
                .collect(Collectors.toList());
        List<UserDTO> users = applyids.stream().map(id -> {
            User user = userMapper.selectById(id);
            UserDTO safeUser = UserDTO.getSafeUser(user);
            return safeUser;
        }).collect(Collectors.toList());
        return users;
    }
}
