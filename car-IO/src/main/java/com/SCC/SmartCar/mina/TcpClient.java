package com.SCC.SmartCar.mina;


import com.SCC.SmartCar.model.IOConstants;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

/**
 * Created by ZJDX on 2016/5/24.
 */
public class TcpClient extends IoHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(TcpClient.class);
    private IoConnector connector;
    private static IoSession session;

    public SocketAddress getAddress() {
        return address;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }

    private SocketAddress address;

    public boolean send(Object message) {
        if (session != null && session.isConnected()) {
            throw new IllegalStateException(
                    "Already connected. Disconnect first.");
        }
        connector = new NioSocketConnector();
        try {
            connector.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
            connector.setHandler(this);
            address=new InetSocketAddress(IOConstants.TCP_REMOTE_SERVER_IP, IOConstants.TCP_REMOTE_SERVER_PORT);
            ConnectFuture future1 = connector.connect(address);
            future1.awaitUninterruptibly();
            if (!future1.isConnected()) {
                logger.debug("!future1.isConnected");
                System.out.println("!future1.isConnected");
                return false;
            }
            session = future1.getSession();
            session.write(message);
            logger.debug("TCP write");
            System.out.println("TCP write");
            session.close();
            session.getCloseFuture().awaitUninterruptibly();
            connector.dispose();
            return true;
        }
        catch (Exception e) {
            connector.dispose();
            System.out.println("exception:" + e.toString() + "\n details:" + e.getCause().toString());
            return false;
        }
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("sessionOpened client: " + session.getRemoteAddress());
        super.sessionOpened(session);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(session, cause);
    }

    //when the data come from server
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        System.out.println("server return:" + message.toString());
    }
    @Override
    public void sessionIdle(IoSession iosession, IdleStatus idlestatus)
            throws Exception {
        System.out.println("sessionIdle");
        super.sessionIdle(iosession, idlestatus);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("client created a session: " + session.getRemoteAddress());
    }
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("client sessionClosed");
    }

}