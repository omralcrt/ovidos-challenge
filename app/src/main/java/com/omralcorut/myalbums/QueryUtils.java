package com.omralcorut.myalbums;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by omral on 20.03.2017.
 */

public class QueryUtils {

    private static final String SAMPLE_JSON_RESPONSE = "";

    public QueryUtils(){}

    public static ArrayList<Album> extractAlbums() {

        //Result album arrayList
        ArrayList<Album> albums = new ArrayList<>();

        try {
            //Create a Json array
            JSONArray albumArray = new JSONArray(SAMPLE_JSON_RESPONSE);

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
