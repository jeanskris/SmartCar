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
    int left;
    int right;
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

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }


}
