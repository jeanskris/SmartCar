package com.SCC.SmartCar;

import com.SCC.SmartCar.model.Map;
import com.sun.xml.internal.bind.v2.runtime.Coordinator;
import org.springframework.stereotype.Service;

/**
 * Created by ZJDX on 2016/7/25.
 */
@Service
public class AutodriveServiceImpl implements IAutodriveService {
    public void createPath(Coordinator start,Coordinator end,Map map){
        System.out.println("createPath!");

    }
}
