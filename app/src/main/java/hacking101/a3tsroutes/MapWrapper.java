package hacking101.a3tsroutes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.utils.PolylineUtils;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapWrapper {

    public static void InitMap(LocationManager locMan, MapboxMap map){
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location loc = null;

        try{
             loc = locMan.getLastKnownLocation(locationProvider);
        } catch (SecurityException sec){
            throw new SecurityException(sec);
        }

        LatLng userPos = new LatLng(loc.getLatitude(), loc.getLongitude());

        CameraPosition cam = new CameraPosition.Builder()
                .target(userPos)
                .zoom(15)
                .tilt(45)
                .build();
        map.setCameraPosition(cam);


        return;
    }

    public static void set3D(MapboxMap map, MapView mapView){
        BuildingPlugin buildingPlugin = new BuildingPlugin(mapView,map,map.getStyle());
        buildingPlugin.setVisibility(true);
    }


    @SuppressWarnings("deprecation")
    public static void DrawRoute(final MapboxMap map, LatLng stopA, LatLng stopB, Context app){
        Point start = Point.fromLngLat(stopA.getLongitude(), stopA.getLatitude());
        Point fin = Point.fromLngLat(stopB.getLongitude(), stopB.getLatitude());
        NavigationRoute.builder(app)
                .accessToken("pk.eyJ1IjoiY3VydGdnIiwiYSI6ImNqcjN4YmR2cDBieWo0M3J3eHM2NndzZ3YifQ.U94oGuGo_P6DTcbGYTZ4Eg")
                .origin(start)
                .destination(fin)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        Log.d("MAP", "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e("MAP", "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e("MAP", "No routes found");
                            return;
                        }

                        DirectionsRoute currentRoute = response.body().routes().get(0);
                        String waypoints = currentRoute.geometry();
                        List<Point> route = PolylineUtils.decode(waypoints, 6);
                        ArrayList<LatLng> finalRoute = new ArrayList<>();
                        for (Point p : route) {
                            finalRoute.add(new LatLng(p.latitude(), p.longitude()));
                        }
                        map.addPolyline(new PolylineOptions()
                                .addAll(finalRoute)
                                .color(Color.CYAN)
                                .width(3));
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e("MAP", "Error: " + throwable.getMessage());
                    }
                });
    }

    @SuppressWarnings("deprecation")
    public static Marker DrawBus(MapboxMap map, MapView mapView, Context app, LatLng pos, String busName){
        Bitmap icBmap = BitmapFactory.decodeResource(app.getResources(),R.drawable.bus);
        Icon ic = IconFactory.recreate("bus#103",icBmap);
        MarkerOptions mo = new MarkerOptions()
                            .position(pos)
                            .title(busName)
                            .icon(ic);
        Marker mark = map.addMarker(mo);
        return mark;
    }

    @SuppressWarnings("deprecation")
    public static void UndrawBus(Marker mark, MapboxMap map){
        map.removeMarker(mark);
    }


}
