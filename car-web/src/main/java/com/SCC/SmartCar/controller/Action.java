package com.SCC.SmartCar.controller;

import com.SCC.SmartCar.dao.RedisDao;
import com.SCC.SmartCar.model.*;
import com.SCC.SmartCar.service.CarIoService;
import com.SCC.SmartCar.service.IAutodriveService;
import com.SCC.SmartCar.service.IEnvironmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "/getCurrentPosition", method = RequestMethod.GET)
    public Coordinate mouseClickPoint() {
       // System.out.println("mapId:");
       // CarRuntimeInfo carRuntimeInfo=(CarRuntimeInfo)redisDao.LRange("carRuntimeInfo_list:"+"1",0,1).get(0);
        CarRuntimeInfo carRuntimeInfo=(CarRuntimeInfo)redisDao.LIndex("carRuntimeInfo_list:"+"1",0);
        Coordinate coordinate=carRuntimeInfo.getCoordinate();
        Map map=(Map)redisDao.read("mapId:"+1);//AutoPark服务器获取当前地图Id，然后将Id发送给这儿请求对应的mapping。
        coordinate.setX(coordinate.getX()-map.getMappingX());
        coordinate.setY(coordinate.getY()-map.getMappingY());
        return coordinate;
    }
    //获取前台设置的起始点和终点，将路径发送给车服务器生成路径并解析成指令
    @RequestMapping(value = "/startAuto", method = RequestMethod.POST)
    public Path startAuto(@RequestBody Trip trip) {
        Map map=environmentService.getMap(trip.getMapID());
        int pathId=autodriveService.createPath(trip.getCarId(),trip.getStartPoint(),trip.getEndPoint(),map);
        Path path=(Path)redisDao.readPath(String.valueOf(trip.getCarId()));
        List<Coordinate> points =path.getPoints();
        System.out.println("path:" + trip.getStartPoint().toString() + "," + trip.getEndPoint().toString()+" mapID:"+trip.getMapID());
        return  path;
    }

}
