package com.example.samson.githubusers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMSON on 7/19/2017.
 */

public class NetworkUtil {


    static String PARAM_QUERY = "query";
    static String SORT = "sort";
    static final String LOGCAT = NetworkUtil.class.getSimpleName();

//    public List<Profile> makeRequest(String reqUrl, String query, String sortLocation) {
    public List<Profile> makeRequest(String reqUrl){
        List<Profile> profileList = null;

        try {
//            Uri builtUri = Uri.parse(reqUrl).buildUpon().appendQueryParameter(PARAM_QUERY, query)
//                    .appendQueryParameter(SORT, sortLocation).build();
//            URL url = createUrl(builtUri.toString());
//            Log.i(LOGCAT, "Creating url: "+ url.toString());
            URL url = createUrl(reqUrl);
            String jsonStr = makeHttpRequest(url);

            Log.i(LOGCAT, "HttpRequest made successfully....: " + jsonStr);

            profileList = readJson(jsonStr);

        } catch (Exception e) {
            Log.e(LOGCAT, "Error in : " + e.toString(), e);
        }

        return profileList;
    }

    public URL createUrl(String reqUrl) {
        URL url = null;
        try {

            url = new URL(reqUrl);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String makeHttpRequest(URL url) {
        String jsonStr = null;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();

                jsonStr = readStream(inputStream);
            } else {
                Log.e(LOGCAT, "Error code is : " + urlConnection.getResponseCode());
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return jsonStr;
    }

    public String readStream(InputStream ins) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i(LOGCAT, "The String: " + builder.toString());
        return builder.toString();
    }

    public List<Profile> readJson(String jsonStr) {
        List<Profile> profileList = new ArrayList<>();

        try {
            JSONObject githubObject = new JSONObject(jsonStr);

            JSONArray itemArray = githubObject.getJSONArray("items");
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject itemObject = itemArray.getJSONObject(i);

                Long id = itemObject.getLong("id");
                JSONObject ownerObject = itemObject.getJSONObject("owner");

                String username_login = ownerObject.getString("login");
                String link_url = ownerObject.getString("html_url");
                String avatar_url = ownerObject.getString("avatar_url");

                profileList.add(new Profile(username_login, avatar_url, link_url));
//                profileList.add(new Profile(String.valueOf(id), avatar_url, link_url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return profileList;
    }
}
