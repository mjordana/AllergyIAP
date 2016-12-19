package com.allergyiap.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.allergyiap.R;
import com.allergyiap.entities.AllergyLevelEntity;
import com.allergyiap.entities.ProductCatalogEntity;
import com.allergyiap.utils.C;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class ProductCatalogMapActivity extends BaseActivity implements OnMapReadyCallback {
    private GoogleMap gMap;

    ProductCatalogEntity prod;

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


        //viewHolder.image.setImageResource(R.drawable.allergy_product);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        double latitude = 41.618002;
        double longitude = 0.628507;
        String title = prod.product_name;
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