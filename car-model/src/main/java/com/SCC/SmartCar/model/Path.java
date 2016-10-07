package com.SCC.SmartCar.model;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZJDX on 2016/7/27.
 */
@Repository
public class Path implements Serializable {
    private int PathId;
    private int carId;
    private List<Coordinate> points;
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getPathId() {
        return PathId;
    }

    public void setPathId(int pathId) {
        PathId = pathId;
    }

    public List<Coordinate> getPoints() {
        return points;
    }

    public void setPoints(List<Coordinate> points) {
        this.points = points;
    }
    public String toString(){
        return  "carId:"+this.getCarId()+"PathId:"+this.getPathId();

    }

}
