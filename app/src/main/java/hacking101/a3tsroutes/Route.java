package hacking101.a3tsroutes;

import android.util.Log;

import com.google.transit.realtime.GtfsRealtime;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private String routeId = null;
    private List<Bus> buses = null;
    private List<BusStop> stops = null;

    public Route(String routeId) {
        this.routeId = routeId;
        this.stops = new ArrayList<BusStop>();
        this.buses = new ArrayList<Bus>();

        OpenDataController openData = OpenDataController.getInstance();
        GtfsRealtime.TripUpdate trip = openData.getTripEntityByRoute(routeId);
        if (trip != null) {
            for (GtfsRealtime.TripUpdate.StopTimeUpdate stopTime : trip.getStopTimeUpdateList()) {
                BusStop stop = openData.getBusStops().get(stopTime.getStopId());
                if (stop == null)
                    continue;

                if (stopTime.hasArrival())
                    stop.arrivalTime = stopTime.getArrival().getTime();
                else if (stopTime.hasDeparture())
                    stop.departureTime = stopTime.getDeparture().getTime() - (System.currentTimeMillis() / 1000L);

                stops.add(stop);
                Log.i("RouteStop", stop.latitude + " " + stop.longitude);
            }

            buses.add(new Bus(trip.getVehicle().getId()));
        }
    }

    public String getRouteID(){
        return new String(routeId);
    }

    public ArrayList<Bus> getBuses(){
        return new ArrayList<Bus>(buses);
    }

    public List<BusStop> getStops() {
        return new ArrayList<BusStop>(stops);
    }

    

}
