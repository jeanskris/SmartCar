package com.SCC.SmartCar.service;

import com.SCC.SmartCar.model.Coordinate;
import com.SCC.SmartCar.model.Map;
import org.springframework.stereotype.Service;

/**
 * Created by ZJDX on 2016/7/25.
 */
@Service
public interface IAutodriveService {
    int createPath(int carId, Coordinate currentPositState, Coordinate end, Map map);
}
