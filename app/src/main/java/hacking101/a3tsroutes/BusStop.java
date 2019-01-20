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

    long getArrivalTimeUnix() {
        return arrivalTime - (System.currentTimeMillis() / 1000L);
    }

    long getDepartureTimeUnix() {
        return departureTime - (System.currentTimeMillis() / 1000L);
    }

    long getEtaMinutes() {
        return getArrivalTimeUnix() / 60;
    }

    long getEtaSeconds() {
        return getArrivalTimeUnix() - (getEtaMinutes() * 60);
    }

    long getEtdMinutes() {
        return getDepartureTimeUnix() / 60;
    }

    long getEtdSeconds() {
        return getDepartureTimeUnix() - (getEtdMinutes() * 60);
    }
}
