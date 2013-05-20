package com.makeapp.android.location;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-8-19
 * Time: ÏÂÎç5:12
 */
public class LocationDesc {
    public LocationDesc() {
    }

    private double latitude;
    private double longitude;
    private double altitude;
    private double accuracy;
    private double altitudeAccuracy;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getAltitudeAccuracy() {
        return altitudeAccuracy;
    }

    public void setAltitudeAccuracy(double altitudeAccuracy) {
        this.altitudeAccuracy = altitudeAccuracy;
    }

}
