package com.example.magupta.instagramclient;

import android.text.Html;
import android.text.Spanned;

import java.text.NumberFormat;

/**
 * Created by magupta on 10/24/15.
 */
public class InstagramPhoto {
    String username;
    String imageUrl;
    String caption;
    String imageHeight;
    String imageWidth;
    int likes;
    String profilePicture;
    long createdTime;

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedTime() {
        long time = System.currentTimeMillis() - createdTime * 1000;
        int seconds = (int) (time/1000);
        int minutes = (int) (seconds/60);
        int hours = (int) (minutes/60);
        int days = (int) hours/24;
        int weeks = (int) days/7;

        if ( weeks > 0)
            return weeks + " w";
        if (days > 0)
            return days + " d";
        if (hours > 0)
            return hours + " h";
        if (minutes > 0)
            return minutes + " m";
        return seconds + " s";
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }



    public InstagramPhoto(){}

    public InstagramPhoto(String username, String imageUrl, String caption, String imageHeight, int likes) {
        this.username = username;
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.imageHeight = imageHeight;
        this.likes = likes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Spanned getCaption() {
        String formattedText = String.format("<b><font color='#125688'>%s</font></b> -- %s", this.username, this.caption);
        return Html.fromHtml(formattedText);
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getLikes() {
        return NumberFormat.getInstance().format(likes) + " likes";
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }
}
