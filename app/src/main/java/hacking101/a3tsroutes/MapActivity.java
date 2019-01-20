package hacking101.a3tsroutes;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private ArrayList<Bus> vehicles;
    static final int ACCESS_LOCATION_REQUEST_CODE = 1337;
    static final String ROUTE_ID_EXTRA = "ROUTE_ID";
    PermissionsManager permissionsManager;
    PermissionsListener permissionsListener = new PermissionsListener() {
        @Override
        public void onExplanationNeeded(List<String> permissionsToExplain) {

        }

        @Override
        public void onPermissionResult(boolean granted) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_map);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //Get routeID
        final int routeID = getIntent().getIntExtra(ROUTE_ID_EXTRA,8);
        Log.i("ROUTE_ID", ""+routeID);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            //User has NOT accepted permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_LOCATION_REQUEST_CODE);
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                MapWrapper.InitMap(locationManager,mapboxMap);
                mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        MapWrapper.set3D(mapboxMap,mapView);
//                        MapWrapper.DrawBus(mapboxMap,mapView,getApplicationContext(), new LatLng(53.523,-113.53),"BUS#103");
                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments

                        //TODO: Get Rizwans LatLng paths from here
                        Route route = new Route(""+routeID);
                        ArrayList<LatLng> nodes = new ArrayList<>();
                        List<BusStop> bs = route.getStops();
                        vehicles = route.getBuses();
                        for (BusStop b:bs) {
                            nodes.add(b.getPosition());
                        }
                        for(Bus b:vehicles){
                            MapWrapper.DrawBus(mapboxMap,mapView,getApplicationContext(),b.getPosition(),b.getBus_id());
                        }

                        MapWrapper.DrawRoute(mapboxMap,nodes,getApplicationContext());
                        getDeviceLocation(mapboxMap);
                    }
                });
            }
        });
    }

    public void getDeviceLocation(MapboxMap mapboxMap) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(MapActivity.this)) {
            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            // Activate with options
            try {
                locationComponent.activateLocationComponent(MapActivity.this, mapboxMap.getStyle());
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(permissionsListener);
            permissionsManager.requestLocationPermissions(MapActivity.this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    ACCESS_LOCATION_REQUEST_CODE);
//        }
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
