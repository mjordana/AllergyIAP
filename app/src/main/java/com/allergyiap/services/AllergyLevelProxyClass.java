package com.allergyiap.services;

import android.content.Context;
import android.util.Log;

import com.allergyiap.entities.AllergyLevelEntity;
import com.allergyiap.entities.StationEntity;
import com.allergyiap.utils.C;
import com.allergyiap.utils.DBHelper;
import com.allergyiap.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by lfernando on 08/12/2016.
 */

public class AllergyLevelProxyClass {
    public static List<StationEntity> getStations() throws Exception {
        Context context = DBHelper.getCurrentContext();
        DBHelper db = DBHelper.getDBHelper(context);
        JSONArray t = db.getQuery("SELECT * FROM stations");
        ArrayList<StationEntity> r = new ArrayList<StationEntity>();
        for (int i = 0; i < t.length(); i++) {
            JSONObject keyValue = t.getJSONObject(i);
            r.add(StationEntity.fromJson(keyValue));
        }
        return r;
    }

    public static List<AllergyLevelEntity> getLevels(int stationId) throws Exception {
        String stationString = String.valueOf(stationId);
        Context context = DBHelper.getCurrentContext();
        DBHelper db = DBHelper.getDBHelper(context);
        JSONArray t = db.getQuery("SELECT allergy_level.*,allergy.allergy_name AS allergy_name FROM allergy_level INNER JOIN allergy ON allergy.idallergy = allergy_level.allergy_idallergy INNER JOIN stations ON stations.name = allergy_level.station WHERE stations.id=" + stationString + " AND date_start <= DATE('NOW') AND DATE('NOW') <= date_end ");
        if (t.length() == 0) {
            String jsonLevels = "";
            if (C.Network.useNetwork) {
                jsonLevels = Util.getUrl(C.Network.WS_URL + "/XarxaImportServlet");
            } else {
                jsonLevels = Util.getJson("levels.json");
            }
            JSONArray jsonObj = new JSONArray(jsonLevels);
            for (int i = 0; i < jsonObj.length(); i++) {
                JSONObject keyValue = jsonObj.getJSONObject(i);
                db.insertJson(keyValue, "allergy_level");
            }
            t = db.getQuery("SELECT allergy_level.*,allergy.allergy_name AS allergy_name FROM allergy_level INNER JOIN allergy ON allergy.idallergy = allergy_level.allergy_idallergy INNER JOIN stations ON stations.name = allergy_level.station WHERE stations.id=" + stationString + " AND date_start <= DATE('NOW') AND DATE('NOW') <= date_end ");
        }
        ArrayList<AllergyLevelEntity> r = new ArrayList<AllergyLevelEntity>();
        for (int i = 0; i < t.length(); i++) {
            JSONObject keyValue = t.getJSONObject(i);
            r.add(AllergyLevelEntity.fromJson(keyValue));
        }
        return r;
    }
}