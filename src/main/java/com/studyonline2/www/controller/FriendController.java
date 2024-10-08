package com.studyonline2.www.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.studyonline2.www.dto.UserDTO;
import com.studyonline2.www.exception.BusinessException;
import com.studyonline2.www.service.FriendService;
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
 * @ClassName=FriendController
 * @Description:
 * @date 2024/10/8 16:14
 * @jdk version used: JDK1.8
 **/
@RestController
@ResponseBody
@RequestMapping("/friend")
public class FriendController {
    @Resource
    private FriendService friendService;

    @PutMapping
    public BaseResponse<Boolean> friendApply(@RequestBody HashMap params, HttpServletRequest request) {
        String useridStr = (String) params.get("userid");
        String addidStr = (String) params.get("addid");
        if (StringUtils.isAnyBlank(useridStr, addidStr)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer userid = Integer.parseInt(useridStr);
        Integer addid = Integer.parseInt(addidStr);
        Boolean ans = friendService.apply(userid, addid);
        if (!ans) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(ans);
    }

    @PostMapping("/agree")
    public BaseResponse<Boolean> agreeApply(@RequestBody HashMap params, HttpServletRequest request) {
        String useridStr = (String) params.get("userid");
        String applyidStr = (String) params.get("applyid");
        if (StringUtils.isAnyBlank(useridStr, applyidStr)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer userid = Integer.parseInt(useridStr);
        Integer applyid = Integer.parseInt(applyidStr);
        Boolean ans = friendService.agree(userid, applyid);
        if (!ans) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(ans);
    }

    @PostMapping("/refuse")
    public BaseResponse<Boolean> refuseApply(@RequestBody HashMap params, HttpServletRequest request) {
        String useridStr = (String) params.get("userid");
        String applyidStr = (String) params.get("applyid");
        if (StringUtils.isAnyBlank(useridStr, applyidStr)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer userid = Integer.parseInt(useridStr);
        Integer applyid = Integer.parseInt(applyidStr);
        Boolean ans = friendService.refuse(userid, applyid);
        if (!ans) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(ans);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteFriend(@RequestBody HashMap params, HttpServletRequest request) {
        String useridStr = (String) params.get("userid");
        String deleteidStr = (String) params.get("deleteid");
        if (StringUtils.isAnyBlank(useridStr, deleteidStr)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer userid = Integer.parseInt(useridStr);
        Integer deleteid = Integer.parseInt(deleteidStr);
        Boolean ans = friendService.delete(userid, deleteid);
        if (!ans) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(ans);
    }

    @GetMapping("/showApplyNum")
    public BaseResponse<Long> showApplyNum(@RequestBody HashMap params, HttpServletRequest request) {
        String useridStr = (String) params.get("userid");
        if (StringUtils.isBlank(useridStr)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer userid = Integer.parseInt(useridStr);
        Long ans = friendService.getApplyNum(userid);
        return ResultUtils.success(ans);
    }

    @PostMapping("/showFriends")
    public BaseResponse<List<UserDTO>> showFriends(@RequestBody HashMap params, HttpServletRequest request) {
        String useridStr = (String) params.get("userid");
        if (StringUtils.isBlank(useridStr)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer userid = Integer.parseInt(useridStr);
        List<UserDTO> users = friendService.getfriends(userid);
        return ResultUtils.success(users);
    }

    @PostMapping("/showApplyList")
    public BaseResponse<List<UserDTO>> showApplyList(@RequestBody HashMap params, HttpServletRequest request) {
        String useridStr = (String) params.get("userid");
        if (StringUtils.isBlank(useridStr)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer userid = Integer.parseInt(useridStr);
        List<UserDTO> users = friendService.showApplyList(userid);
        return ResultUtils.success(users);
    }
}
