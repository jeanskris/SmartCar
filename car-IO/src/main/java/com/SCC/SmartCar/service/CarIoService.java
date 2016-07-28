package com.SCC.SmartCar.service;

import com.SCC.SmartCar.mina.SessionMap;
import com.SCC.SmartCar.model.Command;
import com.SCC.SmartCar.utils.Utils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public void sendAutoJsonCommand(int carId,byte[] message){
        try{

            Date currentTime=new Date(System.currentTimeMillis());
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
            byte[] cmd=Utils.checkData(message);
            //String data = Utils.bytesToHexString(cmd);//传十六进制字符串形式的命令
            String date = sdf.format(currentTime);
            Command command=new Command();
            command.setMode("00");
            command.setData(cmd);
            command.setTime(date);
            SessionMap sessionMap = SessionMap.getInstance();
            IoSession session=sessionMap.getSessionByAttribute("carId",carId);
            JSONObject jsonObject=new JSONObject(command);
            jsonObject.put("data",cmd);
            String j=jsonObject.toString();
            byte[] bytes=j.getBytes();
            //byte[] bytes = Utils.objectToByte(j);
            session.write(IoBuffer.wrap(bytes));
            System.out.println("bytes sendAutoJsonCommand:");
            logger.debug("bytes sendCommand:"+IoBuffer.wrap(bytes));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
