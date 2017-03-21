package com.omralcorut.myalbums;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by omral on 21.03.2017.
 */

public class PhotoAdapter extends ArrayAdapter<Photo> {

    private static final String LOG_TAG = PhotoAdapter.class.getName();

    public PhotoAdapter(Context context, List<Photo> photos) {
        super(context, 0, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Check is there a listItem view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.photo_list_item, parent, false
            );
        }

        //Create a photo object
        Photo currentPhoto = getItem(position);

        //Display title of object
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentPhoto.getTitle().toUpperCase());

        /*
        //Display image of object
        ImageView thumbnail = (ImageView) listItemView.findViewById(R.id.thumbnail);
        Bitmap bmp = null;
        try {
            URL url = new URL(currentPhoto.getThumbnailUrl());
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem converting URL.", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem converting bitmap.", e);
        }
        thumbnail.setImageBitmap(bmp);
        */

        return listItemView;
    }
}
