package hacking101.a3tsroutes;

import com.google.transit.realtime.GtfsRealtime;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class Bus {
    private String bus_id;
    private Double speed;
    private OpenDataController opendata = OpenDataController.getInstance();
    private GtfsRealtime.VehiclePosition vehicle;
    private LatLng current_pos; // 0: latitude, 1:longitude

    public Bus(String new_id) {
        this.bus_id = new_id;
        opendata.updateAllFeeds();

        // update before getting data
        this.vehicle = opendata.getVehicleEntity(this.bus_id);
        this.current_pos = new LatLng(vehicle.getPosition().getLatitude(), vehicle.getPosition().getLongitude());
    }

    public String getBus_id(){return this.bus_id;}

    public void updateCurrentPos() {
        // update current position of the bus

        //update data controller before grabbing data
        opendata.updateVehiclePositionFeed();
        this.vehicle = opendata.getVehicleEntity(this.bus_id);
        this.current_pos.setLatitude(vehicle.getPosition().getLatitude());
        this.current_pos.setLongitude(vehicle.getPosition().getLongitude());
    }

    public LatLng getPosition() {
        return this.current_pos;
    }
}
