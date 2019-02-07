package com.example.mohammad.instagram.recycler_view.profile;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohammad Amin Soheyli on 04/01/2019.
 */
public class PostCard implements Parcelable {
    public static final Creator<PostCard> CREATOR = new Creator<PostCard>() {
        @Override
        public PostCard createFromParcel(Parcel in) {
            return new PostCard(in);
        }

        @Override
        public PostCard[] newArray(int size) {
            return new PostCard[size];
        }
    };
    private String postId;
    private String username, likeNumber, description, date;
    private Bitmap image;

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    private boolean isLiked, isSaved;

    public PostCard(String postId, Bitmap image, String username,
                    String likeNumber, String description,
                    String date, boolean isLiked, boolean isSaved) {

        this.postId = postId;
        this.image = image;
        this.username = username;
        this.likeNumber = likeNumber;
        this.description = description;
        this.date = date;
        this.isLiked = isLiked;
        this.isSaved = isSaved;
    }

    protected PostCard(Parcel in) {
        image = in.readParcelable(null);
        username = in.readString();
        likeNumber = in.readString();
        description = in.readString();
        date = in.readString();
        boolean[] arr = new boolean[]{};
        in.readBooleanArray(arr);
        isSaved = arr[0];
        isLiked = arr[1];
    }

    public boolean isLiked() {
        return isLiked;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public String getPostId() {
        return postId;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(String likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(image, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeString(username);
        dest.writeString(likeNumber);
        dest.writeString(description);
        dest.writeString(date);
        boolean[] arr = new boolean[]{isSaved, isLiked};
        dest.writeBooleanArray(arr);
    }
}
