package com.omralcorut.myalbums;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by omral on 21.03.2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    private static final String LOG_TAG = PhotoAdapter.class.getName();

    private Context mContext;
    private List<Photo> photos;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    public PhotoAdapter(Context mContext, List<Photo> photos) {
        this.mContext = mContext;
        this.photos = photos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.title.setText(photo.getTitle());

        // loading album cover using Glide library
        Glide.with(mContext).load(photo.getThumbnailUrl()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void clear() {
        int size = this.photos.size();
        this.photos.clear();
        notifyItemRangeRemoved(0, size);
    }
}

