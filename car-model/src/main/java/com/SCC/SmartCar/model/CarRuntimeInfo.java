package com.SCC.SmartCar.model;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ZJDX on 2016/7/24.
 */
@Repository
public class CarRuntimeInfo implements Serializable{

    private int carId;
    private Coordinate coordinate;
    private double speed;
    private double acceleration;
    private double angel;
    private Date currentTime;
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public double getAngel() {
        return angel;
    }

    public void setAngel(double angel) {
        this.angel = angel;
    }



}
