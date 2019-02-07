package com.example.mohammad.instagram.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.recycler_view.story.StoryAdapter;
import com.example.mohammad.instagram.recycler_view.story.StoryCard;
import com.example.mohammad.instagram.recycler_view.profile.PostAdapter;
import com.example.mohammad.instagram.recycler_view.profile.PostCard;
import com.example.mohammad.instagram.temp.TestDataGenerator;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerViewPost, recyclerViewStory;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("ValidFragment")
    private HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initials(rootView);
        onClickListeners(rootView);
        return rootView;
    }

    private void initials(View rootView) {
        recyclerViewPost = rootView.findViewById(R.id.recycler_view_post_section);
        recyclerViewStory = rootView.findViewById(R.id.recycler_view_story_section);
        prepareRecyclerView();

    }

    private void prepareRecyclerView() {
        recyclerViewStory.setNestedScrollingEnabled(false);
        recyclerViewPost.setNestedScrollingEnabled(false);
        recyclerViewStory.setHasFixedSize(true);
        recyclerViewPost.setHasFixedSize(true);
        ArrayList<StoryCard> storyInformations = TestDataGenerator.generateSomeStory(getContext());
        ArrayList<PostCard> postInformations = TestDataGenerator.generateSomePost(getContext());
        LinearLayoutManager llmStory = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager llmPost = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewStory.setLayoutManager(llmStory);
        recyclerViewPost.setLayoutManager(llmPost);
        StoryAdapter storyAdapter = new StoryAdapter(storyInformations);
        PostAdapter postAdapter = new PostAdapter(postInformations);
        recyclerViewStory.setAdapter(storyAdapter);
        recyclerViewPost.setAdapter(postAdapter);
    }

    private void onClickListeners(View rootView) {

    }
}
