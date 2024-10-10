package com.studyonline2.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyonline2.www.model.domain.Message;

import java.util.List;

public interface ChatService extends IService<Message> {
    List<Message> getUnreadMessages(Integer userid, Integer targetid);

    Boolean updateMessages(List<Message> unreadMessages);

    Boolean addMessage(Integer userid, Integer targetid, String message);

    List<Message> getHistoryMessages(Integer fromUserid, Integer toUserid, Integer minMsgid);
}
