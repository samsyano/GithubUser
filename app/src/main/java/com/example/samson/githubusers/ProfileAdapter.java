package com.example.samson.githubusers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SAMSON on 7/19/2017.
 */

public class ProfileAdapter extends ArrayAdapter<Profile> {

    public ProfileAdapter(@NonNull Context context, @NonNull List<Profile> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.profile_list, parent, false);
        }

        Profile profile = getItem(position);

        TextView username = (TextView)view.findViewById(R.id.text_profile);
//        TextView link_url = (TextView)view.findViewById(R.id.profile_url);

        username.setText(profile.getProfile_username());
//        link_url.setText(profile.getProfile_url());

        /*
        * Loading Image using piccaso*/
        ImageView image = (ImageView)view.findViewById(R.id.image_profile);

        Picasso.with(getContext()).load(profile.getProfile_image())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.share_icon)
                .into(image);


        return view;
    }
}
