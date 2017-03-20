package com.omralcorut.myalbums;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Album>> {

    private static final String ALBUM_REQUEST_URL = "http://jsonplaceholder.typicode.com/albums";

    private static final int ALBUM_LOADER_ID = 1;

    private AlbumAdapter adapter;

    private TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        //Initilize listView
        ListView albumListView = (ListView) findViewById(R.id.list);

        //If list is empty, show message
        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        albumListView.setEmptyView(emptyStateTextView);

        //Create custom array adapter
        adapter = new AlbumAdapter(this, new ArrayList<Album>());

        //Connect albums arrayList to listView
        albumListView.setAdapter(adapter);

        //When click item, open photos in the album
        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Album currentAlbum = adapter.getItem(position); //for album id
                Intent photoIntent = new Intent(AlbumActivity.this, PhotoActivity.class);
                startActivity(photoIntent);
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Load list
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(ALBUM_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet_connection);
        }

    }


    @Override
    public Loader<List<Album>> onCreateLoader(int id, Bundle args) {
        return new AlbumLoader(this, ALBUM_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Album>> loader, List<Album> albums) {

        //Show loading indicator
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        //Initilize empty state
        emptyStateTextView.setText(R.string.no_album);

        adapter.clear();

        if (albums != null && !albums.isEmpty()) {
            adapter.addAll(albums);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Album>> loader) {
        adapter.clear();
    }
}
