package com.omralcorut.myalbums;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by omral on 20.03.2017.
 */

public class AlbumLoader extends AsyncTaskLoader<List<Album>> {

    private String url;

    public AlbumLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Album> loadInBackground() {
        if (url == null) {
            return null;
        }

        List<Album> albums = QueryUtils.fetchAlbumData(url);
        return albums;
    }
}
