package com.example.samson.githubusers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.app.ShareCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by SAMSON on 7/19/2017.
 */

public class ProfileDetails  extends AppCompatActivity{

    ImageView imageView;
    TextView profileName;
    TextView profileLink;
    Profile profile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView)findViewById(R.id.im_pro);
        profileName = (TextView) findViewById(R.id.profile_username);
        profileLink = (TextView)findViewById(R.id.profile_url);

        Intent intent = getIntent();
        final String urllink = intent.getExtras().getString("Url_link");
        String username = intent.getExtras().getString("Username");
        String image = intent.getExtras().getString("Image");



        profileName.setText(username);
        profileLink.setText(urllink);

        profileLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urllink));
                startActivity(intent);
            }
        });

        Picasso.with(getApplicationContext()).load(image)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.share_icon)
                .into(imageView);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Intent upIntent = getParentActivityIntent();

                //This activity is NOT part of this app's task, so create a new task
                //when navigating up, with a synthesized back stack.
                if(NavUtils.shouldUpRecreateTask(this, upIntent)){
                    TaskStackBuilder.create(this)

                            //Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            //Navigate up to the closest parent
                            .startActivities();
                }else {
                    //This activity is part of this app's task, so simply
                    //  navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
            case R.id.share_icon:
                Intent intent = ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setText("Checkout this awesome developer @"+ profileName.getText() + ", " + profileLink.getText())
                        .setSubject("Share User")
                        .getIntent();
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * Still working with the share button*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
}
