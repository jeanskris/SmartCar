package com.SCC.SmartCar.model;

import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by ZJDX on 2016/9/27.
 * 用于沙盘模型小车
 */
@Repository
public class CommandTestCar implements Serializable{
    String mode;
    int direction;
    int speed;
    String time;
    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}
