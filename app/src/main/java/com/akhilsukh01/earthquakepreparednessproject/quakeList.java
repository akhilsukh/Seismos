package com.akhilsukh01.earthquakepreparednessproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class quakeList extends Activity {

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String TAG = "Checker";

    private FloatingActionButton mapFab;

    ArrayList<ExampleItem> exampleList = new ArrayList<>();
    int n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_quake_list);
        super.onCreate(savedInstanceState);
        //Auto-activity to fetch json info
//        fetchedData process = new fetchedData();
//        process.execute();
        //end

        exampleList.clear();
        final int counter = fetchedData.allDate.size();
        for (n = 0; n < counter; n++){
            exampleList.add(new ExampleItem(fetchedData.allPlacesF.get(n), fetchedData.allMagF.get(n), fetchedData.allDateS.get(n)));
        }
        mRecyclerView = findViewById(R.id.recycler_View);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mapFab = findViewById(R.id.mapFab);
        mapFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentI = new Intent(quakeList.this, quakeMap.class);
                startActivity(intentI);
            }
        });


        //add click to specific marker of place
//        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
////                Toast.makeText(quakeList.this, String.valueOf(exampleList.get(position)),Toast.LENGTH_SHORT).show();
//                Intent intentI = new Intent(quakeList.this, quakeMap.class);
//                startActivity(intentI);
//            }
//        });

        //TESTER
//        String testTotal = String.valueOf(fetchedData.allCoordinatesF);
//        tester.setText(userLocation);



        //Last Updated timestamp at the bottom of the list
        TextView mLastUpdated = findViewById(R.id.lastUpdated);
        String currentDateTimeString = ("Last Updated: " + DateFormat.getDateTimeInstance().format(new Date()));
        mLastUpdated.setText(currentDateTimeString);


        //Finding largest and total earthquakes for banner
        Double largest = fetchedData.allMagF.get(0);
        for(int i=1; i< fetchedData.allMagF.size(); i++)
        {
            if(fetchedData.allMagF.get(i) > largest)
                largest = fetchedData.allMagF.get(i);
        }

        TextView mLargest = findViewById(R.id.largest);
        mLargest.setText(String.valueOf(largest));



//        Button toMap = (Button) findViewById(R.id.toMap);
//        toMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentM = new Intent(quakeList.this, quakeMap.class);
//                startActivity(intentM);
//            }
//        });

        TextView mTotal = findViewById(R.id.total);
        mTotal.setText(String.valueOf(counter));



    }
}
