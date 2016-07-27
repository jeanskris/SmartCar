package com.SCC.SmartCar.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
