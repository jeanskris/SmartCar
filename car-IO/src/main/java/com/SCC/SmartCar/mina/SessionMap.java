package com.SCC.SmartCar.mina;

/**
 * Created by ZJDX on 2016/6/25.
 */

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Description: 单例工具类，保存所有mina客户端连接
 * @author whl
 * @date 2014-9-29 上午10:09:15
 *
 */
public class SessionMap {

    private final static Logger log = LoggerFactory.getLogger(SessionMap.class);
    private static SessionMap sessionMap = null;

    private Map<String, IoSession>map = new ConcurrentHashMap<String, IoSession>();


    //构造私有化 单例
    private SessionMap(){}

    private static class SingletonFactory {
        //JVM内部的机制能够保证当一个类被加载的时候，这个类的加载过程是线程互斥的
        private static  SessionMap sessionMap = new SessionMap();
    }
    /**
     * @Description: 获取唯一实例
     */
    public static SessionMap getInstance(){
        log.debug("SessionMap单例获取---");
        return SingletonFactory.sessionMap;
        /*if(sessionMap == null){
            sessionMap = new SessionMap();
        }
        return sessionMap;*/
    }


    /**
     * @Description: 保存session会话
     */
    public void addSession(String key, IoSession session){
        log.debug("保存会话到SessionMap单例---key=" + key);
        this.map.put(key, session);
    }

    /**
     * @Description: 根据key查找缓存的session
     */
    public IoSession getSession(String key){
        log.debug("获取会话从SessionMap单例---key=" + key);
        return this.map.get(key);
    }
    /**
     * @Description: 根据属性查找缓存的session,比如根据车的id获取session
     */
    public IoSession getSessionByAttribute(Object Name,Object value){
        log.debug("根据属性查找缓存的session---attr=" + value);
        IoSession ioSession=null;
        for (IoSession session:this.map.values()) {
            if(session.getAttribute(Name).equals(value)){
                ioSession=session;
            }
        }
        if(ioSession==null){
            log.debug("没有属性值为" + value+"的session!");
        }
        return ioSession;
    }
    /**
     * @Description: 发送消息到客户端
     */
    public void sendMessage(String[] keys, Object message){
        for(String key : keys){
            IoSession session = getSession(key);

            log.debug("反向发送消息到客户端Session---key=" + key + "----------消息=" + message);
            if(session == null){
                return;
            }
            session.write(message);

        }
    }


    /**
     * @Description: 发送消息到客户端
     */
    public void send(String key, Object message){
            IoSession session = getSession(key);
            log.debug("反向发送消息到客户端Session---key=" + key + "----------消息=" + message);
            if(session == null){
                return;
            }
            session.write(message);

    }

}
