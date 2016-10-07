import java.util.PriorityQueue;

/**
 * Created by ZJDX on 2016/7/24.
 */
public class Test {

    public static double median(int[] array){
        int heapSize = array.length/2 + 1;
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>(heapSize);
        for(int i=0; i<heapSize; i++){
            heap.add(array[i]);
        }
        for(int i=heapSize; i<array.length; i++){
            if(heap.peek()<array[i]){
                heap.poll();
                heap.add(array[i]);
            }
        }
        if(array.length % 2 == 1){
            return (double)heap.peek();
        }
        else{
            return (double)(heap.poll()+heap.peek())/2.0;
        }
    }
    public static void main(String[] args) {//test reading from db
        //ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        CarBasicServiceImpl service = context.getBean(CarBasicServiceImpl.class);
//        CarBasic carBasic=service.findById(1);
//        System.out.println(carBasic);
       /* TcpClient tcpClient=new TcpClient();
        byte[] bytes = { 0x11};
        tcpClient.send(IoBuffer.wrap(bytes));*/
        int a[] = { 8, 5, -4, -6, 9};
        System.out.println(median(a));
        //System.out.println(getHost("http://www.blog.csdn.net/xichenguan/article/details/41485447"));
    }


}
