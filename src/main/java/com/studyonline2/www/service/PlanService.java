package com.studyonline2.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyonline2.www.model.domain.Plan;

import java.util.List;

public interface PlanService extends IService<Plan> {
    Boolean add(Plan plan);

    List<Plan> showHistoryPlans(Integer userid);
}
