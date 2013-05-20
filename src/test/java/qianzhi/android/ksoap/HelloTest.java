package qianzhi.android.ksoap;

import com.makeapp.android.ksoap.SoapUtil;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-7-1
 * Time: обнГ4:36
 */
public class HelloTest
{
    public static void main(String[] args)
    {
        HelloService hs = SoapUtil.getService(HelloService.class);
        hs.list1();
//        System.out.println(""+hs.sayHelloWorldFrom("sss"));
    }
}
