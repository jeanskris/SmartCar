package com.SCC.SmartCar.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Repository;

/**
 * Created by ZJDX on 2016/7/26.
 */
@Repository
public class RedisMessageListener implements MessageListener {

    public void onMessage(final Message message, final byte[] pattern ) {
        JSONObject json=null;
        CarRuntimeInfo carRuntimeInfo=null;
        try {
            json = new JSONObject(message.toString());ObjectMapper mapper = new ObjectMapper();
            carRuntimeInfo= mapper.readValue(json.toString(),CarRuntimeInfo.class);
        }catch (Exception e){
            e.printStackTrace();
        }
       // System.out.println( "Message received:"+  message.toString() );
    }
}

