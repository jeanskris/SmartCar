package com.SCC.SmartCar.model;

import java.io.*;

/**
 * Created by ZJDX on 2016/7/26.
 * 本来拿他处理publish乱码问题 结果不管用
 */
public class MessageUtil {
    //convert To String
    public static String convertToString(Object obj) throws IOException {

        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        String str = bo.toString("UTF-8");
        bo.close();
        oo.close();
        return str;
    }

    //convert To Message
    public static Object convertToMessage(byte[] bytes) throws Exception{
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn = new ObjectInputStream(in);
        return sIn.readObject();

    }
}
