package com.example.samson.githubusers;

/**
 * Created by SAMSON on 7/19/2017.
 */

public class Profile {

    String profile_username;
    String profile_image;
    String profile_url;

    public Profile(String profile_username, String profile_image, String profile_url) {
        this.profile_username = profile_username;
        this.profile_image = profile_image;
        this.profile_url = profile_url;
    }

    public String getProfile_username() {
        return profile_username;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public String getProfile_url() {
        return profile_url;
    }
}
