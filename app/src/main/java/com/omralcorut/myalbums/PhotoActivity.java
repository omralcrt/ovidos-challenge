package com.omralcorut.myalbums;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Photo>> {

    private static final String PHOTO_REQUEST_URL = "http://jsonplaceholder.typicode.com/photos";
    private static final int PHOTO_LOADER_ID = 1;
    private PhotoAdapter adapter;
    private TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        //Initilize gridView
        GridView gridView = (GridView) findViewById(R.id.gridView);

        //Check rotate of phone, Portrait -> 2 Column, Landscape -> 3 Column
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridView.setNumColumns(2);
        } else {
            gridView.setNumColumns(3);
        }

        //If list is empty, show message
        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        gridView.setEmptyView(emptyStateTextView);

        adapter = new PhotoAdapter(this, new ArrayList<Photo>());

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: Do something
            }
        });

        //Check internet connection
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Load list
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(PHOTO_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Photo>> onCreateLoader(int id, Bundle args) {
        //Show all photos of given album id
        Intent intent = getIntent();
        String albumId = intent.getStringExtra("albumID");
        Uri baseUri = Uri.parse(PHOTO_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("albumId", albumId);
        return new PhotoLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Photo>> loader, List<Photo> photos) {

        //Show loading indicator
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        //Initilize empty state
        emptyStateTextView.setText(R.string.no_photo);

        adapter.clear();

        if (photos != null && !photos.isEmpty()) {
            adapter.addAll(photos);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Photo>> loader) {
        adapter.clear();
    }
}
