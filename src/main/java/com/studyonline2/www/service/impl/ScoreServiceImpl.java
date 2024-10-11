package com.studyonline2.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyonline2.www.exception.BusinessException;
import com.studyonline2.www.mapper.ScoreMapper;
import com.studyonline2.www.model.domain.Score;
import com.studyonline2.www.service.ScoreService;
import com.studyonline2.www.utils.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.service.impl
 * @ClassName=ScoreServiceImpl
 * @Description:
 * @date 2024/10/11 14:13
 * @jdk version used: JDK1.8
 **/
@Service
@Slf4j
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {
    @Override
    public Score getScore(Integer userid) {
        Score score = this.lambdaQuery()
                .eq(Score::getId, userid)
                .one();
        if (score==null){
            score = new Score(userid, 1200);
            this.save(score);
        }
        return score;
    }

}
