package com.studyonline2.www.websocket;

import cn.hutool.json.JSONObject;
import com.studyonline2.www.exception.BusinessException;
import com.studyonline2.www.model.domain.Message;
import com.studyonline2.www.service.ChatService;
import com.studyonline2.www.utils.ErrorCode;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.websocket
 * @ClassName=Server
 * @Description:
 * @date 2024/10/9 18:16
 * @jdk version used: JDK1.8
 **/
@Component
@ServerEndpoint(value = "/chat/{userid}", encoders = {ServerEncoder.class})
public class Server {
    private Session session;
    private String userid;
    public static ChatService chatService;

    private Boolean getUnreadMessages(String targetid) throws EncodeException, IOException {
        Boolean ans = Boolean.FALSE;
        Server server = Connections.userServerMap.get(userid);
        List<Message> unreadMessages = chatService.getUnreadMessages(Integer.parseInt(userid), Integer.parseInt(targetid));
        if (!unreadMessages.isEmpty()) {
            JSONObject jsonMessages = new JSONObject(true);
            int num = 0;
            for (Message message : unreadMessages) {
                num++;
                jsonMessages.append("msg" + String.valueOf(num), message);
            }
            jsonMessages.append("total", num);
            server.session.getBasicRemote().sendObject(jsonMessages);
            ans = chatService.updateMessages(unreadMessages);
            if (!ans) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }

        }
        return ans;
    }


    @OnOpen
    public void open(Session session, @PathParam("userid") String userid) throws EncodeException, IOException {
        this.session = session;
        this.userid = userid;
        Map<String, List<String>> params = session.getRequestParameterMap();
        List<String> strings = params.get("targetid");
        Connections.userServerMap.put(userid, this);
        if (!(strings == null && strings.isEmpty())) {
            String targetid = strings.get(0);
            System.out.println("User connected: " + userid + " to " + targetid);
            getUnreadMessages(targetid);
        }

    }

    @OnMessage
    public void message(String messageJson, @PathParam("userid") String userid) throws EncodeException, IOException {
        JSONObject jsonObject = new JSONObject(messageJson);
        String targetid = jsonObject.getStr("targetid");
        String message = jsonObject.getStr("message");
        Boolean ans = chatService.addMessage(Integer.parseInt(userid), Integer.parseInt(targetid), message);
        if (!ans) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        getUnreadMessages(targetid);
    }

    @OnClose
    public void close() {
        System.out.println("Closing connection for user: " + userid);
        Connections.userServerMap.remove(userid);
    }

    @OnError
    public void error(Throwable t) {
        System.err.println("WebSocket error: " + t.getMessage());
    }
}
