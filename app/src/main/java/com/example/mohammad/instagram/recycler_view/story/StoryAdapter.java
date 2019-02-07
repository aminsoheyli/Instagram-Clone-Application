package com.example.mohammad.instagram.recycler_view.story;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.temp.TestDataGenerator;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {
    private View rootView;
    private ArrayList<StoryCard> stories;

    public StoryAdapter(ArrayList<StoryCard> stories) {
        this.stories = stories;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_story,viewGroup,false);
        return new StoryViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder viewHolder, int i) {
        Glide.with(rootView.getContext())
                .load(stories.get(i).getStoryImageUrl())
                .apply(RequestOptions.centerCropTransform().circleCrop())
                .into(viewHolder.storyImage);
        viewHolder.storyUsername.setText(stories.get(i).getStoryUserName());
    }

    @Override
    public int getItemCount() {
        return TestDataGenerator.getItemCount();

    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView storyImage;
        private TextView storyUsername;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            storyImage = itemView.findViewById(R.id.story_image);
            storyUsername = itemView.findViewById(R.id.story_user_name);
        }
    }
}
