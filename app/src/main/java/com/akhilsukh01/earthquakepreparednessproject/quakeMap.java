package com.akhilsukh01.earthquakepreparednessproject;

import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class quakeMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;
    private Marker myMarker;
    private static final String TAG = "quakeMap";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(false);


        int counter = fetchedData.counter;
        LatLng pin;
        mMap.clear();
        for (int n = 0; n < counter; n++) {

            mMap.setOnMarkerClickListener(this);
            pin = new LatLng(fetchedData.allLat.get(n), fetchedData.allLong.get(n));
            mMap.addMarker(new MarkerOptions()
                    .position(pin)
                    .title(fetchedData.allPlaces.get(n))
                    .flat(true)
                    .alpha(0)
                    .snippet(String.valueOf(fetchedData.allMag.get(n)) + " - " + fetchedData.allDateS.get(n)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(fetchedData.allLat.get(0), fetchedData.allLong.get(0))), 3));
            int rings = (int) Math.round(fetchedData.allMag.get(n));

            mMap.addCircle(new CircleOptions()
                    .center(new LatLng(fetchedData.allLat.get(n), fetchedData.allLong.get(n)))
                    .radius(fetchedData.allMag.get(n) * (25000))
                    .strokeWidth(7)
                    .fillColor(Color.RED)
                    .strokeColor(Color.RED));

            for (int asd = 1; asd <= rings; asd++)
            {
                mMap.addCircle(new CircleOptions()
                        .center(new LatLng(fetchedData.allLat.get(n), fetchedData.allLong.get(n)))
                        .radius(asd * (100000))
                        .strokeWidth(5)
                        .strokeColor(Color.RED));

            }
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

//        Toast.makeText(this, "Changed",Toast.LENGTH_SHORT).show();

        return false;
    }
}