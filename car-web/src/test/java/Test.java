import com.SCC.SmartCar.mina.TcpClient;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * Created by ZJDX on 2016/7/24.
 */
public class Test {
    public static void main(String[] args) {//test reading from db
        //ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        CarBasicServiceImpl service = context.getBean(CarBasicServiceImpl.class);
//        CarBasic carBasic=service.findById(1);
//        System.out.println(carBasic);
        TcpClient tcpClient=new TcpClient();
        byte[] bytes = { 0x11};
        tcpClient.send(IoBuffer.wrap(bytes));
    }
}
