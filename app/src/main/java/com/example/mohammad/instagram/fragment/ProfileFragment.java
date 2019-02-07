package com.example.mohammad.instagram.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mohammad.instagram.ProfileType;
import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.activity.EditProfileActivity;
import com.example.mohammad.instagram.activity.LoginActivity;
import com.example.mohammad.instagram.activity.MainActivity;
import com.example.mohammad.instagram.recycler_view.profile.PostAdapter;
import com.example.mohammad.instagram.recycler_view.profile.PostCard;
import com.example.mohammad.instagram.temp.TestDataGenerator;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProfileFragment extends Fragment {
    private static final int EDIT_PROFILE_REQ_CODE = 1;
    private static final String USER_ID_KEY = "USER_ID";
    private static final String PROFILE_TYPE_KEY = "PROFILE_TYPE";
    private ImageView profileImage;
    private Toolbar toolbarToHide;
    private TextView postsNumbers, followersNumbers,
            followingNumbers, name, biography;
    private View followersParent, followingParent;
    private Button editProfile;
    private ProfileType profileType;
    private boolean isFollowedByLoggedInUser;
    private String userId;
    private RecyclerView recyclerViewProfileImages;
    private View signout;
    private Faker faker;

    @SuppressLint("ValidFragment")
    private ProfileFragment() {

    }

    public static ProfileFragment newInstance(String userId, ProfileType profileType) {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        args.putString(USER_ID_KEY, userId);
        args.putSerializable(PROFILE_TYPE_KEY, profileType);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userId = getArguments().getString(USER_ID_KEY);
        this.profileType = (ProfileType) getArguments().getSerializable(PROFILE_TYPE_KEY);
        this.faker = new Faker(new Locale("fa"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initials(rootView);
        onClickListeners();

        return rootView;
    }

    private void initials(View rootView) {
        /**
         * Use of Glide
         */
        profileImage = rootView.findViewById(R.id.profile_image);
        Glide.with(this).load(faker.internet().image())
                .apply(RequestOptions.centerCropTransform().circleCrop()).into(profileImage);
        // ToDO: set the profile image of logged in user or clicked user.
        postsNumbers = rootView.findViewById(R.id.posts);
        followersNumbers = rootView.findViewById(R.id.followers);
        followingNumbers = rootView.findViewById(R.id.following);
        name = rootView.findViewById(R.id.name);
        name.setText(this.userId);
        biography = rootView.findViewById(R.id.biography);
        editProfile = rootView.findViewById(R.id.edit_profile);
        signout = rootView.findViewById(R.id.sign_out);
        followingParent = rootView.findViewById(R.id.following_parent);
        followersParent = rootView.findViewById(R.id.followers_parent);
        recyclerViewProfileImages = rootView.findViewById(R.id.recycler_view_post_section);
        prepareProfileImagesRecyclerView();

        if (profileType == ProfileType.LOGGED_IN_USER_PROFILE) {
//            editProfile.setVisibility(View.GONE );

        }
        if (profileType == ProfileType.CLICKED_USER_PROFILE) {
            toolbarToHide = rootView.findViewById(R.id.toolbar);
            toolbarToHide.setVisibility(View.GONE);

            int backgroundId = isFollowedByLoggedInUser ? R.drawable.follow_button_blue : R.drawable.following_button_white;
            int textColor = isFollowedByLoggedInUser ? Color.WHITE : Color.BLACK;
            String text = isFollowedByLoggedInUser ? "Following" : "Follow";
            editProfile.setBackground(getResources().getDrawable(backgroundId));
            editProfile.setTextColor(textColor);
            editProfile.setText(text);
        }
        String bioText;
        if ((bioText = hasBiography()) != null && bioText.length() != 0) {
            biography.setVisibility(View.VISIBLE);
            biography.setText(bioText);
        }

    }


    private void prepareProfileImagesRecyclerView() {
        recyclerViewProfileImages.setNestedScrollingEnabled(false);
        recyclerViewProfileImages.setHasFixedSize(true);
        ArrayList<PostCard> informations = TestDataGenerator.generateSomePost(getContext());
/**        Fake data generator*/

        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewProfileImages.setLayoutManager(llm);
        PostAdapter adapter = new PostAdapter(informations);
        recyclerViewProfileImages.setAdapter(adapter);

    }

    private ArrayList<PostCard> prepareInformations() {
        ArrayList<PostCard> informations = new ArrayList<>();
        // Home fragment informations query

        return informations;
    }

    private void onClickListeners() {
        // Show followers
        followersParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFollowers();
            }
        });

        // Show following
        followingParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFollowing();
            }
        });


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (profileType) {
                    case LOGGED_IN_USER_PROFILE:
                        Intent intent = new Intent(getContext(), EditProfileActivity.class);
                        startActivity(intent);
//                        Intent intent = new Intent(getContext(), EditProfileActivity.class);
//                        Bundle extras = new Bundle();
//                        extras.putInt("Id", 2);
//                        intent.putExtra("bundle", extras);
//                        startActivityForResult(intent, EDIT_PROFILE_REQ_CODE);
                        break;
                    case CLICKED_USER_PROFILE:
                        if (isFollowedByLoggedInUser) {
                            unFollowQuery();
                        } else {
                            followQuery();
                        }
                        isFollowedByLoggedInUser = !isFollowedByLoggedInUser;
                        int backgroundId = isFollowedByLoggedInUser ? R.drawable.follow_button_blue : R.drawable.following_button_white;
                        int textColor = isFollowedByLoggedInUser ? Color.WHITE : Color.BLACK;
                        String text = isFollowedByLoggedInUser ? "Following" : "Follow";
                        editProfile.setBackground(getResources().getDrawable(backgroundId));
                        editProfile.setTextColor(textColor);
                        editProfile.setText(text);
                        break;
                }
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                MainActivity.self.finish();
                startActivity(intent);
            }
        });
    }

    private void showFollowing() {
    }

    private void followQuery() {
    }

    private void unFollowQuery() {
    }

    private void showFollowers() {
    }


    // Query to specify whether the user has a biography or not.
    private String hasBiography() {
        return null;
    }


    int SumHashItem(HashMap<Integer, Integer> hashMap) {
        int sum = 0;

        for (Map.Entry<Integer, Integer> myItem : hashMap.entrySet()) {
            sum += myItem.getValue();
        }

        return sum;
    }

}
