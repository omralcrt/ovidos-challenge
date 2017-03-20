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

        //Create fake album arrayList
        ArrayList<String> albums = new ArrayList<>();
        albums.add("Album1");
        albums.add("Album2");
        albums.add("Album3");
        albums.add("Album4");
        albums.add("Album5");
        albums.add("Album6");
        albums.add("Album7");

        //Initilize listView
        ListView albumListView = (ListView) findViewById(R.id.list);

        //Create array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, albums
        );

        //connect albums arrayList to listView
        albumListView.setAdapter(adapter);

    }
}
