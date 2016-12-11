package com.allergyiap.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by lfernando on 08/12/2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    private DBHelper(Context context) {
        super(context, "allergyiap", null, 4);
    }

    private static DBHelper db;
    private static Context context;

    static public Context getCurrentContext() {
        return DBHelper.context;
    }

    static public DBHelper getDBHelper(Context context) {
        if (DBHelper.db == null) {
            DBHelper.context = context;
            DBHelper.db = new DBHelper(context);
        }
        return DBHelper.db;
    }

    public void onCreate(SQLiteDatabase db) {
        String queries = "";
        queries += "CREATE TABLE IF NOT EXISTS allergy (\r\n idallergy INTEGER PRIMARY KEY AUTOINCREMENT ,\r\n allergy_name TEXT ,\r\n allergy_description TEXT ,\r\n allergy_code TEXT );\r\n\r\n";
        queries += "CREATE TABLE IF NOT EXISTS allergy_level (\r\n idallergy_level INTEGER PRIMARY KEY AUTOINCREMENT ,\r\n allergy_idallergy INTEGER NOT NULL ,\r\n current_level FLOAT ,\r\n station TEXT ,\r\n date_start DATE ,\r\n date_end DATE ,\r\n forecast_level TEXT ,\r\n FOREIGN KEY(allergy_idallergy)\r\n REFERENCES allergy(idallergy)\r\n ON DELETE CASCADE\r\n ON UPDATE CASCADE);\r\n\r\n";
        queries += "REPLACE INTO allergy VALUES (1, 'Urticaceae', NULL, 'URTI');\r\nREPLACE INTO allergy VALUES (2, 'Gramineae (Poaceae)', NULL, 'GRAM');\r\nREPLACE INTO allergy VALUES (3, 'Olea', NULL, 'OLEA');\r\nREPLACE INTO allergy VALUES (4, 'Artemisia', NULL, 'ARTE');\r\nREPLACE INTO allergy VALUES (5, 'Chenopodiaceae/Amaranthaceae', NULL, 'QUAM');\r\nREPLACE INTO allergy VALUES (6, 'Erica', NULL, 'ERIC');\r\nREPLACE INTO allergy VALUES (7, 'Casuarina', NULL, 'CASU');\r\nREPLACE INTO allergy VALUES (8, 'Compositae (Asteraceae)', NULL, 'COMP');\r\nREPLACE INTO allergy VALUES (9, 'Cruciferae (Brassicaceae)', NULL, 'CRUC');\r\nREPLACE INTO allergy VALUES (10, 'Mercurialis', NULL, 'MERC');\r\nREPLACE INTO allergy VALUES (11, 'Palmae (Arecaceae)', NULL, 'PALM');\r\nREPLACE INTO allergy VALUES (12, 'Cupressaceae', NULL, 'CUPR');\r\n";
        queries += "CREATE TABLE IF NOT EXISTS stations (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, latitude TEXT , longitude TEXT);\r\n";
        queries += "REPLACE INTO stations(name,latitude,longitude) VALUES('Barcelona','41.393728','2.164922');\r\nREPLACE INTO stations(name,latitude,longitude) VALUES('Bellaterra','41.500604','2.108034');\r\nREPLACE INTO stations(name,latitude,longitude) VALUES('Girona','41.984172','2.823250');\r\nREPLACE INTO stations(name,latitude,longitude) VALUES('Lleida','41.628333','0.595556');\r\nREPLACE INTO stations(name,latitude,longitude) VALUES('Manresa','41.720183','1.839867');\r\nREPLACE INTO stations(name,latitude,longitude) VALUES('Roquetes','40.820272','0.493232');\r\nREPLACE INTO stations(name,latitude,longitude) VALUES('Tarragona','41.120039','1.243864');\r\nREPLACE INTO stations(name,latitude,longitude) VALUES('Vielha','42.702339','0.797429');\r\nREPLACE INTO stations(name,latitude,longitude) VALUES('Planes de Son','42.617778','1.079444');\r\n";
        queries += "CREATE TABLE IF NOT EXISTS customer (\r\n idcustomer INTEGER PRIMARY KEY AUTOINCREMENT,\r\n company_name TEXT DEFAULT '' NOT NULL ,\r\n pharmacy_location TEXT DEFAULT '' NOT NULL);\r\n";
        queries += "CREATE TABLE IF NOT EXISTS product_catalog (\r\n idproduct_catalog INTEGER PRIMARY KEY AUTOINCREMENT ,\r\n allergy_idallergy INTEGER NOT NULL ,\r\n customer_idcustomer INTEGER NOT NULL ,\r\n product_name TEXT ,\r\n product_description TEXT ,\r\n FOREIGN KEY(customer_idcustomer)\r\n REFERENCES customer(idcustomer)\r\n ON DELETE CASCADE\r\n ON UPDATE RESTRICT,\r\n FOREIGN KEY(allergy_idallergy)\r\n REFERENCES allergy(idallergy)\r\n ON DELETE RESTRICT\r\n ON UPDATE RESTRICT);\r\n\r\n";
        queries += "CREATE TABLE IF NOT EXISTS data_version (\r\n iddata_version INTEGER PRIMARY KEY AUTOINCREMENT ,\r\n name TEXT ,\r\n last_update DATE);\r\n\r\n";
        for (String query : queries.split(";")) {
            if (query.length() > 7) {
                db.execSQL(query);
            }
        }
    }

    public boolean getLastUpdate(String name, int days) {
        String strDays = String.valueOf(days);
        JSONArray t = getQuery("SELECT * FROM data_version WHERE name = '" + name + "' AND DATE('NOW') < DATE(last_update,'" + strDays + " DAYS')");
        if (t.length() > 0) {
            return true;
        }
        return false;
    }

    public void setLastUpdateToNow(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM data_version WHERE name = '" + name + "'");
        db.execSQL("INSERT INTO data_version(name,last_update) SELECT '" + name + "',DATE('NOW')");
    }

    public void insertJson(JSONObject jsonObj, String table) throws JSONException {
        String sqlKeys = "";
        String sqlValues = "";
        for (Iterator iterator = jsonObj.keys(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            if (!sqlKeys.isEmpty()) {
                sqlKeys = sqlKeys + ",";
                sqlValues = sqlValues + ",";
            }
            sqlKeys = sqlKeys + key;
            sqlValues = sqlValues + "'" + jsonObj.get(key).toString() + "'";
        }
        String query = "REPLACE INTO " + table + "(" + sqlKeys + ") VALUES(" + sqlValues + ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

    public JSONArray getQuery(String selectQuery) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> m = new HashMap<String, String>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    m.put(cursor.getColumnName(i), cursor.getString(i));
                }
                list.add(m);
            } while (cursor.moveToNext());
        }
        return Util.mapToJsonArray(list);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        onCreate(database);
    }
}