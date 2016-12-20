package com.allergyiap.activities;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allergyiap.R;
import com.allergyiap.entities.CustomerEntity;
import com.allergyiap.entities.ProductCatalogEntity;
import com.allergyiap.entities.StationEntity;
import com.allergyiap.services.AllergyLevelProxyClass;
import com.allergyiap.services.CustomerProxyClass;
import com.allergyiap.services.ProductCatalogProxyClass;
import com.allergyiap.utils.C;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter {

    static String TAG = "MapFragment";

    private GoogleMap mMap;
    MapView mMapView;

    private List<StationEntity> stations;
    private List<CustomerEntity> stores;
    private HashMap<String, StationEntity> allergies;
    //private HashMap<String, CustomerEntity> customers;
    private HashMap<Integer, Marker> markers;

    MainActivity activity;
    Context context;

    private AsyncTask<Void, Void, List<StationEntity>> task;
    private AsyncTask<Void, Void, List<CustomerEntity>> taskStores;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG + ".onCreate", ".");
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();
        context = getActivity().getApplicationContext();

        activity.updateLocale();

        allergies = new HashMap<>();
        markers = new HashMap<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        return v;
    }

    private Marker createMarker(double latitude, double longitude, String title) {

        return createMarker(latitude, longitude, title, BitmapDescriptorFactory.HUE_AZURE);

        // Add a marker in Sydney and move the camera
        /*LatLng pos = new LatLng(latitude, longitude);
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title(title)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

        return marker;*/

        //marker.showInfoWindow();
    }

    private Marker createMarker(double latitude, double longitude, String title, float icon) {
        // Add a marker in Sydney and move the camera
        LatLng pos = new LatLng(latitude, longitude);
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title(title)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(icon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

        return marker;

        //marker.showInfoWindow();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

        if (task != null) {
            task.cancel(true);
            task = null;
        }

        if (taskStores != null) {
            taskStores.cancel(true);
            taskStores = null;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        mMap.setInfoWindowAdapter(this);

        // For showing a move to my location button
        //mMap.setMyLocationEnabled(true);

        /*if(location != null) {
            createMarker(location.getLatitude(), location.getLongitude(), "Actual position");
        } else {
            createMarker(41.6167, 0.6333, "lleida");
        }*/

        loadData();
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        String id = marker.getId();

        StationEntity s = allergies.get(id);

        if (s != null) {

            Intent intent = new Intent(activity, MapAllergyLevelsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(C.IntentExtra.Sender.VAR_ALLERGY, s);
            intent.putExtras(bundle);
            startActivity(intent);
        }
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

    private void loadData() {

        task = new LoadStationsBT();
        task.execute();

        taskStores = new LoadStoresBT();
        taskStores.execute();
    }

    private class LoadStationsBT extends AsyncTask<Void, Void, List<StationEntity>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        }

        @Override
        protected List<StationEntity> doInBackground(Void... params) {

            List<StationEntity> stations = new ArrayList<>();
            try {
                stations = AllergyLevelProxyClass.getStations();
            } catch (Exception e) {
                Log.e(TAG, "LoadStationsStoreBT", e);
            }

            return stations;
        }

        @Override
        protected void onPostExecute(List<StationEntity> result) {
            super.onPostExecute(result);

            //activity.findViewById(R.id.progress_bar).setVisibility(View.GONE);

            if (result != null && !result.isEmpty()) {
                stations = result;
                for (StationEntity s : stations) {
                    Marker m = createMarker(s.latitude, s.longitude, s.name);
                    allergies.put(m.getId(), s);
                    markers.put(s.id, m);
                }
            }

            //init task to load pharmacies
            //taskStores = new LoadStoresBT();
            //taskStores.execute();
        }
    }

    private class LoadStoresBT extends AsyncTask<Void, Void, List<CustomerEntity>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        }

        @Override
        protected List<CustomerEntity> doInBackground(Void... params) {

            List<CustomerEntity> customers = new ArrayList<>();
            try {
                customers = CustomerProxyClass.getCustomers();
            } catch (Exception e) {
                Log.e(TAG, "LoadStoresBT", e);
            }

            return customers;
        }

        @Override
        protected void onPostExecute(List<CustomerEntity> result) {
            super.onPostExecute(result);

            activity.findViewById(R.id.progress_bar).setVisibility(View.GONE);

            if (result != null && !result.isEmpty()) {
                stores = result;
                for (CustomerEntity c : stores) {

                    String[] position = String.valueOf(c.pharmacy_location).split(";");

                    createMarker(Double.valueOf(position[0]), Double.valueOf(position[1]), c.company_name, BitmapDescriptorFactory.HUE_RED);
                    //customers.put(m.getId(), c);
                    //markers.put(c.idcustomer, m);
                }
            }
        }
    }

    public void focusCityInMap(Integer in) {

        StationEntity s = stations.get(in.intValue() - 1);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(s.latitude, s.longitude), 14));

        Marker m = markers.get(s.id);
        if (m != null)
            m.showInfoWindow();
    }

    public MatrixCursor populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "cityName"});

        for (StationEntity station : stations) {
            //Log.d(TAG, station.id + " - " + station.name);
            //if (station.name.toLowerCase().startsWith(query.toLowerCase()))
            if (station.name.toLowerCase().contains(query.toLowerCase()))
                c.addRow(new Object[]{station.id, station.name});
        }

        return c;
    }
}
