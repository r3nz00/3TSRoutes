package hacking101.a3tsroutes;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.Arrays;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.transit.realtime.GtfsRealtime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static hacking101.a3tsroutes.MapActivity.ROUTE_ID_EXTRA;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mainRecyclerView;
    private MainRecyclerViewAdapter mainRecycleViewAdapter;
    private RecyclerView.LayoutManager mainLayoutManager;
    private String[] testStrings = {"Static string", "Second Static String","1","2","3","4","1","2","3","4","1","2","3","4"};
    private ArrayList<String> myDataset = new ArrayList<String>(Arrays.asList(testStrings));

    SwipeController swipeController;
    ItemTouchHelper itemTouchhelper;


    Timer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRecyclerView = (RecyclerView) findViewById(R.id.MainRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mainRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mainLayoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(mainLayoutManager);

        // specify an adapter (see also next example)

        mainRecycleViewAdapter = new MainRecyclerViewAdapter(myDataset, this);
        mainRecyclerView.setAdapter(mainRecycleViewAdapter);

        swipeController = new SwipeController(mainRecycleViewAdapter);
        itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(mainRecyclerView);

//        myTimer = new Timer();
//        myTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                OpenDataController openData = OpenDataController.getInstance();
//                try {
//                    openData.updateAllFeeds();
////                    for (GtfsRealtime.FeedEntity entity: openData.getTripEntities()) {
//////                        Log.i("tripdata", entity.toString());
////                    }
//                } catch (Exception e) {
//                    Log.i("tripdata", "something went wrong");
//                    e.printStackTrace();
//                }
//            }
//        }, 0, 30000);

        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("bus");
//
//        BusCapacity newBus = new BusCapacity("1234", "321", 15);
//        myRef.setValue(newBus);
    }

    @Override
    protected void onStart() {
        super.onStart();
        OpenDataController openData = OpenDataController.getInstance();
        openData.updateAllFeeds();
//        Route route = new Route("8");
//        Intent intent = new Intent(this, MapActivity.class);
//        intent.putExtra(ROUTE_ID_EXTRA, 8);
//        startActivity(intent);
    }

//    public void othermethod(){
//
//    }
}
