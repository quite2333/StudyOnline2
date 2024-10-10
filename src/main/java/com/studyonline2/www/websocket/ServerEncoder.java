package com.studyonline2.www.websocket;


import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.websocket
 * @ClassName=ServerEncoder
 * @Description:
 * @date 2024/10/9 18:16
 * @jdk version used: JDK1.8
 **/
@Slf4j
public class ServerEncoder implements Encoder.Text<JSONObject> {
    @Override
    public String encode(JSONObject jsonObject) throws EncodeException {
        return jsonObject.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
