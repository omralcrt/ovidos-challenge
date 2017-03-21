package com.omralcorut.myalbums;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

/**
 * Created by omral on 21.03.2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    private static final String LOG_TAG = PhotoAdapter.class.getName();

    private Context mContext;
    private List<Photo> photos;

    //Custom holder for views in recyclerView
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public View loadingIndicator;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            loadingIndicator = view.findViewById(R.id.loading_indicator);
        }
    }


    public PhotoAdapter(Context mContext, List<Photo> photos) {
        this.mContext = mContext;
        this.photos = photos;
    }

    //Create custom listItem view
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    //Display thumbnail and title of item
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.loadingIndicator.setVisibility(View.VISIBLE);
        holder.title.setText(WordUtils.capitalize(photo.getTitle()));

        // loading image using Glide library
        Glide.with(mContext).load(photo.getThumbnailUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable>
                            target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        //When image loaded, hide loading indicator
                        holder.loadingIndicator.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    //Clear adapter
    public void clear() {
        int size = this.photos.size();
        this.photos.clear();
        notifyItemRangeRemoved(0, size);
    }

    //Add new list to adapter
    public void addALl(List<Photo> datas){
        photos.clear();
        photos.addAll(datas);
        notifyDataSetChanged();
    }
}

