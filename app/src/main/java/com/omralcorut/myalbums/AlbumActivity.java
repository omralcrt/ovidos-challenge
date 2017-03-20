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
        ArrayList<Album> albums = new ArrayList<>();
        albums.add(new Album(1,1,"Album1"));
        albums.add(new Album(1,2,"Album2"));
        albums.add(new Album(1,3,"Album3"));
        albums.add(new Album(2,4,"Album4"));
        albums.add(new Album(3,5,"Album5"));
        albums.add(new Album(3,6,"Album6"));
        albums.add(new Album(3,7,"Album7"));

        //Initilize listView
        ListView albumListView = (ListView) findViewById(R.id.list);

        //Create custom array adapter
        AlbumAdapter adapter = new AlbumAdapter(this, albums);

        //connect albums arrayList to listView
        albumListView.setAdapter(adapter);

    }
}
