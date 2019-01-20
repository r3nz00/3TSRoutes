package hacking101.a3tsroutes;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class BusStop {
    public String address;
    public String landmark_name;
    public String latitude;
    public String longitude;
    public String stop_id;

    public long departureTime;
    public long arrivalTime;

    LatLng getPosition() {
        return new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
    }

    long getEtaMinutes() {
        return arrivalTime / 60;
    }

    long getEtaSeconds() {
        return arrivalTime - (getEtaMinutes() * 60);
    }

    long getEtdMinutes() {
        return departureTime / 60;
    }

    long getEtdSeconds() {
        return departureTime - (getEtdMinutes() * 60);
    }
}
