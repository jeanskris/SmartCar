package com.SCC.SmartCar.model;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZJDX on 2016/7/4.
 */
@Repository
public class Map implements Serializable {


      int id;
      List<Coordinate> points;

      double mappingX;//实际地图与存储地图的x差值,用于接收定位点时进行相同的映射处理。
      double mappingY;

      public List<Coordinate> getPoints() {
            return points;
      }
      public int getId() {
            return id;
      }

      public void setId(int id) {
            this.id = id;
      }
      public void setPoints(List<Coordinate> points) {
            this.points = points;
      }


      public double getMappingX() {
            return mappingX;
      }

      public void setMappingX(double mappingX) {
            this.mappingX = mappingX;
      }

      public double getMappingY() {
            return mappingY;
      }

      public void setMappingY(double mappingY) {
            this.mappingY = mappingY;
      }


}