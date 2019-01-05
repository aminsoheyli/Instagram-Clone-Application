package com.example.mohammad.instagram;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohammad Amin Soheyli on 04/01/2019.
 */
public class FollowsInformation implements Parcelable {
    public static final Creator<FollowsInformation> CREATOR = new Creator<FollowsInformation>() {
        @Override
        public FollowsInformation createFromParcel(Parcel in) {
            return new FollowsInformation(in);
        }

        @Override
        public FollowsInformation[] newArray(int size) {
            return new FollowsInformation[size];
        }
    };

    private String name;
    private int imageId;

    public FollowsInformation(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    protected FollowsInformation(Parcel in) {
        name = in.readString();
        imageId = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(imageId);
    }
}
