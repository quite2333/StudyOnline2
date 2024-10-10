package com.studyonline2.www.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyonline2.www.exception.BusinessException;
import com.studyonline2.www.mapper.MessageMapper;
import com.studyonline2.www.model.domain.Message;
import com.studyonline2.www.service.ChatService;
import com.studyonline2.www.utils.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.service.impl
 * @ClassName=ChatServiceImpl
 * @Description:
 * @date 2024/10/9 21:23
 * @jdk version used: JDK1.8
 **/
@Service
@Slf4j
public class ChatServiceImpl extends ServiceImpl<MessageMapper, Message> implements ChatService {
    @Override
    public List<Message> getUnreadMessages(Integer userid, Integer targetid) {
        if (userid == null || targetid == null || userid == 0 || targetid == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Message> messageList = this.lambdaQuery()
                .eq(Message::getFromUserid, targetid)
                .eq(Message::getToUserid, userid)
                .eq(Message::getUnreadFlag, 1)
                .list();
        return messageList;
    }
    @Override
    public Boolean updateMessages(List<Message> unreadMessages) {
        Boolean ans = Boolean.TRUE;
        for (Message unreadMessage : unreadMessages) {
            unreadMessage.setUnreadFlag(0);
            ans = this.lambdaUpdate()
                    .eq(Message::getMsgid, unreadMessage.getMsgid())
                    .set(Message::getUnreadFlag, 0)
                    .update() && ans;
            if (!ans) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
        return ans;
    }

    @Override
    public Boolean addMessage(Integer userid, Integer targetid, String message) {
        if (userid == null || targetid == null || userid == 0 || targetid == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(message)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Message msg = new Message();
        msg.setContent(message);
        msg.setCreatetime(new Date());
        msg.setFromUserid(userid);
        msg.setToUserid(targetid);
        msg.setUnreadFlag(1);
        boolean ans = this.save(msg);
        return ans;
    }

    @Override
    public List<Message> getHistoryMessages(Integer fromUserid, Integer toUserid, Integer minMsgid) {
        if (fromUserid == null || toUserid == null || minMsgid == null || fromUserid == 0 || toUserid == 0 || minMsgid ==0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Message> messages = this.lambdaQuery()
                .lt(Message::getMsgid, minMsgid)
                .and(messageLambdaQueryWrapper -> messageLambdaQueryWrapper
                        .eq(Message::getFromUserid, fromUserid)
                        .eq(Message::getToUserid, toUserid)
                        .or()
                        .eq(Message::getFromUserid, toUserid)
                        .eq(Message::getToUserid, fromUserid)
                )
                .orderByDesc(Message::getMsgid)
                .last("limit 10")
                .list();
        return messages;
    }
}
