package com.allergyiap.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.allergyiap.R;
import com.allergyiap.entities.StationEntity;
import com.allergyiap.services.AllergyLevelProxyClass;
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
    private HashMap<Integer, Marker> markers;

    private List<Integer> riskColors;

    private SimpleCursorAdapter mAdapter;

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

        final String[] from = new String[]{"cityName"};
        final int[] to = new int[]{android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(context,
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "onCreateOptionsMenu");
        //return super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_map, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.menu_search));
        searchView.setSuggestionsAdapter(mAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                focusCityInMap(position);
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                focusCityInMap(position);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                populateAdapter(newText);
                return false;
            }
        });

        return true;
    }

    private void focusCityInMap(int position) {
        Cursor cursor = mAdapter.getCursor();
        Integer in = cursor.getInt(position);
        StationEntity s = stations.get(in.intValue()-1);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(s.latitude, s.longitude), 14));

        Marker m = markers.get(s.id);
        if (m != null)
            m.showInfoWindow();
    }

    private void initStationsAllergies() {
        //stations = new ArrayList<>();
        allergies = new HashMap<>();
        markers = new HashMap<>();

        try {
            stations =  AllergyLevelProxyClass.getStations();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //stations.add(new StationEntity(1, "Lleida", 41.628333, 0.595556));
        //stations.add(new StationEntity(2, "Manresa", 41.720183, 1.839867));
        //stations.add(new StationEntity(3, "Barcelona", 41.393728, 2.164922));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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

    private Marker createMarker(double latitude, double longitude, String title) {
        // Add a marker in Sydney and move the camera
        LatLng pos = new LatLng(latitude, longitude);
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title(title)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

        return marker;

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
            Marker m = createMarker(s.latitude, s.longitude, s.name);
            allergies.put(m.getId(), s);
            markers.put(s.id, m);
        }
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        String id = marker.getId();

        StationEntity s = allergies.get(id);

        Intent intent = new Intent(this, MapAllergyLevelsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(C.IntentExtra.Sender.VAR_ALLERGY, s);
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


    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "cityName"});

        for (StationEntity station : stations) {

            if (station.name.toLowerCase().startsWith(query.toLowerCase()) || station.name.toLowerCase().contains(query.toLowerCase()))
                c.addRow(new Object[]{station.id, station.name});
        }

        mAdapter.changeCursor(c);
    }

}
