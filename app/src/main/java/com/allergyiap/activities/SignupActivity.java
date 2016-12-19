package com.allergyiap.activities;

/**
 * Created by fahmi on 12/18/16.
 */
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allergyiap.R;

import java.util.Objects;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    EditText etFullname, etEmail, etPassword, etConfirm;
    TextView AlreadyMember;
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        AlreadyMember = (TextView) findViewById(R.id.already);

        AlreadyMember.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent a = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(a);
            }
        });

        etFullname = (EditText) findViewById(R.id.nameId);
        etEmail = (EditText) findViewById(R.id.emailId);
        etPassword = (EditText) findViewById(R.id.passwordId);
        etConfirm = (EditText) findViewById(R.id.confirmId);


        register = (Button) findViewById(R.id.registerId);
        final String name = etFullname.getText().toString();
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String confirm = etConfirm.getText().toString();

       // NewUser saveData = new NewUser(name, email, password);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (!password.equals(confirm)){
                    Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_SHORT).show();

                } else if(name.isEmpty() || email.isEmpty()|| password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Form is empty", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Create account succesfull", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }



            }
        });

    }
}

