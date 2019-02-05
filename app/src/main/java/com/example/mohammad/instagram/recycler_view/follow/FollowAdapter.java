package com.example.mohammad.instagram.recycler_view.follow;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mohammad.instagram.R;

import java.util.List;

/**
 * Created by Mohammad Amin Soheyli on 04/01/2019.
 */
public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowsViewHolder> {
    private List<String> informations;
    private View rootView;

    public FollowAdapter(List<String> informations) {
        this.informations = informations;
    }

    @NonNull
    @Override
    public FollowsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_follows, viewGroup, false);
        return new FollowsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowsViewHolder vh, int i) {
        vh.name.setText(informations.get(i));
        vh.followBtn.setBackground(rootView.getResources().getDrawable(R.drawable.follow_button_blue));
        vh.followBtn.setText("Following");
        vh.followBtn.setTextColor(Color.WHITE);
        vh.followBtn.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return informations.size();
    }

    public static class FollowsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private Button followBtn;
        private ImageView profileImage;

        public FollowsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            followBtn = itemView.findViewById(R.id.following_state);
            profileImage = itemView.findViewById(R.id.profile_image);
            Glide.with(itemView.getContext())
                    .load("https://carwad.net/sites/default/files/instagram-png-transparent-images-145814-4659729.jpg")
                    .apply(RequestOptions.centerCropTransform().circleCrop()).into(profileImage);
            // ToDO: set the profile image of logged in user or clicked user.
            onClickListeners();
        }

        private void onClickListeners() {
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            followBtn.setOnClickListener(new View.OnClickListener() {
                int state = 0;

                @Override
                public void onClick(View v) {
                    if (state == 0) {
                        followBtn.setBackground(v.getResources().getDrawable(R.drawable.following_button_white));
                        followBtn.setText("Follow");
                        followBtn.setTextColor(Color.BLACK);
                        state = 1;
                    } else {
                        followBtn.setBackground(v.getResources().getDrawable(R.drawable.follow_button_blue));
                        followBtn.setText("Following");
                        followBtn.setTextColor(Color.WHITE);
                        state = 0;
                    }
                }
            });
        }
    }
}