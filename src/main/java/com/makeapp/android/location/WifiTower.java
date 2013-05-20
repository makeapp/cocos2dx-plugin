package com.makeapp.android.location;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-8-19
 * Time: ÏÂÎç4:40
 */
public class WifiTower {
    private String macAddress;
    private Integer signalStrength;
    private Integer age;


    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Integer getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(Integer signalStrength) {
        this.signalStrength = signalStrength;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
