package com.example.mohammad.instagram.recycler_view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {
    private ArrayList<StoryCard> stories;

    public StoryAdapter(ArrayList<StoryCard> stories) {
        this.stories = stories;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder storyViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {
        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
