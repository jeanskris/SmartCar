package com.SCC.SmartCar.mina;


import com.SCC.SmartCar.model.IOConstants;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component("tcpServerHandler")
public class TCPServerHandler extends IoHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(TCPServerHandler.class);
    @Autowired
    CarHandler carHandler;
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        if (message instanceof IoBuffer) {
            IoBuffer buffer = (IoBuffer) message;
            buffer.setAutoShrink(true);
            buffer.setAutoExpand(true);
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes);
            JSONObject json = new JSONObject(new String(bytes));
            String type=(String )json.get("type");



            if(type.equals("carBasic")){

                System.out.println("carBasic:"+json.toString());
                //将当前session的
                carHandler.carBasic(json);

            }else if(type.equals("runInfo")){
                System.out.println("runInfo:"+json.toString());
                //put data to runInfo
                carHandler.carRuntimeInfo(json);
            }else if(type.equals("init")){
                session.setAttribute("carId",json.get("carId"));
                System.out.println("init:"+json.toString());
                //put data to runInfo
            }


            /*JSONObject json = new JSONObject(new String(bytes));
            System.out.println("data from client:"+json.toString());*/
            //redisDao.save("currentCoordinate",coordinate);

           // carService.getDataFromCar(bytes); //调用service业务处理
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        System.out.println("data has been sent to client");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        InetSocketAddress address =(InetSocketAddress)session.getRemoteAddress();
      //  String clientIP = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();
        IOConstants.TCP_REMOTE_SERVER_IP=address.getAddress().toString().substring(1);
        //Constant.TCP_REMOTE_SERVER_PORT=address.getPort();
        logger.debug("sessionCreated, TCP_REMOTE_SERVER_IP: " +   IOConstants.TCP_REMOTE_SERVER_IP);
        System.out.println("sessionCreated,TCP_REMOTE_SERVER_IP: " +   IOConstants.TCP_REMOTE_SERVER_IP);
        //保存客户端的会话session
        SessionMap sessionMap = SessionMap.getInstance();
        sessionMap.addSession(IOConstants.TCP_REMOTE_SERVER_IP, session);
    }
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("Server sessionClosed" );
    }

    @Override
    public void sessionIdle(IoSession iosession, IdleStatus idlestatus)
            throws Exception {
        System.out.println("sessionIdle");
        super.sessionIdle(iosession, idlestatus);
    }
    @Override
    public void sessionOpened(IoSession iosession) throws Exception {
        System.out.println("sessionOpened");
        super.sessionOpened(iosession);
    }
}
