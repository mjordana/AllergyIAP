package com.allergyiap.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allergyiap.R;
import com.allergyiap.adapters.ViewPagerAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    private ShareActionProvider mShareActionProvider;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*initLinearLayouts();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_map:
                                Toast.makeText(getApplicationContext(), "Map", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_config:
                                Toast.makeText(getApplicationContext(), "Config", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_product:
                                Toast.makeText(getApplicationContext(), "Product", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });

        ImageView icon_map = (ImageView) findViewById(R.id.map_image);
        icon_map.setColorFilter(Color.parseColor("#2196F3"));

        final TextView textView = (TextView) findViewById(R.id.menu_name);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                LatLng pos = new LatLng(41.6167, 0.6333);
                mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                textView.setText("Lleida");
            }
        });*/

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupViewPager();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "onCreateOptionsMenu");
        //return super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Return true to display menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                share();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>This is the text that will be shared.</p>"));
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    private void initLinearLayouts() {

        //CardView map = (CardView) (findViewById(R.id.linear_map)).findViewById(R.id.cardlist_item);
        //CardView config = (CardView) (findViewById(R.id.linear_config)).findViewById(R.id.cardlist_item);
        //CardView product = (CardView) (findViewById(R.id.linear_product)).findViewById(R.id.cardlist_item);

        //map.setCardBackgroundColor(getResources().getColor(R.color.red));
        //config.setCardBackgroundColor(getResources().getColor(R.color.green));
        //product.setCardBackgroundColor(getResources().getColor(R.color.purple));

        //((ImageView)map.findViewById(R.id.menu_image)).setImageResource(R.drawable.map);
        //((ImageView)config.findViewById(R.id.menu_image)).setImageResource(R.drawable.config);
        //((ImageView)product.findViewById(R.id.menu_image)).setImageResource(R.drawable.product_catalog);

        //((TextView) map.findViewById(R.id.menu_name)).setText(getResources().getString(R.string.menu_map));
        //((TextView) config.findViewById(R.id.menu_name)).setText(getResources().getString(R.string.menu_config));
        //((TextView) product.findViewById(R.id.menu_name)).setText(getResources().getString(R.string.menu_product));

    }

    public void onClickMap(View v) {
        startActivity(new Intent(this, MapActivity.class));
    }

    public void onClickProductCatalog(View v) {
        startActivity(new Intent(this, ProductCatalogActivity.class));
    }

    public void onClickConfiguration(View v) {
        startActivity(new Intent(this, ConfigurationActivity.class));
    }

    private void setupViewPager() {
        Log.v(TAG, ".setupViewPager");

        viewPager.removeAllViews();
        viewPager.removeAllViewsInLayout();
        //mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new com.allergyiap.activities.MapFragment(), getString(R.string.menu_map));
        adapter.addFrag(new ProductsFragment(), getString(R.string.menu_product));

        viewPager.setAdapter(adapter);

        //initTabs();
    }
}
