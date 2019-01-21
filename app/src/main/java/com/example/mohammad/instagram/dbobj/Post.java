package com.example.mohammad.instagram.dbobj;

import android.graphics.Bitmap;

/**
 * Created by Mohammad Amin Soheyli on 20/01/2019.
 */
public class Post {
    private String postId;
    private String userId;
    private String postDate;
    private Bitmap image;

    public Post(String postId, String userId, String postDate, Bitmap image) {
        this.postId = postId;
        this.userId = userId;
        this.postDate = postDate;
        this.image = image;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
