package com.allergyiap.services;

import android.content.Context;

import com.allergyiap.entities.AllergyLevelEntity;
import com.allergyiap.entities.CustomerEntity;
import com.allergyiap.entities.StationEntity;
import com.allergyiap.utils.DBHelper;
import com.allergyiap.utils.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfernando on 08/12/2016.
 */

public class CustomerProxyClass {
    public static List<CustomerEntity> getCustomers() throws Exception {
        Context context = DBHelper.getCurrentContext();
        DBHelper db = DBHelper.getDBHelper(context);

        if (!db.getLastUpdate("customer", 7)) {
            // update it
            InputStream s = null;
            s = context.getAssets().open("customers.json");
            String jsonLevels = Util.convertStreamToString(s);

            JSONArray jsonObj = new JSONArray(jsonLevels);
            for (int i = 0; i < jsonObj.length(); i++) {
                JSONObject keyValue = jsonObj.getJSONObject(i);
                db.insertJson(keyValue, "customer");
            }
        }

        JSONArray t = db.getQuery("SELECT * FROM customer");
        ArrayList<CustomerEntity> r = new ArrayList<CustomerEntity>();
        for (int i = 0; i < t.length(); i++) {
            JSONObject keyValue = t.getJSONObject(i);
            r.add(CustomerEntity.fromJson(keyValue));
        }
        return r;
    }

    public static CustomerEntity getCustomerFromId(int idcustomer) throws Exception {
        List<CustomerEntity> customers = CustomerProxyClass.getCustomers();
        for (CustomerEntity customer : customers) {
            if (customer.idcustomer == idcustomer) {
                return customer;
            }
        }
        return null;
    }

}