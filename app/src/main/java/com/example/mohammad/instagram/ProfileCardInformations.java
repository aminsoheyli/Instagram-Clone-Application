package com.example.mohammad.instagram;

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
    private String username, likeNumber, description, date;
    private int pofileImageId, imageId;

    public ProfileCardInformations(int pofileImageId, int image, String username,
                                   String likeNumber, String description, String date) {
        this.pofileImageId = pofileImageId;
        this.imageId = image;
        this.username = username;
        this.likeNumber = likeNumber;
        this.description = description;
        this.date = date;
    }

    protected ProfileCardInformations(Parcel in) {
        pofileImageId = in.readInt();
        imageId = in.readInt();
        username = in.readString();
        likeNumber = in.readString();
        description = in.readString();
        date = in.readString();
    }

    public int getPofileImageId() {
        return pofileImageId;
    }

    public void setPofileImageId(int pofileImageId) {
        this.pofileImageId = pofileImageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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
        dest.writeInt(pofileImageId);
        dest.writeInt(imageId);
        dest.writeString(username);
        dest.writeString(likeNumber);
        dest.writeString(description);
        dest.writeString(date);
    }
}
