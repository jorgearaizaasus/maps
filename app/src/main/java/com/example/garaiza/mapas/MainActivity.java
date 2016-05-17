package com.example.garaiza.mapas;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.garaiza.mapas.cluster.MyClusterRenderer;
import com.example.garaiza.mapas.cluster.MyItem;
import com.example.garaiza.mapas.entities.AsincronousTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.ClusterManager;
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ArrayAdapter<String> mAdapter;
    private CoordinatorLayout coordinatorLayout;
    Button btnShowLocation;
    GoogleMap googleMap;
    boolean enableGps = false;
    public LatLng latLng;
    double DEFAULT_LATITUTE = 21.8887303;
    double DEFAULT_LONGITUDE = -102.2480241;
    // Declare a variable for the cluster manager.
    ClusterManager<MyItem> mClusterManager;
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsincronousTask asyn = new AsincronousTask(this);
        asyn.execute();
    }

    public void showSnackBar(){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Message is deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });

        snackbar.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Polylines are useful for marking paths and routes on the map.
        setGoogleMap(map);
        setUpClusterer();
    }


    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void onCreate2(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your customize action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Log.d("permissions: ", "true");
                showSnackBar();
            }
        });
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        initializeMap();
    }


    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void initializeMap(){
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setUpClusterer() {
        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this, getGoogleMap());
        mClusterManager.setRenderer(new MyClusterRenderer(this, getGoogleMap(), mClusterManager));
        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        getGoogleMap().setOnCameraChangeListener(mClusterManager);
        getGoogleMap().setOnMarkerClickListener(mClusterManager);
        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {
        double lat;
        double lng;
        // Set some lat/lng coordinates to start with.
        if(isEnableGps()){
            lat = getLatLng().latitude;
            lng = getLatLng().longitude;
            getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(getLatLng(), 5));
        }else{
            lat = DEFAULT_LATITUTE;
            lng = DEFAULT_LONGITUDE;
            getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 5));
        }
        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 50; i++) {
            double offset = i / 100d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
    }

    public boolean isEnableGps() {
        return enableGps;
    }

    public void setEnableGps(boolean enableGps) {
        this.enableGps = enableGps;
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
