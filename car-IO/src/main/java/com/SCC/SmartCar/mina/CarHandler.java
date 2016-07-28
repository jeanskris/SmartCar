package com.SCC.SmartCar.mina;


import com.SCC.SmartCar.dao.RedisDao;
import com.SCC.SmartCar.model.CarRuntimeInfo;
import com.SCC.SmartCar.model.Coordinate;
import com.SCC.SmartCar.model.RedisPublisher;
import com.SCC.SmartCar.service.CarIoService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component("carHandler")
public class CarHandler {
    private static Logger logger = LoggerFactory.getLogger(CarHandler.class);
   /* @Autowired
    private JmsTemplate jmsTemplate;*/
    @Autowired
    RedisDao redisDao;
    @Autowired
    CarIoService carIoService;
    @Autowired
    RedisPublisher redisPublisher;
    /*protected byte[] bytes;
    public CarHandler(byte[] bytes) {
        this.bytes = bytes;
    }*/
    public CarHandler(){

    }
    public void carBasic(JSONObject json) throws Exception{
        //put data to carBasic message QUENE

    }
    public void carRuntimeInfo( JSONObject json) throws Exception{
        //put data to carBasic message QUENE
        Date receivedTime=new Date(System.currentTimeMillis());

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        CarRuntimeInfo carRuntimeInfo = new CarRuntimeInfo();
        carRuntimeInfo.setAcceleration((Double)json.get("Acceleration"));
        carRuntimeInfo.setAngel((Double) json.get("angel"));
        carRuntimeInfo.setCarId((Integer)json.get("carId"));
        carRuntimeInfo.setCoordinate(new Coordinate((Double)json.get("location_x"),(Double)json.get("location_y")));
        carRuntimeInfo.setSpeed((Double) json.get("speed_current"));
        carRuntimeInfo.setCurrentTime(sdf.parse(json.get("currentTime").toString()).getTime());


        //redisDao.delete(Integer.toString((Integer)json.get("carId")));
        //carRuntimeInfo= (CarRuntimeInfo)redisDao.LPop("0");
        redisDao.LPush("carRuntimeInfo_list:"+Integer.toString((Integer)json.get("carId")),carRuntimeInfo);

        List<CarRuntimeInfo> list=(List<CarRuntimeInfo>)redisDao.LRange("carRuntimeInfo_list:"+Integer.toString((Integer)json.get("carId")),0,-1);
        redisPublisher.Publish(Integer.toString((Integer)json.get("carId"))+":topic",carRuntimeInfo);

        try {

            String currentTime=json.get("currentTime").toString();
            Date date = sdf.parse(currentTime);
            long delay=(receivedTime.getTime()-date.getTime());
            System.out.println("receivedTime="+receivedTime.toString());
            System.out.println("json.get="+date.toString());
            System.out.println("delay="+delay);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



/*    public void sendMessage(final javax.jms.Destination destination, final Serializable obj) {
        System.out.println("---------------生产者发送消息-----------------:"+obj.toString());
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objMessage = session.createObjectMessage(obj);
                return objMessage;
            }

        });
    }*/


}

