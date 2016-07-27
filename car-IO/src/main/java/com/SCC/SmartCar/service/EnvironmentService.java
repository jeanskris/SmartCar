package com.SCC.SmartCar.service;

import com.SCC.SmartCar.model.HttpRequest;
import com.SCC.SmartCar.model.Map;
import com.SCC.SmartCar.model.config.ServerConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Created by ZJDX on 2016/7/25.
 */
@Service
public class EnvironmentService implements IEnvironmentService {
    public Map getMap(int mapid){
        HttpRequest hr=new HttpRequest();
        Map map=null;
        try {

            ObjectMapper mapper = new ObjectMapper();
            JSONObject jsonObject=hr.sendGet(ServerConfig.ENVIRONMENT_SERVER+"getMap?mapId="+mapid,"");
            map= mapper.readValue(jsonObject.toString(),Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }
}