package com.SCC.SmartCar.mina;


import com.SCC.SmartCar.model.IOConstants;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.InetSocketAddress;

@Component("tcpAddress")
public class ServerIpTCP extends InetSocketAddress {
    /*public ServerIpTCP() {
        super("114.215.144.107", 5555);
    }//aliyun*/
    public ServerIpTCP() {
        super(IOConstants.TCP_SERVER_IP, IOConstants.TCP_SERVER_PORT);
    } //localhost
    public ServerIpTCP(InetAddress add, int port) {
        super(add, port);
    }
}
