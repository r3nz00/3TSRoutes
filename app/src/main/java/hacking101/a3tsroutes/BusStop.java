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

    String getTime() {
        if (arrivalTime == 0)
            return "Arriving in: " + getEtdMinutes() + ":" + getEtdSeconds();
        else
            return "Departing in: " + getEtaMinutes() + ":" + getEtaSeconds();
    }

    long getArrivalTimeUnix() {
        return (System.currentTimeMillis() / 1000L) - arrivalTime;
    }

    long getDepartureTimeUnix() {
        return (System.currentTimeMillis() / 1000L) - departureTime;
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
