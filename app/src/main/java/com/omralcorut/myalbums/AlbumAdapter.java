package com.omralcorut.myalbums;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.apache.commons.lang3.text.WordUtils;

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

        //Create is a listItem view
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
        title.setText(WordUtils.capitalize(currentAlbum.getTitle()));

        return listItemView;
    }
}
