package com.SCC.SmartCar.model;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by ZJDX on 2016/7/26.
 */
@Repository
public class RedisPublisher {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /*发布消息到Channel*/
    public void Publish(String channelTopic,Serializable message) throws IOException {
        /*String str1=MessageUtil.convertToString(channelTopic);
        String str2=MessageUtil.convertToString(message);*/
        //不进行下面处理就会有乱码问题
        String jsonMessage = "";
        if (message.getClass().isAssignableFrom(String.class)) {
            jsonMessage = (String) message;
        } else {
            try {
                jsonMessage = new JSONObject(message).toString();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        stringRedisTemplate.convertAndSend(channelTopic,jsonMessage );
    }

}
