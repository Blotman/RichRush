package com.nguy.richrush.core;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    private final static String TAG = "DataUtils";

    public interface RestaurantDataListener {
        void onReceived();
    }

    private static RestaurantDataListener sRestaurantDataListener = null;

    private static final List<RestaurantData> sRestaurantsList = new ArrayList<>();

    public static void setRestaurantDataListener(RestaurantDataListener listener) {
        sRestaurantDataListener = listener;
    }

    public static void executeGetRestaurants(String latitude, String longitude) {
        new GetRestaurantsTask().execute(latitude, longitude);
    }

    public static void notifyRestaurantDataReceived() {
        if (sRestaurantDataListener != null) {
            sRestaurantDataListener.onReceived();
        }
    }

    public static int getRestaurantsCount() {
        return sRestaurantsList.size();
    }

    public static RestaurantData getRestaurantData(int index) {
        synchronized (sRestaurantsList) {
            return index >= 0 && index < sRestaurantsList.size() ? sRestaurantsList.get(index) : null;
        }
    }

    public static void resetRestaurantData(String restaurantListJson) {
        synchronized (sRestaurantsList) {
            sRestaurantsList.clear();
            try {
                final JSONArray root = new JSONArray(restaurantListJson);
                final int rootLength = root.length();
                for (int i = 0; i < rootLength; i++) {
                    final JSONObject jsonObject = root.getJSONObject(i);
                    sRestaurantsList.add(new RestaurantData(jsonObject));
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private static class GetRestaurantsTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            final String restaurantListJson;
            if (params.length > 1) {
                final String url = "https://api.doordash.com/v2/restaurant/?lat=" + params[0] + "&lng=" + params[1];
                restaurantListJson = NetworkUtils.sendGetRequest(url);
            } else {
                restaurantListJson = "";
            }
            resetRestaurantData(restaurantListJson);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            notifyRestaurantDataReceived();
        }
    }
}
