package com.SCC.SmartCar.service;

import com.SCC.SmartCar.dao.RedisDao;
import com.SCC.SmartCar.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZJDX on 2016/7/25.
 */
@Service
public class AutodriveService implements IAutodriveService {
    @Autowired
    RedisDao redisDao;
    @Autowired
    CarIoService carIoService;

    public void autoDriving(final Map map, final List<Coordinate> points, final int carId) {
        // determine start and end
        Thread t = new Thread(new Runnable(){
            public void run(){
                double minDIst = -1.0;
                int carid=carId;
                Coordinate start = points.get(0);
                Coordinate end = points.get(1);
                start.setX(start.getX()-map.getMappingX());//沙盘小车接口
                start.setY(start.getY()-map.getMappingY());//沙盘小车接口
                end.setX(end.getX()-map.getMappingX());//沙盘小车接口
                end.setY(end.getY()-map.getMappingY());//沙盘小车接口
                double initDIst = Math.sqrt(Math.pow(start.getX() - end.getX(), 2) + Math.pow(start.getY() - end.getY(), 2));
                // record time and stop after 5s!!!
                carIoService.sendAutoJsonCommandToTestCar(carId,"command", 0, 1);  // forward command
                //while(true) {
                    // get car current position
                    CarRuntimeInfo carRuntimeInfo=(CarRuntimeInfo)redisDao.LIndex("carRuntimeInfo_list:"+"1",0);
                    Coordinate curPos=carRuntimeInfo.getCoordinate();
                try {
                    Thread.sleep(10000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                carIoService.sendAutoJsonCommandToTestCar(carid, "command",0, 0);  // stop command
                    // compare current position with target position && send command
                    double curDist = Math.sqrt(Math.pow(curPos.getX() - end.getX(), 2) + Math.pow(curPos.getY() - end.getY(), 2));
                    if (minDIst != -1 && minDIst < initDIst * 0.3) {
                        carIoService.sendAutoJsonCommandToTestCar(carid, "command",0, 0);  // stop command
                        //break;
                    } else {
                        minDIst = curDist;
                        //carIoService.sendAutoJsonCommandToTestCar(carId, 0, 1);  // forward command
                    }

                //}
            }
        });
        t.start();
        System.out.println("auto driving sucessfully");
    }
    public int createPath(int carId, Coordinate currentPositState, Coordinate end, Map map){
        Path path=new Path();
        path.setCarId(carId);
        path.setPathId(1);
        List<Coordinate> points=new ArrayList<Coordinate>();
       // double tan=Math.tan(currentPositState.getAngel());
        Coordinate startCoordinate=new Coordinate();
        startCoordinate.setX(currentPositState.getX());
        startCoordinate.setY(currentPositState.getY());
        Coordinate endCoordinate=new Coordinate();
        endCoordinate.setX(end.getX());
        endCoordinate.setY(end.getY());
        Double distX=(endCoordinate.getX()-startCoordinate.getX())/64;
        Double distY=(endCoordinate.getY()-startCoordinate.getY())/64;
        Coordinate coordinate=new Coordinate();
        coordinate.setX(currentPositState.getX());
        coordinate.setY(currentPositState.getY());
        for(int i=64;i>0;i--){
            coordinate.setX(coordinate.getX() + distX);
            coordinate.setY(coordinate.getY() + distY);
            Coordinate co=new Coordinate();
            co.setX(coordinate.getX());
            co.setY(coordinate.getY());
            points.add(co);
        }
       /* int len=200;
        for(int i=0;i<len;i++){
            coordinate.setX(coordinate.getX() + 1);//startoordinate
            coordinate.setY(coordinate.getY() - 1);
            Coordinate co=new Coordinate();
            co.setX(coordinate.getX());
            co.setY(coordinate.getY());
            points.add(co);*/

            //矩形闭环
            /*if(i<len/4) {
                coordinate.setX(coordinate.getX());
                coordinate.setY(coordinate.getY() + 1);
                Coordinate co=new Coordinate();//不新建对象的话，下面每次对coordinate的更改都会更改前面已存入的数据，因为add的是引用。
                co.setX(coordinate.getX());
                co.setY(coordinate.getY());
                points.add(co);
            }else if(i>=len/4&&i<len/2){
                coordinate.setX(coordinate.getX() + 1);
                coordinate.setY(coordinate.getY());
                Coordinate co=new Coordinate();
                co.setX(coordinate.getX());
                co.setY(coordinate.getY());
                points.add(co);
            }else if(i>=len/2&&i<(len-len/4)){
                coordinate.setX(coordinate.getX());
                coordinate.setY(coordinate.getY() - 1);
                Coordinate co=new Coordinate();
                co.setX(coordinate.getX());
                co.setY(coordinate.getY());
                points.add(co);
            }else if(i>=(len-len/4)&&i<len){
                coordinate.setX(coordinate.getX() - 1);
                coordinate.setY(coordinate.getY());
                Coordinate co=new Coordinate();
                co.setX(coordinate.getX());
                co.setY(coordinate.getY());
                points.add(co);
            }*/
        //}
        path.setPoints(points);
        redisDao.savePath(String.valueOf(carId),path);
        Path readPath=(Path)redisDao.readPath(String.valueOf(carId));
        System.out.println(readPath.toString());
        System.out.println("createPath!");
        System.out.println("test!");
       // carIoService.sendAutoJsonCommand(carId, IOConstants.FORWARD_MANUAL);//不用于沙盘
    return path.getPathId();
    }

}
