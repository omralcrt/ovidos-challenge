package com.omralcorut.myalbums;

import android.text.TextUtils;
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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omral on 20.03.2017.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    public QueryUtils(){}

    //Return list of album
    public static List<Album> fetchAlbumData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Album> albums = extractFeatureFromJson(jsonResponse);

        return albums;
    }

    //Return given URL object
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    //Helper method for making HTTP request
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the album JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //Helper method for converting input stream to string
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //Create album list with given string json
    private static List<Album> extractFeatureFromJson(String albumJSON) {

        if (TextUtils.isEmpty(albumJSON)) {
            return null;
        }

        //Result album arrayList
        List<Album> albums = new ArrayList<>();

        try {
            //Create a Json array
            JSONArray albumArray = new JSONArray(albumJSON);

            for (int i = 0; i < albumArray.length(); i++) {
                //Take a object in array
                JSONObject currentAlbum = albumArray.getJSONObject(i);

                //Extract values of album
                int userId = currentAlbum.getInt("userId");
                int id = currentAlbum.getInt("id");
                String title = currentAlbum.getString("title");

                //Create a new album
                Album album = new Album(userId, id, title);

                //Add album to arrayList
                albums.add(album);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the album JSON results", e);
        }
        return albums;
    }

}
