package com.SCC.SmartCar.model;

import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by ZJDX on 2016/7/28.
 */
@Repository
public class Command implements Serializable{
    String mode;
    byte[] data;
    String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

}
