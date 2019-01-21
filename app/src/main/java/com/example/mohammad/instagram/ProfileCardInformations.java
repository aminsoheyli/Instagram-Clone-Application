package com.example.mohammad.instagram;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohammad Amin Soheyli on 04/01/2019.
 */
public class ProfileCardInformations implements Parcelable {
    public static final Creator<ProfileCardInformations> CREATOR = new Creator<ProfileCardInformations>() {
        @Override
        public ProfileCardInformations createFromParcel(Parcel in) {
            return new ProfileCardInformations(in);
        }

        @Override
        public ProfileCardInformations[] newArray(int size) {
            return new ProfileCardInformations[size];
        }
    };
    private boolean isLiked, isSaved;
    private String username, likeNumber, description, date;
    private Bitmap image;

    public ProfileCardInformations(Bitmap image, String username,
                                   String likeNumber, String description,
                                   String date, boolean isLiked, boolean isSaved) {

        this.image = image;
        this.username = username;
        this.likeNumber = likeNumber;
        this.description = description;
        this.date = date;
        this.isLiked = isLiked;
        this.isSaved = isSaved;
    }

    protected ProfileCardInformations(Parcel in) {
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
