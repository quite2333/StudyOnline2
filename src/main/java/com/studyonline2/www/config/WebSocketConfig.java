package com.studyonline2.www.config;

import com.studyonline2.www.service.ChatService;
import com.studyonline2.www.websocket.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.config
 * @ClassName=WebSocketConfig
 * @Description:
 * @date 2024/10/10 22:53
 * @jdk version used: JDK1.8
 **/
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    @Resource
    public void socketUserService(ChatService chatService){
        Server.chatService= chatService;
    }

}
