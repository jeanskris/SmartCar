package com.SCC.SmartCar.service;

import com.SCC.SmartCar.dao.RedisDao;
import com.SCC.SmartCar.mina.SessionMap;
import com.SCC.SmartCar.model.*;
import org.apache.mina.core.session.IoSession;
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

    public void autoDriving(final Map map, final List<Coordinate> trajectory, final int carId) {
        Thread t = new Thread(new Runnable(){
            public void run(){
                double threshold = 10; //纠偏允许误差，暂时为1
                System.out.println("trajectory is:" + trajectory);
                SessionMap sessionMap=SessionMap.getInstance();

                while(true) {
                    IoSession session=sessionMap.getSessionByAttribute("carId",carId);
                    if(session==null)break;
                    // get car current position
                    // 轨迹是否已经经过处理？轨迹点的参考系应该与定位点的参考系保持一致
                    CarRuntimeInfo carRuntimeInfo = (CarRuntimeInfo)redisDao.LIndex("carRuntimeInfo_list:"+"1",0);
                    Coordinate point = carRuntimeInfo.getCoordinate();
                    point.setX(point.getX() - map.getMappingX());
                    point.setY(point.getY() - map.getMappingY());
                    carRuntimeInfo.setCoordinate(point);
                    Coordinate nextPoint = findNextPoint(carRuntimeInfo.getCoordinate(), trajectory);

                    System.out.println("cur point is: [" + point.getX() + " , " + point.getY() +
                            "], next point is: [" + nextPoint.getX() + " , " + nextPoint.getY() + "]");

                    if (nextPoint == trajectory.get(trajectory.size() - 1)) {
                        if (finalControl(session, carRuntimeInfo, nextPoint, threshold)) break;
                    } else {
                        lateralControl(session, carRuntimeInfo, nextPoint);
                    }

                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        System.out.println("auto driving sucessfully");
    }

    public boolean lateralControl(IoSession session, CarRuntimeInfo info, Coordinate nextPoint) {
        Coordinate curPoint = info.getCoordinate();
        double nextAngle;
        // !!! map: up increases y, right increases x
        // angle to next point before
        if (nextPoint.getY() < curPoint.getY()) {
            nextAngle = Math.atan(Math.abs((curPoint.getY() - nextPoint.getY()) / (curPoint.getX() - nextPoint.getX())));
            nextAngle = nextAngle * 180 / Math.PI;
            nextAngle = nextPoint.getX() < curPoint.getX()? - nextAngle - 90: nextAngle + 90;
        } else {
            nextAngle = Math.atan((curPoint.getX() - nextPoint.getX()) / (curPoint.getY() - nextPoint.getY()));
            nextAngle = nextAngle * 180 / Math.PI;
        }
        // angle to next point after calculating self-angle
        double diffAngle = info.getAngel() - nextAngle;
        System.out.println("info angle: " + info.getAngel() +
                ",next angle:" + nextAngle +
                ", diff angle: " + diffAngle);
        // send command
        int speed = 2;
        carIoService.sendAutoJsonCommandToTestCarV2(session, "command", (int)diffAngle, speed);


        return true;
    }

    public boolean finalControl(IoSession session, CarRuntimeInfo info, Coordinate nextPoint, double threshold) {
        if (calcDist(info.getCoordinate(), nextPoint) < threshold) {
            carIoService.sendAutoJsonCommandToTestCarV2(session, "command", 0, 0);
            return true;
        } else {
            lateralControl(session, info, nextPoint);
            return false;
        }
    }

    public Coordinate findNextPoint(Coordinate curPoint, List<Coordinate> trajectory){
        double minDist = Double.MAX_VALUE;
        int minIndex = 0;

        for (int i = 0; i < trajectory.size(); i++) {
            Coordinate refPoint = trajectory.get(i);
            double dist = calcDist(curPoint, refPoint);
            if(dist < minDist){
                minDist = dist;
                minIndex = i;
            }
        }

        if (minIndex == trajectory.size() - 1) {
            return trajectory.get(trajectory.size() - 1);
        }
        else {
            return trajectory.get(minIndex + 1);
        }
    }


    public double calcDist(Coordinate start, Coordinate end) {
        // in the order of 1 center-meter
        return Math.sqrt(Math.pow(start.getX() - end.getX(), 2) + Math.pow(start.getY() - end.getY(), 2));
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
