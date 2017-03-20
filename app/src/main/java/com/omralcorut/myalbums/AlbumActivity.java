package com.omralcorut.myalbums;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent photoIntent = new Intent(view.getContext(), PhotoActivity.class);
                startActivity(photoIntent);
            }
        });

    }
}
