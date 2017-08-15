package com.example.samson.githubusers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SAMSON on 8/13/2017.
 */

public class ProfileRecyclerAdapter extends RecyclerView.Adapter<ProfileRecyclerAdapter.ProfileRecyclerViewHolder>{

    Context context;
    List<Profile> profileList;
    RecyclerClickInterface recyclerClickInterface;


    public ProfileRecyclerAdapter(Context context, List<Profile> profileList, RecyclerClickInterface recyclerClickInterface) {
        this.context = context;
        this.profileList = profileList;
        this.recyclerClickInterface = recyclerClickInterface;
    }

    @Override
    public ProfileRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_list, parent, false);

        return new ProfileRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfileRecyclerViewHolder holder, int position) {

       holder.bind(position);

//        Picasso.with(context).load(image_url).placeholder(R.drawable.placeholder)
//        .error(R.drawable.share_icon).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    class ProfileRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView textView;
        LinearLayout listLayout;
        public ProfileRecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_profile);
            textView = (TextView) itemView.findViewById(R.id.text_profile);
            listLayout = (LinearLayout) itemView.findViewById(R.id.listView);

            itemView.setOnClickListener(this);
        }

        public void bind(final int position){
          final   Profile profile = profileList.get(position);

            textView.setText(profile.getProfile_username());

//            String image_url = profile.getProfile_image();
            Picasso.with(context).load(profile.getProfile_image())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.share_icon)
                    .into(imageView);
        }

        @Override
        public void onClick(View v) {
//            int clickedPosition = getAdapterPosition();
            recyclerClickInterface.recyclerClick(getAdapterPosition());



        }
    }

    interface RecyclerClickInterface{
        void recyclerClick(int index);
    }
}
