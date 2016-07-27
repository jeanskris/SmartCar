import com.SCC.SmartCar.model.IOConstants;
import com.SCC.SmartCar.service.CarIoService;

/**
 * Created by ZJDX on 2016/7/24.
 */
public class MinaTest1 {
    public static void main(String[] args){
        CarIoService carIoService=new CarIoService();
        byte[] bytes= IOConstants.FORWARD;
        carIoService.sendCommand(1, bytes);
    }
}
