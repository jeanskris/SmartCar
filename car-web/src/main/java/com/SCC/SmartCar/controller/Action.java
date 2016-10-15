package com.SCC.SmartCar.controller;

import com.SCC.SmartCar.dao.RedisDao;
import com.SCC.SmartCar.model.*;
import com.SCC.SmartCar.model.config.ServerConfig;
import com.SCC.SmartCar.service.CarIoService;
import com.SCC.SmartCar.service.IAutodriveService;
import com.SCC.SmartCar.service.IEnvironmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    IAutodriveService autodriveService;
    @Autowired
    RedisDao redisDao;
    @RequestMapping(value = "/startAutoPark", method = RequestMethod.GET)
    public void startAutoPark(){
        logger.debug("run");
        //carService.sendCommand(1,IOConstants.RUN);//沙盘！！ 不是沙盘去掉注释
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
    @RequestMapping(value = "/getCurrentPosition", method = RequestMethod.GET)
    public Coordinate getCurrentPosition() {
       // System.out.println("mapId:");
       // CarRuntimeInfo carRuntimeInfo=(CarRuntimeInfo)redisDao.LRange("carRuntimeInfo_list:"+"1",0,1).get(0);
        CarRuntimeInfo carRuntimeInfo=(CarRuntimeInfo)redisDao.LIndex("carRuntimeInfo_list:"+"1",0);
        Coordinate coordinate=carRuntimeInfo.getCoordinate();
        HttpRequest hr=new HttpRequest();
        Map map=new Map();
        map=(Map)redisDao.read("mapId:"+1);
        if(redisDao.read("smartCarMapId:"+1)==null) {
            try {
                JSONObject result=hr.sendGet(ServerConfig.ENVIRONMENT_SERVER+"getMap?mapId="+1,"");
                ObjectMapper mapper=new ObjectMapper();
                map= mapper.readValue(result.toString(),Map.class);
                redisDao.save("smartCarMapId:"+1,map);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            map=(Map)redisDao.read("smartCarMapId:"+1);
        }
        coordinate.setX(coordinate.getX()-map.getMappingX());
        coordinate.setY(coordinate.getY()-map.getMappingY());
//        System.out.println("getCurrentPosition:" +coordinate.getX()+ "," + coordinate.getY());
        return coordinate;
    }
    //获取前台设置的起始点和终点，将路径发送给车服务器生成路径并解析成指令
    @RequestMapping(value = "/startAuto", method = RequestMethod.POST)
    public Path startAuto(@RequestBody Trip trip) {
        CarRuntimeInfo carRuntimeInfo=(CarRuntimeInfo)redisDao.LIndex("carRuntimeInfo_list:"+"1",0);//沙盘小车接口
        Coordinate startPoint=carRuntimeInfo.getCoordinate();//沙盘小车接口 用当前位置作为起始位置
        Map map=environmentService.getMap(trip.getMapID());
        startPoint.setX(startPoint.getX()-map.getMappingX());//沙盘小车接口
        startPoint.setY(startPoint.getY()-map.getMappingY());//沙盘小车接口
        int pathId=autodriveService.createPath(trip.getCarId(),startPoint,trip.getEndPoint(),map);//获取路径并返回停车，这部分要迁移到停车应用上。
        Path path=(Path)redisDao.readPath(String.valueOf(trip.getCarId()));
        System.out.println("startAuto path:" +startPoint.toString() + " , " + trip.getEndPoint().toString()+" mapID:"+trip.getMapID());
        System.out.println("startAuto path:"+path.toString());
        List<Coordinate> points=new ArrayList<Coordinate>();
        points.add(startPoint);
        points.add(trip.getEndPoint());
        autodriveService.autoDriving(map,points,trip.getCarId());
        return  path;
    }

}
