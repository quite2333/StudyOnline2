package com.studyonline2.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyonline2.www.model.domain.Match;

import java.util.HashMap;

public interface MatchService extends IService<Match> {
    Boolean cancel(Integer userid);

    HashMap match(Integer userid, Integer times);
}
