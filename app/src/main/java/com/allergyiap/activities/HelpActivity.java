package com.allergyiap.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.allergyiap.R;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailHelp;
    Button send, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        emailHelp = (EditText) findViewById(R.id.etEmailHelp);
        send = (Button) findViewById(R.id.btSend);
        cancel = (Button) findViewById(R.id.btCancel);

        send.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btSend:

                break;
            case R.id.btCancel:
                //startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                onBackPressed();
                break;
        }
    }
}
