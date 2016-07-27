import com.SCC.SmartCar.service.CarIoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ZJDX on 2016/7/24.
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml"})
//使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner
@RunWith(SpringJUnit4ClassRunner.class)
public class MinaTest {
    @Autowired
    CarIoService carIoService;
    @Test
    public void send() throws InterruptedException{
        byte[] bytes={0x11};
        carIoService.sendCommand(1, bytes);
    }
}
