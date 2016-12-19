package com.allergyiap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allergyiap.R;

/**
 * Created by fahmi on 12/18/16.
 */

public class LoginActivity extends AppCompatActivity {
    EditText user_name, password;
    Button login, signup;
    TextView forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        user_name = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);

        forgot = (TextView) findViewById(R.id.forgot);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(user_name.getText().toString().equals("admin@allergy.com") && password.getText().toString().equals("admin")){
                    Toast.makeText(getApplicationContext(), "Redirecting ...", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent f = new Intent(getApplicationContext(),HelpActivity.class);
                startActivity(f);
            }
        });
    }
}
