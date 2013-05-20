package com.makeapp.android.ksoap;

import com.makeapp.javase.util.convert.AbstractConverter;
import org.ksoap2.serialization.SoapObject;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-7-1
 * Time: обнГ6:54
 */
public class MapSoapObjectConverter
        extends AbstractConverter<Map, SoapObject> {

    @Override
    protected SoapObject doConvert(Map obj, SoapObject defValue) {
        return null;
    }

    public String getType() {
        return Map.class.getSimpleName() + SoapObject.class.getSimpleName();
    }
}
