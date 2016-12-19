package com.allergyiap.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.allergyiap.BuildConfig;
import com.allergyiap.R;
import com.allergyiap.utils.D;
import com.allergyiap.utils.Prefs;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private static String TAG = "ConfigurationActivity";

    private Button btnWeek;
    private Button btnHour;
    private Button btnAllergy;

    Switch sound;
    Switch alarm;
    Switch increase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sound = (Switch) findViewById(R.id.enableSound);
        alarm = (Switch) findViewById(R.id.enableAlarm);
        increase = (Switch) findViewById(R.id.alarmIncrease);

        btnWeek = (Button) findViewById(R.id.daysOfWeek);
        btnHour = (Button) findViewById(R.id.hour);
        btnAllergy = (Button) findViewById(R.id.allergy);

        sound.setOnCheckedChangeListener(this);
        alarm.setOnCheckedChangeListener(this);
        increase.setOnCheckedChangeListener(this);

        btnWeek.setOnClickListener(this);
        btnHour.setOnClickListener(this);
        btnAllergy.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Boolean alarm_enable = Prefs.getInstance(context).getAlarmEnabled();
        alarm.setChecked(alarm_enable);

        Boolean increase_enable = Prefs.getInstance(context).getAlarmIncrease();
        increase.setChecked(increase_enable);

        Boolean sound_enable = Prefs.getInstance(context).getSoundEnabled();
        sound.setChecked(sound_enable);

        String text = "";
        List result = Prefs.getInstance(context).getDaysWeek();
        if (result != null) {
            String[] items_s = getResources().getStringArray(R.array.string_array_short_days_week);
            for (int i = 0; i < result.size(); i++) {
                Double d = (Double)result.get(i);
                int indice = d.intValue();
                text += items_s[indice] + " ";
            }
        }
        btnWeek.setText(text);
        btnHour.setText(Prefs.getInstance(context).getHourAlarm());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_info:
                showDialogInfo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogInfo() {
        String versionName = BuildConfig.VERSION_NAME;
        String title = getString(R.string.title_version_app);
        String message = getString(R.string.version_app, versionName);
        D.showSimpleDialog(this, title, message);
    }

    public void onClickSaveChanges(View v) {
        Toast teste = Toast.makeText(getApplicationContext(), "Changes correctly saved", Toast.LENGTH_LONG);
        teste.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.enableSound:
                Prefs.getInstance(context).setSoundEnabled(isChecked);
                break;
            case R.id.enableAlarm:
                toggleButtonsAlarm(isChecked);
                break;
            case R.id.alarmIncrease:
                Prefs.getInstance(context).setAlarmIncrease(isChecked);
                break;
        }
    }

    private void toggleButtonsAlarm(boolean isChecked) {
        btnWeek.setEnabled(isChecked);
        btnHour.setEnabled(isChecked);
        Prefs.getInstance(context).setAlarmEnabled(isChecked);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.daysOfWeek:

                //String[] items = {" Monday ", " Tuesday ", " Wednesday ", " Thursday ", " Friday ", " Saturday ", " Sunday "};
                String[] items = getResources().getStringArray(R.array.string_array_days_week);
                List<Integer> selected = Prefs.getInstance(context).getDaysWeek();
                D.showWeekDaysDialog(this, items, selected);
                break;
            case R.id.hour:
                D.showHourDialog(this);
                break;
            case R.id.allergy:
                String[] allergies = getResources().getStringArray(R.array.string_array_allergies);
                List<String> allergies_selected = Prefs.getInstance(context).getAllergies();

                D.showAllergyDialog(this, allergies, allergies_selected);
                break;
        }
    }

    public void setTextButtonHour(String result) {
        Prefs.getInstance(context).setHourAlarm(result);
        btnHour.setText(result);
    }

    public void setTextButtonDays(List<Integer> result) {

        Prefs.getInstance(context).setDaysWeek(result);

        String text = "";
        String[] items_s = getResources().getStringArray(R.array.string_array_short_days_week);
        for (int i = 0; i < result.size(); i++) {
            int indice = (int) result.get(i);
            text += items_s[indice] + " ";
        }
        btnWeek.setText(text);
    }

    public void setAllergies(List<String> result) {
        Prefs.getInstance(context).setAllergies(result);
    }
}
