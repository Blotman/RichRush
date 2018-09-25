package com.nguy.richrush;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    public static String sendGetRequest(String urlString) {
        String response = "";
        try {
            final URL url = new URL(urlString);
            final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            final StringBuilder responseBuilder = new StringBuilder();

            final Reader inputStreamReader = new InputStreamReader(urlConnection.getInputStream(),"utf-8");
            final BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                responseBuilder
                        .append(line)
                        .append("\n");
            }
            bufferReader.close();
            response = responseBuilder.toString();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return response;
    }
}
