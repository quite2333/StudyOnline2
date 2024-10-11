package com.studyonline2.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyonline2.www.mapper.ScoreMapper;
import com.studyonline2.www.model.domain.Score;

public interface ScoreService extends IService<Score> {
    Score getScore(Integer userid);

}
