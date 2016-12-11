package com.allergyiap.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lfernando on 11/12/2016.
 */

public class CustomerEntity extends Entity{
    public int idcustomer;
    public String company_name;
    public String pharmacy_location;

    public CustomerEntity(){

    }

    static public CustomerEntity fromJson(JSONObject obj) throws JSONException {
        CustomerEntity o = new CustomerEntity();
        o.idcustomer = Integer.parseInt(obj.get("idcustomer").toString());
        o.company_name = obj.get("company_name").toString();
        o.pharmacy_location = obj.get("pharmacy_location").toString();
        return o;
    }

}
