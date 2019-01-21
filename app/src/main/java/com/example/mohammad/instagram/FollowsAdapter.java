package com.example.mohammad.instagram;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mohammad Amin Soheyli on 04/01/2019.
 */
public class FollowsAdapter extends RecyclerView.Adapter<FollowsAdapter.FollowsViewHolder> {
    private List<FollowsInformation> informations;
    private View rootView;

    public FollowsAdapter(List<FollowsInformation> informations) {
        this.informations = informations;
    }

    @NonNull
    @Override
    public FollowsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_follows, viewGroup, false);
        return new FollowsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowsViewHolder followsViewHolder, int i) {
        followsViewHolder.name.setText(informations.get(i).getName());
        followsViewHolder.image.setImageResource(informations.get(i).getImageId());
        followsViewHolder.followBtn.setBackground(rootView.getResources().getDrawable(R.drawable.follow_button_blue));
        followsViewHolder.followBtn.setText("Following");
        followsViewHolder.followBtn.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return informations.size();
    }

    public static class FollowsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private CircleImageView image;
        private Button followBtn;

        public FollowsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.imageId);
            followBtn = itemView.findViewById(R.id.following_state);
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