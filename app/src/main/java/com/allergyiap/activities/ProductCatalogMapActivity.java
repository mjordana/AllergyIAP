package com.allergyiap.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.allergyiap.R;
import com.allergyiap.entities.AllergyLevelEntity;
import com.allergyiap.entities.CustomerEntity;
import com.allergyiap.entities.ProductCatalogEntity;
import com.allergyiap.services.CustomerProxyClass;
import com.allergyiap.utils.C;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class ProductCatalogMapActivity extends BaseActivity implements OnMapReadyCallback {
    private static String TAG = "ProductCatalogMapActivity";

    private GoogleMap gMap;

    ProductCatalogEntity prod;
    private AsyncTask<Integer, Void, CustomerEntity> task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog_map);

        prod = (ProductCatalogEntity) getIntent().getSerializableExtra(C.IntentExtra.Sender.VAR_PRODUCT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ((TextView) findViewById(R.id.title)).setText(prod.product_name);
        ((TextView) findViewById(R.id.description)).setText(prod.product_description);
        //viewHolder.image.setImageResource(R.drawable.allergy_product);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (task != null) {
            task.cancel(true);
            task = null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;


        task = new LoadInfoBT();
        task.execute(prod.customer_idcustomer);

    }

    private class LoadInfoBT extends AsyncTask<Integer, Void, CustomerEntity> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        }

        @Override
        protected CustomerEntity doInBackground(Integer... params) {
            int id = params[0];
            try {
                return CustomerProxyClass.getCustomerFromId(id);
            } catch (Exception e) {
                Log.e(TAG, "", e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(CustomerEntity result) {
            super.onPostExecute(result);
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
            if (result != null)
                loadInfo(result);
        }
    }

    private void loadInfo(CustomerEntity result) {

        String[] aux = String.valueOf(result.pharmacy_location).split(";");

        double latitude = Double.parseDouble(aux[0]);
        double longitude = Double.parseDouble(aux[1]);
        String title = result.company_name;
        LatLng pos = new LatLng(latitude, longitude);
        Marker marker = gMap.addMarker(new MarkerOptions()
                .position(pos)
                .title(title)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

        marker.showInfoWindow();
    }
}