package hacking101.a3tsroutes;

import android.util.Log;

import com.google.transit.realtime.GtfsRealtime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

                if (stopTime.hasArrival()) {
                    stop.arrivalTime = stopTime.getArrival().getTime();
                    stop.departureTime = 0;
                }
                else if (stopTime.hasDeparture()) {
                    stop.arrivalTime = 0;
                    stop.departureTime = stopTime.getDeparture().getTime();
                }

                stops.add(stop);
//                Log.i("RouteStop", stop.latitude + " " + stop.longitude);
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

    public static List<String> getRouteIds() {
        OpenDataController openData = OpenDataController.getInstance();
        openData.updateTripFeed();
        ArrayList<String> ids = new ArrayList<>();
        for (GtfsRealtime.FeedEntity entity: openData.getTripEntities()) {
            if (!entity.hasTripUpdate())
                continue;
            ids.add(entity.getTripUpdate().getTrip().getRouteId());
        }

        return ids;
    }
}
