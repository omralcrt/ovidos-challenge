package com.omralcorut.myalbums;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Photo>> {

    private static final String PHOTO_REQUEST_URL = "http://jsonplaceholder.typicode.com/photos";
    private static final int PHOTO_LOADER_ID = 1;
    private PhotoAdapter adapter;
    private TextView emptyStateTextView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        //Initilize recyclerView and empty state textView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        emptyStateTextView = (TextView) findViewById(R.id.empty_view);

        //Create custom array adapter
        adapter = new PhotoAdapter(this, new ArrayList<Photo>());

        //Check rotate of phone, Portrait -> 2 Column, Landscape -> 3 Column
        //Give some spacing to items
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL));
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5)));
        }
        else{
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                    StaggeredGridLayoutManager.VERTICAL));
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(5)));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //Connect photos arrayList to recyclerView
        recyclerView.setAdapter(adapter);

        //Check internet connection
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Load list
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(PHOTO_LOADER_ID, null, this);
        } else {
            //Show error message
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }


    //Fetch photos in the background
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

    //When fetched, show photos in the recyclerView with cards
    @Override
    public void onLoadFinished(Loader<List<Photo>> loader, List<Photo> photos) {

        //Remove loading indicator
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        //Initilize empty state
        emptyStateTextView.setText(R.string.no_photo);

        adapter.clear();

        //Check photos is empty
        if (photos != null && !photos.isEmpty()) {
            adapter.addALl(photos);
            recyclerView.setVisibility(View.VISIBLE);
            emptyStateTextView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyStateTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Photo>> loader) {
        adapter.clear();
    }

    //RecyclerView item decoration - give equal margin around grid item
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;

        public GridSpacingItemDecoration(int spanCount, int spacing) {
            this.spanCount = spanCount;
            this.spacing = spacing;
        }


        //Helper method for getting item offsets
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);

            outRect.left = spacing;
            outRect.right = spacing;

            if (position < spanCount) {
                outRect.top = spacing*2;
            }
            outRect.bottom = spacing*2;
        }
    }

    //Converting dp to pixel
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
