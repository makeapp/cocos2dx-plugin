package com.makeapp.android.location;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import com.makeapp.android.util.ServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-8-19
 * Time: ����12:30
 */
public class LocationUtil {

    public static LocationManager getLocationManager(Context context) {
        return ServiceUtil.getLocationManager(context);
    }

    public static Location getLocation(Context context) {
        LocationManager locationManager = getLocationManager(context);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        String best = locationManager.getBestProvider(criteria, true);

        return locationManager.getLastKnownLocation(best);
    }

    public static LocationDesc getCellLocation(double latitude, double longitude, List<CellTower> cellTowers, List<WifiTower> wifiTower) {
        Map location = new HashMap();
//        location.put("latitude", latitude);
//        location.put("longitude", longitude);
//
//        Map<String, Object> params = new HashMap();
//        params.put("version", "1.1.0");
//        params.put("host", "maps.google.com");
//        params.put("home_mobile_country_code", 310);
//        params.put("home_mobile_network_code", 410);
//        params.put("radio_type", "gsm");
//        params.put("carrier", "Vodafone");
//        params.put("location", location);
//        params.put("request_address", true);
//        params.put("address_language", "zh_CN");
//        params.put("cell_towers", cellTowers);
//        params.put("wifi_towers", wifiTower);
//
//        String result = HttpUtil.postGetText("", "http://www.google.com/loc/json", JsonUtil.toJson(params), "text/json");
//        if (result != null) {
//            JsonObject root = (JsonObject) JsonUtil.fromJson(result);
//            JsonObject object = root.getAsJsonObject("location");
//            return JsonUtil.fromJson(object, LocationDesc.class);
//        }
        return null;
    }

    public static List<Placemark> getAddress(String lat, String lag) {

//        List<Placemark> placemarks = new ArrayList<Placemark>();
//        try {
//            Map<String, Object> params = new HashMap();
//            params.put("key", "abcdefg");
//            params.put("q", lat + "," + lag);
//            String result = HttpUtil.getText("address", "http://maps.google.cn/maps/geo", params);
//            if (result != null) {
//                JsonObject root = (JsonObject) JsonUtil.fromJson(result);
//                JsonArray jsonArray = root.getAsJsonArray("Placemark");
//                for (int i = 0; i < jsonArray.size(); i++) {
//                    JsonObject placemarkJson = (JsonObject) jsonArray.get(i);
//
//                    Placemark placemark = new Placemark();
//                    placemarks.add(placemark);
//                    placemark.setAddress(placemarkJson.get("address").getAsString());
//
//
//                    JsonObject addressDetailJson = placemarkJson.getAsJsonObject("AddressDetails");
//                    placemark.setAccuracy(addressDetailJson.get("Accuracy").getAsString());
//
//                    JsonObject countryJson = addressDetailJson.getAsJsonObject("Country");
//                    placemark.setCountryName(countryJson.get("CountryName").getAsString());
//                    placemark.setCountryCode(countryJson.get("CountryNameCode").getAsString());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        return placemarks;
        return null;
    }


    public static void main(String[] args) {
//        System.out.println("" + getAddress("1450", "233"));

        CellTower ct = new CellTower();
        ct.setCellId("42");
        ct.setLocationAreaCode(415);
        ct.setMobileCountryCode(310);
        ct.setMobileNetworkCode(410);
        ct.setSignalStrength(-60);
        ct.setTimingAdvance(5555);

        List cellTowers = new ArrayList();
        cellTowers.add(ct);

        ct = new CellTower();
        ct.setCellId("88");
        ct.setLocationAreaCode(415);
        ct.setMobileCountryCode(310);
        ct.setMobileNetworkCode(580);
        ct.setSignalStrength(-70);
        ct.setTimingAdvance(7777);
        cellTowers.add(ct);


        WifiTower wifiTower = new WifiTower();
        wifiTower.setMacAddress("01-23-45-67-89-ab");
        wifiTower.setSignalStrength(8);
        wifiTower.setAge(0);

        List wifiTowers = new ArrayList();
        wifiTowers.add(wifiTower);

//        getLocation(51.0, -0.1, cellTowers, wifiTowers);
    }
}
