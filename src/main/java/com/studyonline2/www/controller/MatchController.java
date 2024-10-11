package com.studyonline2.www.controller;

import com.studyonline2.www.exception.BusinessException;
import com.studyonline2.www.service.MatchService;
import com.studyonline2.www.utils.BaseResponse;
import com.studyonline2.www.utils.ErrorCode;
import com.studyonline2.www.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.controller
 * @ClassName=MatchController
 * @Description:
 * @date 2024/10/11 11:36
 * @jdk version used: JDK1.8
 **/
@RestController
@ResponseBody
@RequestMapping("/match")
public class MatchController {
    @Resource
    private MatchService matchService;
    @PutMapping
    public BaseResponse<HashMap> match(@RequestBody HashMap params, HttpServletRequest request){
        String useridStr = (String) params.get("userid");
        String timesStr = (String) params.get("times");
        if (StringUtils.isAnyBlank(useridStr,timesStr)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer userid = Integer.parseInt(useridStr);
        Integer times = Integer.parseInt(timesStr);

        HashMap ans =matchService.match(userid,times);
        return ResultUtils.success(ans);
    }
    @PostMapping("/cancel")
    public BaseResponse<Boolean> cancel(@RequestBody HashMap params, HttpServletRequest request) {
        Boolean ans = Boolean.FALSE;
        String useridStr = (String) params.get("userid");
        if (StringUtils.isBlank(useridStr)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer userid = Integer.parseInt(useridStr);
        ans =matchService.cancel(userid);
        if (!ans){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(ans);
    }

}
