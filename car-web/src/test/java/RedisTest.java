import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ZJDX on 2016/7/24.
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml"})
//使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTest {

  /*  @Autowired
    private RedisDao redisDao;

    @Test
    public void saveOne() throws InterruptedException{
        redisDao.save("test","test");
    }
    @Test
    public void getMap() throws InterruptedException{
        System.out.println("map：" + redisDao.read(1).getMappingX());
    }*/

}