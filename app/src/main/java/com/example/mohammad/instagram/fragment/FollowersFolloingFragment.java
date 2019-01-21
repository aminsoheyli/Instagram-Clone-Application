package com.example.mohammad.instagram.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.recycler_view.follow.FollowAdapter;
import com.example.mohammad.instagram.recycler_view.follow.FollowCard;

import java.util.ArrayList;

/**
 * Created by Mohammad Amin Soheyli on 04/01/2019.
 */
public class FollowersFolloingFragment extends DialogFragment {
    private static final String INFORMATIONS_KEY = "information's_key";
    private View rootView;
    private ArrayList<FollowCard> followsInformations;
    private RecyclerView recyclerView;

    public static FollowersFolloingFragment newInstance(ArrayList<FollowCard> informations) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(INFORMATIONS_KEY, informations);
        FollowersFolloingFragment fragment = new FollowersFolloingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        followsInformations = getArguments().getParcelableArrayList(INFORMATIONS_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_followers_following, container, false);
        recyclerView = rootView.findViewById(R.id.follows_recycler_view);
        prepareRecyclerView();
        return rootView;
    }

    private void prepareRecyclerView() {
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        FollowAdapter followAdapter = new FollowAdapter(followsInformations);
        recyclerView.setAdapter(followAdapter);
    }


}
