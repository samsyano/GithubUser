package com.example.samson.githubusers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by SAMSON on 7/19/2017.
 */

public class GitUserAsyncLoader extends AsyncTaskLoader<List<Profile>> {

    String url;

    public GitUserAsyncLoader(Context context, String url) {
        super(context);
        this.url = url;

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Profile> loadInBackground() {

        NetworkUtil util = new NetworkUtil();
        List<Profile> profileList = util.makeRequest(url);

        return profileList;
    }
}
