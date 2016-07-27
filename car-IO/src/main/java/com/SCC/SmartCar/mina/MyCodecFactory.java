package com.SCC.SmartCar.mina;

import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

import java.nio.charset.Charset;


public class MyCodecFactory extends TextLineCodecFactory {
    public MyCodecFactory() {
        super(Charset.forName("UTF-8"));
    }
}
