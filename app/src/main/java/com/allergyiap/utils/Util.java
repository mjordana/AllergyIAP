package com.allergyiap.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lfernando on 08/12/2016.
 */

public class Util {
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
    static public JSONArray mapToJsonArray(List<HashMap<String, String>> list)
    {
        JSONArray json_arr=new JSONArray();
        for (Map<String, String> map : list) {
            JSONObject json_obj=new JSONObject();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                try {
                    json_obj.put(key,value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            json_arr.put(json_obj);
        }
        return json_arr;
    }
}
