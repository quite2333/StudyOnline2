package com.studyonline2.www.controller;

import com.studyonline2.www.exception.BusinessException;
import com.studyonline2.www.model.domain.Plan;
import com.studyonline2.www.model.domain.request.PlanRequest;
import com.studyonline2.www.service.PlanService;
import com.studyonline2.www.utils.BaseResponse;
import com.studyonline2.www.utils.ErrorCode;
import com.studyonline2.www.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.controller
 * @ClassName=PlanController
 * @Description:
 * @date 2024/10/9 16:50
 * @jdk version used: JDK1.8
 **/
@RestController
@ResponseBody
@RequestMapping("/plan")
public class PlanController {
    @Resource
    private PlanService planService;

    @PutMapping
    public BaseResponse<Boolean> addplan(@RequestBody PlanRequest planRequest, HttpServletRequest request) {
        if (planRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Plan plan = new Plan(planRequest);
        Boolean ans = planService.add(plan);
        if (!ans) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(ans);
    }

    @PostMapping("/showHistoryPlans")
    public BaseResponse<List<Plan>> showHistoryPlans(@RequestBody HashMap params, HttpServletRequest request) {
        String userid = (String) params.get("userid");
        if (StringUtils.isBlank(userid)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer id = Integer.parseInt(userid);
        List<Plan> plans = planService.showHistoryPlans(id);
        return ResultUtils.success(plans);
    }
}
