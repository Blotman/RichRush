package com.nguy.richrush.core;

import android.util.Log;

import org.json.JSONObject;

public class RestaurantData {
    final static String TAG = "RestaurantData";

    private final JSONObject jsonObject;
    public final int id;
    public final String name;
    public final String description;
    public final String coverImgUrl;
    public final String status;

    public RestaurantData(JSONObject jsonObject) {
        this.jsonObject = jsonObject;

        id = getInt(jsonObject, "id");
        name = getString(jsonObject, "name");
        description = getString(jsonObject, "description");
        coverImgUrl = getString(jsonObject, "cover_img_url");
        status = getString(jsonObject, "status");
    }

    private int getInt(JSONObject jsonObject, String name) {
        int value = -1;
        try {
            value = jsonObject.getInt(name);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return value;
    }

    private String getString(JSONObject jsonObject, String name) {
        String value = "";
        try {
            value = jsonObject.getString(name);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return value;
    }
}
