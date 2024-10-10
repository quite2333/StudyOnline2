package com.studyonline2.www.websocket;

import com.studyonline2.www.model.domain.Message;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.websocket
 * @ClassName=MQService
 * @Description:
 * @date 2024/10/9 21:59
 * @jdk version used: JDK1.8
 **/
public class MQService {
    @Resource
    private static RabbitTemplate rabbitTemplate;
    @Resource
    private static RabbitAdmin rabbitAdmin;
    private static String exchangeName = "chat";
    private static DirectExchange directExchange=new DirectExchange(exchangeName, true, false);
    public static Boolean saveMsgToRabbitMQ(Message msg) {
        Integer toUserid = msg.getToUserid();
        Integer fromUserid = msg.getFromUserid();
        String queueName = getQueueName(fromUserid, toUserid);
        if (rabbitAdmin.getQueueProperties(queueName) != null) {
            rabbitTemplate.convertAndSend(queueName, queueName, msg);
        } else {
            createUserDirectQueue(queueName);
            rabbitTemplate.convertAndSend(exchangeName,queueName, msg);
        }
        return true;
    }
    public static void createUserDirectQueue(String queueName) {
        Map<String, Object> params = new HashMap<>();
        //配置死信交换机
        params.put("x-dead-letter-exchange", "UserMsgDirectExchange");
        //设置消息超时时间-默认7天
        params.put("x-message-ttl", 7 * 24 * 60 * 60 * 1000L);
        // 一般设置一下队列的持久化就好,其余两个就是默认false
        Queue qe = new Queue(queueName, false, false, true, params);
        BindingBuilder.bind(qe).to(directExchange).with(queueName);
    }
    public static String getQueueName(Integer fromUserid,Integer toUserid){
        StringBuilder qn=new StringBuilder();
        qn.append("User").append(fromUserid).append("To").append("User").append(toUserid);
        return qn.toString();
    }

    public List<Message> getMessages(Integer fromUserid,Integer toUserid) {
        String queueName = getQueueName(fromUserid, toUserid);
        List<Message> messages = new ArrayList<>();
        Message message;
        while ((message = (Message) rabbitTemplate.receiveAndConvert(queueName)) != null) {
            messages.add(message);
        }
        return messages;
    }

}
