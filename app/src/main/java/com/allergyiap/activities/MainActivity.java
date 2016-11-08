package com.allergyiap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allergyiap.R;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
