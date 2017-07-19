package com.example.samson.githubusers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Profile>>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportLoaderManager().initLoader(1, null, this);

//        onSaveInstanceState(savedInstanceState);

    }
    ProgressBar progressBar;
    TextView emptyView;
    ProfileAdapter profileAdapter;
    ListView listView;
    @Override
    public Loader<List<Profile>> onCreateLoader(int id, Bundle args) {
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        emptyView = (TextView)findViewById(R.id.confirm_data);


        ConnectivityManager conMagr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMagr.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnected()){
            progressBar.setVisibility(View.VISIBLE);
            return new GitUserAsyncLoader(MainActivity.this);
        }else{
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No internet connection");
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Profile>> loader, List<Profile> data) {
        progressBar.setVisibility(View.GONE);

        profileAdapter = new ProfileAdapter(MainActivity.this, data);
        listView = (ListView)findViewById(R.id.listView);

        listView.setEmptyView(emptyView);
        if(data!= null){
            listView.setAdapter(profileAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Profile profile = profileAdapter.getItem(position);
                    Intent intent = new Intent(MainActivity.this, ProfileDetails.class);
                    intent.putExtra("Username", profile.getProfile_username())
                            .putExtra("Image", profile.getProfile_image())
                            .putExtra("Url_link", profile.getProfile_url());
                    startActivity(intent);

                }
            });

        }

    }

    @Override
    public void onLoaderReset(Loader<List<Profile>> loader) {

    }
}
