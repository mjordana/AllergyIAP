package com.allergyiap.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.allergyiap.R;
import com.allergyiap.utils.DBHelper;

/**
 * Working like a preloader
 **/
public class LaunchScreenActivity extends BaseActivity {

    static final String TAG = "LaunchScreen";
    private AsyncTask<Void, Void, Boolean> task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG + ".onCreate", ".");
        super.onCreate(savedInstanceState);

        /*this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        getSupportActionBar().hide();

        setContentView(R.layout.activity_launch_screen);

        task = new BackgroundTask().execute();
    }

    public void showResult() {
        Intent intent = new Intent(LaunchScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                Thread.sleep(3000); //solo para pruebas
                //check Internet
                //if (!CommonServices.getInstance(context).checkInternet())
                //    return null;

                //check if google services its ok
                //if (!CommonServices.getInstance(context).checkServicesGoogle(LaunchScreenActivity.this))
                //    return Boolean.FALSE;

                return Boolean.TRUE;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
//          Pass your loaded data here using Intent

            showResult();
        }
    }
}
