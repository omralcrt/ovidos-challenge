package com.omralcorut.myalbums;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        //Get albums from QueryUtils
        ArrayList<Album> albums = QueryUtils.extractAlbums();

        //Initilize listView
        ListView albumListView = (ListView) findViewById(R.id.list);

        //Create custom array adapter
        AlbumAdapter adapter = new AlbumAdapter(this, albums);

        //connect albums arrayList to listView
        albumListView.setAdapter(adapter);

    }
}
