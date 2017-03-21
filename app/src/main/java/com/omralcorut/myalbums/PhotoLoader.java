package com.omralcorut.myalbums;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by omral on 21.03.2017.
 */

public class PhotoLoader extends AsyncTaskLoader<List<Photo>> {

    private String url;

    public PhotoLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    //Fetch all data of photo in the background
    @Override
    public List<Photo> loadInBackground() {
        if (url == null) {
            return null;
        }

        List<Photo> photos = QueryUtils.fetchPhotoData(url);
        return photos;
    }
}
