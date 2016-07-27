package com.SCC.SmartCar.model;

import org.springframework.stereotype.Repository;

/**
 * Created by ZJDX on 2016/7/25.
 */
@Repository
public class Trip {
    private int MapID;
    private int carId;
    private Coordinate startPoint;
    private Coordinate endPoint;
    public int getMapID() {
        return MapID;
    }

    public void setMapID(int mapID) {
        MapID = mapID;
    }
    public Coordinate getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Coordinate endPoint) {
        this.endPoint = endPoint;
    }

    public Coordinate getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Coordinate startPoint) {
        this.startPoint = startPoint;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
    public String toString(){
        return  "carId:"+this.getCarId()+"mapID:"+this.getMapID()+"startPoint:"+startPoint.getX()+" "+startPoint.getY()+" endPoint:"+endPoint.getX()+" "+endPoint.getY();

    }
}
