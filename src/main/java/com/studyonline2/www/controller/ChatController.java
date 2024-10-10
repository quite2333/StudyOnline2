package com.studyonline2.www.controller;

import com.studyonline2.www.exception.BusinessException;
import com.studyonline2.www.model.domain.Message;
import com.studyonline2.www.model.domain.Plan;
import com.studyonline2.www.service.ChatService;
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
 * @ClassName=ChatController
 * @Description:
 * @date 2024/10/11 0:42
 * @jdk version used: JDK1.8
 **/
@RestController
@ResponseBody
@RequestMapping("/chat")
public class ChatController {
    @Resource
    private ChatService chatService;
    @PostMapping("/showHistoryPlans")
    public BaseResponse<List<Message>> showHistoryPlans(@RequestBody HashMap params, HttpServletRequest request) {
        String fromUserid = (String) params.get("fromUserid");
        String toUserid = (String) params.get("toUserid");
        String minMsgid = (String) params.get("minMsgId");
        if (StringUtils.isAnyBlank(fromUserid,toUserid,minMsgid)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        List<Message> messages = chatService.getHistoryMessages(Integer.parseInt(fromUserid),Integer.parseInt(toUserid),Integer.parseInt(minMsgid));
        return ResultUtils.success(messages);
    }
}
