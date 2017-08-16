package com.example.samson.githubusers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Profile>>, ProfileRecyclerAdapter.RecyclerClickInterface{

    public static final int LOADER_CALLBACKS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getSupportLoaderManager().initLoader(LOADER_CALLBACKS, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.refresh:


        return true;
        }
        return super.onOptionsItemSelected(item);
    }

    Profile profile;
    ProgressBar progressBar;
    TextView emptyView;
    ProfileAdapter profileAdapter;
//    ListView listView;

    @Override
    public Loader<List<Profile>> onCreateLoader(int id, Bundle args) {
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

//        emptyView = (TextView)findViewById(R.id.confirm_data);



        ConnectivityManager conMagr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMagr.getActiveNetworkInfo();


        if(netInfo != null && netInfo.isConnected()){
            progressBar.setVisibility(View.VISIBLE);

            final String BASE_URL =  "https://api.github.com/search/repositories";

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            String sort = sharedPreferences.getString
                    (getString(R.string.sort_editPreference_key), getString(R.string.sort_by_value_default));

            String query = sharedPreferences.getString
                    (getString(R.string.query_editPreference_key), getString(R.string.query_editPreference_default_value));

//        String query = "android";
//        String sort = "lagos";
            String query_key = "q";
            String sort_key = "sort";
            Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(query_key, query)
                    .appendQueryParameter(sort_key, sort).build();


            Log.e("URL ADDRESS", "The addreess is "+ builtUri.toString());

            return new GitUserAsyncLoader(MainActivity.this, builtUri.toString());

        }else{
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No internet connection");
        }

        return null;

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ON RESUME: ", "On resume called");
        getSupportLoaderManager().restartLoader(LOADER_CALLBACKS, null, this);
    }

    RecyclerView listView;
    ProfileRecyclerAdapter adapter;
//    ProfileRecyclerAdapter.RecyclerClickInterface clickInterface;

    @Override
    public void onLoadFinished(Loader<List<Profile>> loader, final List<Profile> data) {
        progressBar.setVisibility(View.GONE);

        listView = (RecyclerView)findViewById(R.id.listView);

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setHasFixedSize(true);
//        profileAdapter = new ProfileAdapter(MainActivity.this, data);
//        listView = (ListView)findViewById(R.id.listView);


        adapter = new ProfileRecyclerAdapter(MainActivity.this, data, this);


//        listView.setEmptyView(emptyView);
        if(data!= null){
            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                     profile = profileAdapter.getItem(position);
//                    Intent intent = new Intent(MainActivity.this, ProfileDetails.class);
//                    intent.putExtra("Username", profile.getProfile_username())
//                            .putExtra("Image", profile.getProfile_image())
//                            .putExtra("Url_link", profile.getProfile_url());
//                    startActivity(intent);
//                }
//            });
        }
    }
    @Override
    public void onLoaderReset(Loader<List<Profile>> loader) {

        listView.setAdapter(new ProfileRecyclerAdapter(this, new ArrayList<Profile>(), this));
    }
    Toast mToast;

    @Override
    public void recyclerClick(int index) {
//      Log.e("CLICKED: ", "The item is clicked at index " + index);
                profile = adapter.profileList.get(index);
                    Intent intent = new Intent(MainActivity.this, ProfileDetails.class);
                    intent.putExtra("Username", profile.getProfile_username())
                            .putExtra("Image", profile.getProfile_image())
                            .putExtra("Url_link", profile.getProfile_url());
                    startActivity(intent);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
