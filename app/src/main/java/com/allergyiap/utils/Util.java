package com.allergyiap.utils;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by lfernando on 08/12/2016.
 */

public class Util {
    public static class DownloadTask extends AsyncTask<URL, Void, String> {
        protected String doInBackground(URL... urls) {
            int count = urls.length;
            if (count > 0) {
                URL url = urls[0];
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    return Util.convertStreamToString(in);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }
            return "";
        }
    }

    public static String getUrl(String urlStr) throws Exception {
        java.net.URL url = new URL(urlStr);
        DownloadTask d = new DownloadTask();

        return d.execute(url).get();
    }

    public static String getJson(String file) throws Exception {
        Context context = DBHelper.getCurrentContext();
        InputStream s = context.getAssets().open(file);
        return Util.convertStreamToString(s);
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    static public JSONArray mapToJsonArray(List<HashMap<String, String>> list) {
        JSONArray json_arr = new JSONArray();
        for (Map<String, String> map : list) {
            JSONObject json_obj = new JSONObject();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                try {
                    json_obj.put(key, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            json_arr.put(json_obj);
        }
        return json_arr;
    }
}
