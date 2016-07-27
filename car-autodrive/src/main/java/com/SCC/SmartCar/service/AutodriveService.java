package com.SCC.SmartCar.service;

import com.SCC.SmartCar.dao.RedisDao;
import com.SCC.SmartCar.model.Coordinate;
import com.SCC.SmartCar.model.Map;
import com.SCC.SmartCar.model.Path;
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

    public int createPath(int carId, Coordinate currentPositState, Coordinate end, Map map){
        Path path=new Path();
        path.setCarId(carId);
        path.setPathId(1);
        List<Coordinate> points=new ArrayList<Coordinate>();
       // double tan=Math.tan(currentPositState.getAngel());
        Coordinate coordinate=new Coordinate();
        coordinate.setX(currentPositState.getX());
        coordinate.setY(currentPositState.getY());
        int len=200;
        for(int i=0;i<len;i++){
            coordinate.setX(coordinate.getX() + 1);
            coordinate.setY(coordinate.getY());
            Coordinate co=new Coordinate();
            co.setX(coordinate.getX());
            co.setY(coordinate.getY());
            points.add(co);

            //闭环
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
        }
        path.setPoints(points);
        redisDao.savePath(String.valueOf(carId),path);
        System.out.println("createPath!");
    return path.getPathId();
    }
}
