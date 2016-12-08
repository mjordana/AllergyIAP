package com.allergyiap.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Globalia-5 on 12/11/2016.
 */

public class StationEntity extends Entity {

    public int id;
    public String name;
    public double latitude;
    public double longitude;

    public StationEntity() {

    }

    public StationEntity(int id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    static public StationEntity fromJson(JSONObject obj) throws JSONException {
        StationEntity o = new StationEntity();
        o.id = Integer.parseInt(obj.get("id").toString());
        o.name = obj.get("name").toString();
        o.latitude = Double.parseDouble(obj.get("latitude").toString());
        o.longitude = Double.parseDouble(obj.get("longitude").toString());
        return o;
    }
}
