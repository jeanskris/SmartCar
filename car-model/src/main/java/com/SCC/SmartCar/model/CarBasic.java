package com.SCC.SmartCar.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ZJDX on 2016/7/24.
 */
public class CarBasic implements Serializable{

    private int carId;
    private Date releaseDate;


    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }


}
