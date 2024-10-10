package com.studyonline2.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyonline2.www.mapper.PlanMapper;
import com.studyonline2.www.model.domain.Plan;
import com.studyonline2.www.service.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.service.impl
 * @ClassName=PlanServiceImpl
 * @Description:
 * @date 2024/10/9 18:00
 * @jdk version used: JDK1.8
 **/
@Service
@Slf4j
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements PlanService {
    /**
     * @param plan
     * @return
     */
    @Override
    public Boolean add(Plan plan) {
        boolean ans = this.save(plan);
        return ans;
    }

    /**
     * @param userid
     * @return
     */
    @Override
    public List<Plan> showHistoryPlans(Integer userid) {
        List<Plan> planList = this.lambdaQuery()
                .eq(Plan::getUserid, userid)
                .list();
        return planList;
    }
}
