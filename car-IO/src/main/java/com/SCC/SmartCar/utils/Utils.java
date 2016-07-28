package com.SCC.SmartCar.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by ZJDX on 2016/6/24.
 */
public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);
    /**
     * byte2float
     *
     * @param b byte (at least four bytes)
     * @param index start position
     * @return float
     */
    public static float byte2float(byte[] b, int index) {
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }
    public static byte XOR(byte[] data){
        byte temp=data[0];
        for(int i=1;i<=data.length-4;i++){
            temp=(byte)(temp^data[i]);
        }

        return temp;
    }

    public static byte[] checkData(byte[] data){
        byte[] after=data;
        after[13]=XOR(after);
        return after;
    }
    public static byte[] shortToByte(short value){
        int temp = value;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (temp & 0xff);// 将最低位保存在最低位
            temp = temp >> 8;// 向右移8位
            System.out.println(b[i]);
        }
        return b;
    }


    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);// 最低位
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }


    public static byte[] intToByte(int value){
        int temp =value;
        byte[] b = new byte[4];
        for(int i=0;i<b.length;i++){
            b[i] = (byte) (temp & 0xff);
            temp = temp>>8;
        }
        return b;
    }


    public static int byteToInt(byte[] b){
        int a1 = b[0] & 0xff;
        int a2 = b[1] & 0xff;
        int a3 = b[2] & 0xff;
        int a4 = b[3] & 0xff;
        a4 = a4 <<24;
        a3 = a3 <<16;
        a2 = a2 <<8;
        return a4|a3|a2|a1;
    }

    public static byte[] objectToByte(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        byte[] b =  bos.toByteArray();
        oos.close();
        bos.close();
        return b;
    }

    public static Object byteToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Object obj;
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        obj = oi.readObject();
        bi.close();
        oi.close();
        return obj;
    }
    public static String byteArrayToString(byte[] bytes)
    {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < bytes.length; i++)
        {
            buff.append(bytes[i] + " ");
        }
        return buff.toString();
    }
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
