package com.allergyiap.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.SparseArray;
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
import com.allergyiap.entities.StationEntity;
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

    private SparseArray<TextView[]> tabBadges;
    private TextView[] tabLabels;
    private Integer[] countBadges;

    private SimpleCursorAdapter mAdapter;
    private SearchView searchView;
    private MenuItem searchItem;


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

        getMenuInflater().inflate(R.menu.menu_main, menu);
        //getMenuInflater().inflate(R.menu.menu_map, menu);

        searchItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
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
        // Return true to display menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                share();
                break;
            case R.id.menu_item_config:
                initConfig();
                break;
            case R.id.menu_item_login:
                initLogin();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void initLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void initConfig() {
        startActivity(new Intent(this, ConfigurationActivity.class));
    }

    private void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>This is the text that will be shared.</p>"));
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    private void setupViewPager() {
        Log.v(TAG, ".setupViewPager");

        //viewPager.removeAllViews();
        //viewPager.removeAllViewsInLayout();
        //mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new com.allergyiap.activities.MapFragment(), getString(R.string.menu_map));
        adapter.addFrag(new ProductsFragment(), getString(R.string.menu_product));

        viewPager.setAdapter(adapter);

        initTabs();
    }

    private void initTabs() {

        countBadges = new Integer[2];
        //tabBadges = new TextView[tabLayout.getTabCount()][2];
        tabBadges = new SparseArray<>(tabLayout.getTabCount());
        tabLabels = new TextView[tabLayout.getTabCount()];

        for (int i = 0; i < 2; i++) {
            countBadges[i] = 0;

            View v = View.inflate(getBaseContext(), R.layout.tab_custom_icon, null);
            TextView badge = (TextView) v.findViewById(R.id.badge_lower_ten);
            TextView badge2 = (TextView) v.findViewById(R.id.badge_higher_ten);
            TextView label = (TextView) v.findViewById(R.id.tab_label);
            label.setText(tabLayout.getTabAt(i).getText() + " ");

            tabLayout.getTabAt(i).setCustomView(v);

            //tabBadges[i][0] = badge;
            //tabBadges[i][1] = badge2;
            TextView[] list = new TextView[2];
            list[0] = badge;
            list[1] = badge2;
            tabBadges.put(i, list);
            tabLabels[i] = label;

            setTabBadge(i, "0");
        }

        initEventTabSelected();
    }

    private void initEventTabSelected() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                tabLabels[tab.getPosition()].setTextColor(getResources().getColor(R.color.colorAccent));

                if(tab.getPosition() == 1){ //Product tab
                    searchItem.setVisible(false);
                } else{

                    searchItem.setVisible(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                if (tab != null)
                    tabLabels[tab.getPosition()].setTextColor(getResources().getColor(R.color.grey_20));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLabels[0].setTextColor(getResources().getColor(R.color.colorAccent));
    }

    public void setTabBadge(int i, String text) {

        TextView[] badge = tabBadges.get(i);
        badge[0].setVisibility(View.GONE);
        badge[1].setVisibility(View.GONE);

        Integer fin = Integer.parseInt(text);
        if (fin.intValue() > 9) {
            badge[1].setVisibility(View.VISIBLE);
        } else if (fin.intValue() > 0) {
            badge[0].setVisibility(View.VISIBLE);
        }

        badge[0].setText(text);
        badge[1].setText(text);
    }

    private void focusCityInMap(int position) {
        Cursor cursor = mAdapter.getCursor();
        Integer in = cursor.getInt(position);

        ViewPagerAdapter adp = (ViewPagerAdapter) viewPager.getAdapter();
        ((com.allergyiap.activities.MapFragment) adp.getItem(0)).focusCityInMap(in);

    }

    private void populateAdapter(String query) {
        //MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "cityName"});

        ViewPagerAdapter adp = (ViewPagerAdapter) viewPager.getAdapter();
        MatrixCursor c = ((com.allergyiap.activities.MapFragment) adp.getItem(0)).populateAdapter(query);

        mAdapter.changeCursor(c);
    }

    /**
     *
     * ViewPagerAdapter adp = (ViewPagerAdapter) viewPager.getAdapter();
     ((CompanyListFragment) adp.getItem(0)).setCompanies(companiesAndChats.aCompanies);
     ((ChatListFragment) adp.getItem(1)).setChats(companiesAndChats.aChats);
     */
}

