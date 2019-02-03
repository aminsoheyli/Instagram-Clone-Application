package com.example.mohammad.instagram.recycler_view.comment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohammad Amin Soheyli on 22/01/2019.
 */
public class CommentCard implements Parcelable {
    private String profileName, commentText;

    public CommentCard(String profileName, String commentText) {

        this.profileName = profileName;
        this.commentText = commentText;
    }

    public CommentCard(Parcel in) {
        profileName = in.readString();
        commentText = in.readString();
    }

    public static final Creator<CommentCard> CREATOR = new Creator<CommentCard>() {
        @Override
        public CommentCard createFromParcel(Parcel in) {
            return new CommentCard(in);
        }

        @Override
        public CommentCard[] newArray(int size) {
            return new CommentCard[size];
        }
    };

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profileName);
        dest.writeString(commentText);
    }
}
