package com.allergyiap.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Application Preferences Manager.
 */
public class Prefs {
    static final String TAG = "Prefs";

    private Context context;

    static Prefs prefs;

    public static Prefs getInstance(Context context) {
        if(prefs == null) {
            Log.e(TAG,"new instance");
            prefs = new Prefs(context);
        }
        return prefs;
    }

    private Prefs(Context context) {
        this.context = context;
    }

    /**
     * @return Default {@code SharedPreferences}.
     */
    private SharedPreferences defaultSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences sharedPrefs() {
        return context.getApplicationContext().getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    public int getAppVersionCode() {
        Log.v(TAG, ".getAppVersionCode");
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public int getRegisteredVersionCode(){
        Log.v(TAG, ".getRegisteredVersionCode");
        return sharedPrefs().getInt(C.GCM.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
    }

    /**
     * Remove register GCM
     */
    public void clearRegistrationId() {
        Log.v(TAG, ".clearRegistrationId");
        sharedPrefs().edit()
                .remove(C.GCM.PROPERTY_REG_ID)
                .remove(C.GCM.PROPERTY_APP_VERSION)
                .apply();
    }

    /**
     * @return id register GCM
     */
    public String getRegistrationId() {
        String registrationId = sharedPrefs().getString(C.GCM.PROPERTY_REG_ID, "");
        Log.d(TAG, ".getRegistrationId:" + registrationId);
        return registrationId;
    }


    public void setRegistrationId(String registrationId) {
        Log.d(TAG, ".setRegistrationId:" + registrationId);
        sharedPrefs().edit()
                .putString(C.GCM.PROPERTY_REG_ID, registrationId)
                .putInt(C.GCM.PROPERTY_APP_VERSION, getAppVersionCode())
                .apply();
    }

    /**
     * Language (DefaultSharedPrefs from PreferenceActivity)
     */
    public String getLanguage() {
        String language = defaultSharedPrefs().getString(C.Prefs.LANGUAGE, C.Lang.LANG_EN);
        Log.d(TAG, ".getLanguage" + language);
        return language;
    }

    public void setLanguage(String language) {
        Log.v(TAG, ".setLanguage:" + language);
        try {
            // save
            defaultSharedPrefs().edit().putString(C.Prefs.LANGUAGE, language).apply();
        } catch (Exception e) {
            Log.e(TAG, ".setLanguage Error: " + e.getMessage());
        }
    }

    public void setUserFirstTime(Boolean param) {
        sharedPrefs().edit().putBoolean(C.Prefs.USER_FIRST_TIME, param).apply();
    }
}
