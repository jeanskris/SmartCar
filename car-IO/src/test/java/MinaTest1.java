import java.util.Scanner;

/**
 * Created by ZJDX on 2016/7/24.
 */
public class MinaTest1 {
    static public int enterStation(int n)
{
    if(n==1)
    {
        return 1;
    }
    if(n==2)
    {
        return 2;
    }
    if(n==3)
    {
        return 4;
    }
    return enterStation(n-1)+enterStation(n-2)+enterStation(n-3);
}
    // 递推实现方式
    public static int fibonacciNormal(int n){
        if(n==1)
        {
            return 1;
        }
        if(n==2)
        {
            return 2;
        }
        if(n==3)
        {
            return 4;
        }
        int n1 = 1, n2 = 2, n3=4,sn = 0;
        for(int i = 0; i < n - 3; i ++){
            sn = n1 + n2+n3;
            n1 = n2;
            n2 = n3;
            n3 = sn;
        }
        return sn;
    }

    public static void main(String[] args){
//        CarIoService carIoService=new CarIoService();
//        byte[] bytes= IOConstants.FORWARD;
//        carIoService.sendCommand(1, bytes);
        int a =0;
        Scanner input = new Scanner(System.in);
        a = input.nextInt();
        int outInt=0;
        outInt=fibonacciNormal(a);
        System.out.print(outInt);
    }

}
