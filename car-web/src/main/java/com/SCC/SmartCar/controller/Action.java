package com.SCC.SmartCar.controller;

import com.SCC.SmartCar.model.IOConstants;
import com.SCC.SmartCar.model.Map;
import com.SCC.SmartCar.model.ResponseMessage;
import com.SCC.SmartCar.model.Trip;
import com.SCC.SmartCar.service.CarIoService;
import com.SCC.SmartCar.service.IEnvironmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ZJDX on 2016/6/25.
 */
@RestController
public class Action {
    private static Logger logger = LoggerFactory.getLogger(Action.class);

    @Autowired
    CarIoService carService;
    @Autowired
    IEnvironmentService environmentService;

    @RequestMapping(value = "/startAutoPark", method = RequestMethod.GET)
    public void startAutoPark(){
        logger.debug("run");
        carService.sendCommand(1,IOConstants.RUN);
    }

    @RequestMapping(value = "/forward", method = RequestMethod.GET)
    public void forward(){
        logger.debug("forward");
        carService.sendCommand(1,IOConstants.FORWARD);
        try {
            Thread.sleep(200);
        }catch (Exception e){
        System.out.println(e.getMessage().toString());
        }
    }

    //获取前台设置的起始点和终点，将路径发送给车服务器生成路径并解析成指令
    @RequestMapping(value = "/startAuto", method = RequestMethod.POST)
    public ResponseMessage startAuto(@RequestBody Trip trip) {
        Map map=environmentService.getMap(trip.getMapID());


        ResponseMessage responseMessage=new ResponseMessage();
        responseMessage.setMessage("success!");
        System.out.println("path:" + trip.getStartPoint().toString() + "," + trip.getEndPoint().toString()+" mapID:"+trip.getMapID());
        return  responseMessage;
    }

}
