package com.allergyiap.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.allergyiap.R;

public class ConfigurationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
    }
    public void onClickSaveChanges(View v){
        Toast teste = Toast.makeText(getApplicationContext(), "Changes correctly saved", Toast.LENGTH_LONG);
        teste.show();

    }
}
