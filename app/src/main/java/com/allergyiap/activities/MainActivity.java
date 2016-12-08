package com.allergyiap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allergyiap.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLinearLayouts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "onCreateOptionsMenu");
        //return super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        // Return true to display menu
        return true;
    }

    private void initLinearLayouts() {

        CardView map = (CardView) (findViewById(R.id.linear_map)).findViewById(R.id.cardlist_item);
        CardView config = (CardView) (findViewById(R.id.linear_config)).findViewById(R.id.cardlist_item);
        CardView product = (CardView) (findViewById(R.id.linear_product)).findViewById(R.id.cardlist_item);

        map.setCardBackgroundColor(getResources().getColor(R.color.red));
        config.setCardBackgroundColor(getResources().getColor(R.color.green));
        product.setCardBackgroundColor(getResources().getColor(R.color.purple));

        ((ImageView)map.findViewById(R.id.menu_image)).setImageResource(R.drawable.map);
        ((ImageView)config.findViewById(R.id.menu_image)).setImageResource(R.drawable.config);
        ((ImageView)product.findViewById(R.id.menu_image)).setImageResource(R.drawable.product_catalog);

        ((TextView) map.findViewById(R.id.menu_name)).setText(getResources().getString(R.string.menu_map));
        ((TextView) config.findViewById(R.id.menu_name)).setText(getResources().getString(R.string.menu_config));
        ((TextView) product.findViewById(R.id.menu_name)).setText(getResources().getString(R.string.menu_product));

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
}
