package com.example.mohammad.instagram;

import android.os.Parcelable;
import java.io.Serializable;

/**
 * Created by Mohammad Amin Soheyli on 04/01/2019.
 */
public class ProfileInformations {
    private int numberOfPosts;
    private int numberOfFollowers;
    private int numberOfFollowing;
    private int profileImageID;


    public ProfileInformations(int numberOfPosts, int numberOfFollowers,
                               int numberOfFollowing, int profileImageID) {
        this.numberOfPosts = numberOfPosts;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfFollowing = numberOfFollowing;
        this.profileImageID = profileImageID;
    }

    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public int getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    public int getNumberOfFollowing() {
        return numberOfFollowing;
    }

    public void setNumberOfFollowing(int numberOfFollowing) {
        this.numberOfFollowing = numberOfFollowing;
    }

    public int getProfileImageID() {
        return profileImageID;
    }

    public void setProfileImageID(int profileImageID) {
        this.profileImageID = profileImageID;
    }
}
