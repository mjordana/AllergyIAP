package com.allergyiap.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.allergyiap.R;
import com.allergyiap.utils.C;
import com.allergyiap.utils.DBHelper;
import com.allergyiap.utils.Prefs;

import java.util.Locale;

/**
 * Base class to all activities.
 */
public abstract class BaseActivity extends AppCompatActivity {

    // Application context
    Context context;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        context = getApplicationContext();
        db = DBHelper.getDBHelper(context);

        updateLocale();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // transparent toolbar android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    /**
     * Apply user language to current locale
     */
    protected void updateLocale() {
        // set current user language
        try {

            String langUser = Locale.getDefault().getLanguage();

            if (C.Lang.AVAILABLE.contains(langUser)) {
                Prefs.getInstance(context).setLanguage(langUser);
            } else {
                Prefs.getInstance(context).setLanguage(C.Lang.LANG_EN);
            }
        } catch (Exception e) {
            Log.e("BaseActivity.onCreate", "Set Language Exception: " + e.getMessage());
            Prefs.getInstance(context).setLanguage(C.Lang.LANG_EN);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
