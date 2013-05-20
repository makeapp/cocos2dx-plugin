/****************************************************************************************
 * Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.     *
 ****************************************************************************************/

package com.makeapp.android.ksoap;

import com.makeapp.javase.log.Logger;
import com.makeapp.javase.lang.StringUtil;
import com.makeapp.javase.util.convert.ConverterUtil;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-7-1
 * Time: ÏÂÎç2:15
 */
public class SoapUtil {
    static Logger logger = Logger.getLogger(SoapUtil.class);

    {
        ConverterUtil.addConverter(new MapSoapObjectConverter());
        ConverterUtil.addConverter(new SoapObjectMapConverter());
    }

    public static <T> T getService(final Class<T> t) {
        Class[] clazz = {t};

        return (T) Proxy.newProxyInstance(SoapUtil.class.getClassLoader(), clazz, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                WebService ws = t.getAnnotation(WebService.class);
                if (ws != null) {

                    Type type = method.getGenericReturnType();
                    if (type instanceof ParameterizedType) {
                        ParameterizedType ptypes = (ParameterizedType) type;
                        ptypes.getOwnerType();

                    }

                    String wsdl = ws.wsdlLocation();
                    WebMethod webMethod = method.getAnnotation(WebMethod.class);
                    if (webMethod != null) {
                        String action = StringUtil.getString(webMethod.action(), method.getName());
                        SoapObject request = new SoapObject(ws.targetNamespace(), action);
                        Annotation[][] allAnns = method.getParameterAnnotations();
                        for (int i = 0; i < allAnns.length; i++) {
                            for (Annotation an : allAnns[i]) {
                                if (an.annotationType() == WebParam.class) {
                                    WebParam p = (WebParam) an;
                                    request.addProperty(p.name(), args[i]);
                                }
                            }
                        }

                        Object result = call(wsdl, ws.targetNamespace() + action, request);
                        if (result instanceof SoapObject) {

                        }
                    }
                }
                return null;
            }
        });

    }


    private static Object call(String serviceUrl, String action, SoapObject object) {
        HttpTransportSE transport = new HttpTransportSE(serviceUrl);
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(object);
            transport.debug = true;
            transport.call(action, envelope);
            logger.info("request:{0}", transport.requestDump);
            logger.info("response:{0}", transport.responseDump);
            if (envelope.getResponse() != null) {
                Object resultObject = envelope.getResponse();
                if (resultObject instanceof SoapObject) {
                    return resultObject;
                } else if (resultObject instanceof SoapPrimitive) {
                    return resultObject.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("request:{0}", transport.requestDump);
            logger.info("response:{0}", transport.responseDump);
        }
        return null;
    }
}
