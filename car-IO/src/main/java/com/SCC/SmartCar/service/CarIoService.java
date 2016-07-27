package com.SCC.SmartCar.service;

import com.SCC.SmartCar.mina.SessionMap;
import com.SCC.SmartCar.utils.Utils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by ZJDX on 2016/7/24.
 */
@Service
public class CarIoService {
    private static Logger logger= LoggerFactory.getLogger(CarIoService.class);
    public void sendCommand(int carId,byte[] message){

        byte[] bytes = message;
        bytes= Utils.checkData(bytes);
        SessionMap sessionMap = SessionMap.getInstance();
        IoSession session=sessionMap.getSessionByAttribute("carId",carId);
        session.write(IoBuffer.wrap(bytes));
        System.out.println("bytes sendCommand:"+IoBuffer.wrap(bytes));
        logger.debug("bytes sendCommand:"+IoBuffer.wrap(bytes));
    }
}
