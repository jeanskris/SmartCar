package com.SCC.SmartCar.model;

import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by ZJDX on 2016/6/20.
 */
@Repository
public class Coordinate implements Serializable {
    private double x;
    private double y;
    public Coordinate(){

    }
    public Coordinate(double x,double y){
        this.x=x;
        this.y=y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString(){
        return  "x:"+this.getX()+"  y:"+this.getY();

    }


}
