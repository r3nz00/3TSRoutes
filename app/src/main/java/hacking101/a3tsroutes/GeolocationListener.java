package hacking101.a3tsroutes;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GeolocationListener implements LocationListener {
    public Location getPhoneLocation() {
        return phoneLocation;
    }

    public void setPhoneLocation(Location phoneLocation) {
        this.phoneLocation = phoneLocation;
    }

    private Location phoneLocation = null;

    @Override
    public void onLocationChanged(Location location) {
        if (phoneLocation == null)
            phoneLocation = location;
        else
            phoneLocation.set(location);
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
