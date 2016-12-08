package com.allergyiap.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lfernando on 08/12/2016.
 */

public class AllergyLevelEntity {
    public String forecast_level;
    public String allergy_idallergy;
    public String date_start;
    public String current_level;
    public String station;
    public String idallergy_level;
    public String date_end;

    static public AllergyLevelEntity fromJson(JSONObject obj) throws JSONException {
        AllergyLevelEntity o = new AllergyLevelEntity();
        o.forecast_level = obj.get("forecast_level").toString();
        o.allergy_idallergy = obj.get("allergy_idallergy").toString();
        o.date_start = obj.get("date_start").toString();
        o.current_level = obj.get("current_level").toString();
        o.station = obj.get("station").toString();
        o.idallergy_level = obj.get("idallergy_level").toString();
        o.date_end = obj.get("date_end").toString();
        return o;
    }
}
