package com.allergyiap.services;

import android.content.Context;

import com.allergyiap.entities.CustomerEntity;
import com.allergyiap.entities.ProductCatalogEntity;
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

public class ProductCatalogProxyClass {
    public static List<ProductCatalogEntity> getProductCatalog() throws Exception {
        Context context = DBHelper.getCurrentContext();
        DBHelper db = DBHelper.getDBHelper(context);

        if (!db.getLastUpdate("product_catalog", 7)) {
            // update it
            InputStream s = null;
            s = context.getAssets().open("product_catalogs.json");
            String jsonLevels = Util.convertStreamToString(s);

            JSONArray jsonObj = new JSONArray(jsonLevels);
            for (int i = 0; i < jsonObj.length(); i++) {
                JSONObject keyValue = jsonObj.getJSONObject(i);
                db.insertJson(keyValue, "product_catalog");
            }
        }

        JSONArray t = db.getQuery("SELECT * FROM product_catalog");
        ArrayList<ProductCatalogEntity> r = new ArrayList<ProductCatalogEntity>();
        for (int i = 0; i < t.length(); i++) {
            JSONObject keyValue = t.getJSONObject(i);
            r.add(ProductCatalogEntity.fromJson(keyValue));
        }
        return r;
    }
}