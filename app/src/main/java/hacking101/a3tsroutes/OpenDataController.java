package hacking101.a3tsroutes;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.transit.realtime.GtfsRealtime;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class OpenDataController {

    private static OpenDataController instance = null;
    private static URL BUS_STOP_JSON_URL = null;
    private static URL VEHICLE_POSITION_URL = null;
    private static URL TRIP_UPDATE_URL = null;
    private static URL ALERT_URL = null;

    private GtfsRealtime.FeedMessage vehiclePositionFeed = null;
    private GtfsRealtime.FeedMessage tripFeed = null;
    private GtfsRealtime.FeedMessage alertFeed = null;
    private HashMap<String, BusStop> busStops = null;

    private OpenDataController() {
        busStops = new HashMap<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            BUS_STOP_JSON_URL = new URL("https://data.edmonton.ca/resource/hq5j-d489.json");
            queryBusStopData();

            VEHICLE_POSITION_URL = new URL("https://data.edmonton.ca/download/7qed-k2fc/application%2Foctet-stream");
            TRIP_UPDATE_URL = new URL("https://data.edmonton.ca/download/uzpc-8bnm/application%2Foctet-stream");
            ALERT_URL = new URL("https://data.edmonton.ca/download/rqaa-jae6/application%2Foctet-stream");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static OpenDataController getInstance() {
        if (instance == null)
            instance = new OpenDataController();
        return instance;
    }

    public HashMap<String, BusStop> getBusStops() {
        return busStops;
    }

    public void updateVehiclePositionFeed() {
        try {
            vehiclePositionFeed = GtfsRealtime.FeedMessage.parseFrom(VEHICLE_POSITION_URL.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queryBusStopData() throws IOException {
        InputStreamReader reader = new InputStreamReader(BUS_STOP_JSON_URL.openStream());
        Type listType = new TypeToken<List<BusStop>>(){}.getType();
        Gson gson = new Gson();
        List<BusStop> busStopList = gson.fromJson(reader, listType);

        for (BusStop stop: busStopList) {
            busStops.put(stop.stop_id, stop);
        }
    }

    public List<GtfsRealtime.FeedEntity> getVehicleEntities() {
        return vehiclePositionFeed.getEntityList();
    }

    public GtfsRealtime.VehiclePosition getVehicleEntity(String id) {
        List<GtfsRealtime.FeedEntity> vehicles = getVehicleEntities();
        for (GtfsRealtime.FeedEntity vehicle: vehicles) {
            if (!vehicle.hasVehicle())
                continue;
            if (vehicle.getVehicle().getVehicle().getId().equals(id))
                return vehicle.getVehicle();
        }

        return null;
    }

    public void updateAllFeeds() {
        updateVehiclePositionFeed();
        updateTripFeed();
        updateAlertFeed();
    }

    public void updateTripFeed() {
        try {
            tripFeed = GtfsRealtime.FeedMessage.parseFrom(TRIP_UPDATE_URL.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<GtfsRealtime.FeedEntity> getTripEntities() {
        return tripFeed.getEntityList();
    }

    public GtfsRealtime.TripUpdate getTripEntityByRoute(String id) {
        for (GtfsRealtime.FeedEntity entity: getTripEntities()) {
            if (!entity.hasTripUpdate())
                continue;
            if (entity.getTripUpdate().getTrip().getRouteId().equals(id)) {
                return entity.getTripUpdate();
            }
        }

        return null;
    }

    public void updateAlertFeed() {
        try {
            alertFeed = GtfsRealtime.FeedMessage.parseFrom(ALERT_URL.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<GtfsRealtime.FeedEntity> getAlertEntities() {
        return alertFeed.getEntityList();
    }
}
