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
import java.util.HashMap;
import java.util.Iterator;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by lfernando on 08/12/2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    private DBHelper(Context context) {
        super(context, "allergyiap", null, 1);
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
        String queries = "CREATE TABLE IF NOT EXISTS allergy (\r\n idallergy INTEGER PRIMARY KEY AUTOINCREMENT ,\r\n allergy_name TEXT ,\r\n allergy_description TEXT ,\r\n allergy_code TEXT );\r\n\r\nCREATE TABLE IF NOT EXISTS allergy_level (\r\n idallergy_level INTEGER PRIMARY KEY AUTOINCREMENT ,\r\n allergy_idallergy INTEGER NOT NULL ,\r\n current_level FLOAT ,\r\n station TEXT ,\r\n date_start DATE ,\r\n date_end DATE ,\r\n forecast_level TEXT ,\r\n FOREIGN KEY(allergy_idallergy)\r\n REFERENCES allergy(idallergy)\r\n ON DELETE CASCADE\r\n ON UPDATE CASCADE);\r\n\r\nCREATE TABLE IF NOT EXISTS product_catalog (\r\n idproduct_catalog INTEGER PRIMARY KEY AUTOINCREMENT ,\r\n allergy_idallergy INTEGER NOT NULL ,\r\n customer_idcustomer INTEGER NOT NULL ,\r\n product_name TEXT ,\r\n product_description TEXT ,\r\n FOREIGN KEY(customer_idcustomer)\r\n REFERENCES customer(idcustomer)\r\n ON DELETE CASCADE\r\n ON UPDATE RESTRICT,\r\n FOREIGN KEY(allergy_idallergy)\r\n REFERENCES allergy(idallergy)\r\n ON DELETE RESTRICT\r\n ON UPDATE RESTRICT);\r\n\r\nREPLACE INTO allergy VALUES (1, 'Urticaceae', NULL, 'URTI');\r\nREPLACE INTO allergy VALUES (2, 'Gramineae (Poaceae)', NULL, 'GRAM');\r\nREPLACE INTO allergy VALUES (3, 'Olea', NULL, 'OLEA');\r\nREPLACE INTO allergy VALUES (4, 'Artemisia', NULL, 'ARTE');\r\nREPLACE INTO allergy VALUES (5, 'Chenopodiaceae/Amaranthaceae', NULL, 'QUAM');\r\nREPLACE INTO allergy VALUES (6, 'Erica', NULL, 'ERIC');\r\nREPLACE INTO allergy VALUES (7, 'Casuarina', NULL, 'CASU');\r\nREPLACE INTO allergy VALUES (8, 'Compositae (Asteraceae)', NULL, 'COMP');\r\nREPLACE INTO allergy VALUES (9, 'Cruciferae (Brassicaceae)', NULL, 'CRUC');\r\nREPLACE INTO allergy VALUES (10, 'Mercurialis', NULL, 'MERC');\r\nREPLACE INTO allergy VALUES (11, 'Palmae (Arecaceae)', NULL, 'PALM');\r\nREPLACE INTO allergy VALUES (12, 'Cupressaceae', NULL, 'CUPR');\r\n";
        for (String query : queries.split(";")) {
            if (query.length() > 7) {
                db.execSQL(query);
            }
        }
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
        String query = "INSERT INTO " + table + "(" + sqlKeys + ") VALUES(" + sqlValues + ")";
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
    }
}