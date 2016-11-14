package com.allergyiap.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.allergyiap.R;
import com.allergyiap.entities.AllergyEntity;
import com.allergyiap.entities.StationEntity;
import com.allergyiap.utils.C;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter {

    static String TAG = "MapsActivity";
    public static final int PERMISSIONS_REQUEST_LOCATION = 100;

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location location;

    private List<StationEntity> stations;
    private HashMap<String, StationEntity> allergies;

    private List<Integer> riskColors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*locationManager = (LocationManager) this.getSystemService(context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_LONG).show();
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);
            }
        } else {
            Toast.makeText(this, "requestLocationUpdates", Toast.LENGTH_LONG).show();
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }*/

        initStationsAllergies();
        riskColors = new ArrayList<>();
        riskColors.add(getResources().getColor(R.color.blue));
        riskColors.add(getResources().getColor(R.color.green));
        riskColors.add(getResources().getColor(R.color.yellow));
        riskColors.add(getResources().getColor(R.color.orange));
        riskColors.add(getResources().getColor(R.color.red));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initStationsAllergies() {
        stations = new ArrayList<>();
        allergies = new HashMap<>();

        stations.add(new StationEntity(1, "Lleida", 41.628333, 0.595556));
        stations.add(new StationEntity(2, "Manresa", 41.720183, 1.839867));
        stations.add(new StationEntity(3, "Barcelona", 41.393728, 2.164922));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e(TAG, "");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    private String createMarker(double latitude, double longitude, String title) {
        // Add a marker in Sydney and move the camera
        LatLng pos = new LatLng(latitude, longitude);
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title(title)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

        return marker.getId();

        //marker.showInfoWindow();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        mMap.setInfoWindowAdapter(this);

        /*if(location != null) {
            createMarker(location.getLatitude(), location.getLongitude(), "Actual position");
        } else {
            createMarker(41.6167, 0.6333, "lleida");
        }*/

        for (StationEntity s : stations) {
            String id = createMarker(s.latitude, s.longitude, s.city);
            allergies.put(id, s);
        }
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        String id = marker.getId();

        StationEntity s = allergies.get(id);

        Intent intent = new Intent(this, MapAllergyLevelsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(C.IntentExtra.Sender.VAR_ALLERGY, s.id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * La interfaz contiene dos métodos que puedes implementar: getInfoWindow(Marker) y getInfoContents(Marker).
     * La API primero llamará a getInfoWindow(Marker).
     * Si el valor devuelto es null, llamará a getInfoContents(Marker).
     * Si el valor devuelto también es null, se usará la ventana de información predeterminada.
     *
     * @param marker
     * @return
     */
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


}
