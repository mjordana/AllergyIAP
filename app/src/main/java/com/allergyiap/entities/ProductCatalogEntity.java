package com.allergyiap.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lfernando on 11/12/2016.
 */

public class ProductCatalogEntity extends Entity{
    public int idproduct_catalog;
    public int allergy_idallergy;
    public int customer_idcustomer;
    public String product_name;
    public String product_description;

    public ProductCatalogEntity(){

    }

    static public ProductCatalogEntity fromJson(JSONObject obj) throws JSONException {
        ProductCatalogEntity o = new ProductCatalogEntity();
        o.idproduct_catalog = Integer.parseInt(obj.get("idproduct_catalog").toString());
        o.allergy_idallergy = Integer.parseInt(obj.get("allergy_idallergy").toString());
        o.customer_idcustomer = Integer.parseInt(obj.get("customer_idcustomer").toString());
        o.product_name = obj.get("product_name").toString();
        o.product_description = obj.get("product_description").toString();
        return o;
    }

}
