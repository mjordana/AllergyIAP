package com.allergyiap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allergyiap.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ibtn_map:
                startActivity( new Intent(this, MapActivity.class));
                break;
            case R.id.ibtn_product:
                startActivity( new Intent(this, ProductCatalogActivity.class));
                break;
            case R.id.ibtn_config:
                startActivity( new Intent(this, ConfigurationActivity.class));
                break;
            case R.id.ibtn_alarms:
                startActivity( new Intent(this, MyAlarmsActivity.class));
                break;
        }

    }
}
