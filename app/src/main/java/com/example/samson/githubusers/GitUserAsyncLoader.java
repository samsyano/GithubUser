package com.example.samson.githubusers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by SAMSON on 7/19/2017.
 */

public class GitUserAsyncLoader extends AsyncTaskLoader<List<Profile>> {


    public GitUserAsyncLoader(Context context) {
        super(context);
    }

        static final String BASE_URL =  "https://api.github.com/search/repositories";
    String type = "android";
    String sort = "lagos";

//    String BASE_URL = "https://api.github.com/search/repositories?q=android&lagos";


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Profile> loadInBackground() {

        NetworkUtil util = new NetworkUtil();
//        List<Profile> profileList = util.makeRequest(BASE_URL);
        List<Profile> profileList = util.makeRequest(BASE_URL, type, sort);

        return profileList;
    }
}
