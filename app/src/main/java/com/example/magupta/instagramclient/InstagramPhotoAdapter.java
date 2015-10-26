package com.example.magupta.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by magupta on 10/24/15.
 */
public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotoAdapter(Context context, List<InstagramPhoto> photos) {
        super(context,android.R.layout.simple_list_item_1 , photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        //Get data time for this position
        InstagramPhoto photo = getItem(position);
        // Check if we are using recycled view if not we need to inflate
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        // Look up views for populating the data ( image , caption )
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto  = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        ImageView ivProfilePicture=  (ImageView) convertView.findViewById(R.id.ivProfilePic);
//        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);

        // Insert the model data into each of the view items
        tvCaption.setText(photo.getCaption());
//        tvUsername.setText(photo.getUsername());
        tvLikes.setText(photo.getLikes());
        // Clear out the image
        ivPhoto.setImageResource(0);
        //Insert Image using Picasso
        Picasso.with(getContext()).load(photo.getImageUrl()).into(ivPhoto);
        // Return the created item as view
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(3)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        Picasso.with(getContext())
                .load(photo.getProfilePicture())
                .fit()
                .transform(transformation)
                .into(ivProfilePicture);

        return convertView;
    }
}
