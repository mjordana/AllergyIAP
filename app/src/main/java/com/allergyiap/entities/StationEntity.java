package com.allergyiap.entities;

/**
 * Created by Globalia-5 on 12/11/2016.
 */

public class StationEntity extends Entity {

    public int id;
    public String city;
    public double latitude;
    public double longitude;

    public StationEntity() {
    }

    public StationEntity(int id, String city, double latitude, double longitude){
        this.id = id;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
