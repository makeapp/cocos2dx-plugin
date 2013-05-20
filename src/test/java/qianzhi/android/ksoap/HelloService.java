package qianzhi.android.ksoap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-7-1
 * Time: обнГ4:11
 */

@WebService(wsdlLocation = "http://www.mykaka.cn/webservice/android.asmx?WSDL",
        targetNamespace = "http://mykaka.cn/webservice/",
        portName = "1.2")
public interface HelloService
{
    @WebMethod()
    @WebResult()
    public Map<String, Object> sayHelloWorldFrom(@WebParam(name = "arg0") String s);

    @WebMethod()
    public List<Map<String, Object>> list1();
}
