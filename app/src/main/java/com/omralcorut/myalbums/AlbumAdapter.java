package com.omralcorut.myalbums;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by omral on 20.03.2017.
 */

public class AlbumAdapter extends ArrayAdapter<Album> {


    public AlbumAdapter(Context context, List<Album> albums) {
        super(context, 0, albums);
    }

    //Return listItem view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Check is there a listItem view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.album_list_item, parent, false
            );
        }

        //Create a album object
        Album currentAlbum = getItem(position);

        //Display title of object
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentAlbum.getTitle());

        return listItemView;
    }
}
