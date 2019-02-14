package com.example.mohammad.instagram;

import android.graphics.Bitmap;

import java.io.Serializable;

public class PreviewDetail implements Serializable {
    private String userName;
    private Bitmap userProfile, image;
    private boolean isLiked;

    public PreviewDetail(String userName, Bitmap userProfile, Bitmap image, boolean isLiked) {
        this.userName = userName;
        this.userProfile = userProfile;
        this.image = image;
        this.isLiked = isLiked;
    }

    public String getUserName() {
        return userName;
    }

    public Bitmap getUserProfile() {
        return userProfile;
    }

    public Bitmap getImage() {
        return image;
    }

    public boolean isLiked() {
        return isLiked;
    }
}
