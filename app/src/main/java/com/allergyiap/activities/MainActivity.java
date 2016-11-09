package com.allergyiap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allergyiap.R;
import com.google.android.gms.vision.text.Line;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLinearLayouts();
    }

    private void initLinearLayouts() {

        CardView map = (CardView) (findViewById(R.id.linear_map)).findViewById(R.id.cardlist_item);
        CardView alarms = (CardView) (findViewById(R.id.linear_alarms)).findViewById(R.id.cardlist_item);
        CardView config = (CardView) (findViewById(R.id.linear_config)).findViewById(R.id.cardlist_item);
        CardView product = (CardView) (findViewById(R.id.linear_product)).findViewById(R.id.cardlist_item);

        map.setCardBackgroundColor(getResources().getColor(R.color.teal));
        alarms.setCardBackgroundColor(getResources().getColor(R.color.cyan));
        config.setCardBackgroundColor(getResources().getColor(R.color.blue_grey));
        product.setCardBackgroundColor(getResources().getColor(R.color.lime));

        ((ImageView)map.findViewById(R.id.menu_image)).setImageResource(R.drawable.icon_map);
        //((ImageView)alarms.findViewById(R.id.menu_image)).setImageResource(getResources().getDrawable(R.drawable.alarms));
        //((ImageView)config.findViewById(R.id.menu_image)).setImageResource(getResources().getDrawable(R.drawable.config));
        //((ImageView)product.findViewById(R.id.menu_image)).setImageResource(getResources().getDrawable(R.drawable.product));

        ((TextView) map.findViewById(R.id.menu_name)).setText(getResources().getString(R.string.menu_map));
        ((TextView) alarms.findViewById(R.id.menu_name)).setText(getResources().getString(R.string.menu_alarms));
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

    public void onClickMyAlarms(View v) {
        startActivity(new Intent(this, MyAlarmsActivity.class));
    }
}
