package com.example.mohammad.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final int EDIT_PROFILE_REQ_CODE = 1;
    private CircleImageView profileImage;
    private TextView postsNumbers, followersNumbers, followingNumbers, name, biography;
    private View followersParent, followingParent;
    private Button editProfile;
    private RecyclerView recyclerViewProfileImages;
    private int sumHeight;
    private HashMap<Integer, Integer> itemHeight;


    public static ProfileFragment newInstance(ProfileInformations profileInformations) {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        profileImage = rootView.findViewById(R.id.profile_tab);
        postsNumbers = rootView.findViewById(R.id.posts);
        followersNumbers = rootView.findViewById(R.id.followers);
        followingNumbers = rootView.findViewById(R.id.following);
        name = rootView.findViewById(R.id.name);
        biography = rootView.findViewById(R.id.biography);
        editProfile = rootView.findViewById(R.id.edit_profile);
        followingParent = rootView.findViewById(R.id.following_parent);
        followersParent = rootView.findViewById(R.id.followers_parent);

        recyclerViewProfileImages = rootView.findViewById(R.id.recycler_view_profile_images);
        prepareProfileImagesRecyclerView();

        String bioText;
        if ((bioText = hasBiography()) != null && bioText.length() != 0) {
            biography.setVisibility(View.VISIBLE);
            biography.setText(bioText);
        }
    }

    private void prepareProfileImagesRecyclerView() {
        recyclerViewProfileImages.setNestedScrollingEnabled(false);
        recyclerViewProfileImages.setHasFixedSize(true);
        ArrayList<ProfileCardInformations> informations = new ArrayList<>();
        ProfileCardInformations first =
                new ProfileCardInformations(R.drawable.like_icon_fill
                        , R.drawable.instagram_icon
                        , "example"
                        , "16 likes"
                        , "This is a example's dynamic description"
                        , "2 Days ago");
        ProfileCardInformations second =
                new ProfileCardInformations(R.drawable.like_icon_stroke
                        , R.drawable.saved_icon_stroke
                        , "alisafri98"
                        , "120 likes"
                        , "This is a Ali Safari's dynamic description "
                        , "14 May 2018");
        ProfileCardInformations third =
                new ProfileCardInformations(R.drawable.instagram_icon
                        , R.drawable.like_icon_fill
                        , "amisoheyli77"
                        , "200 likes"
                        , "This is a Amin Soheyli's dynamic description"
                        , "20 minutes ago");
        ProfileCardInformations fourth =
                new ProfileCardInformations(R.drawable.saved_icon_fill
                        , R.drawable.comment_icon
                        , "test19"
                        , "17 likes"
                        , "This is a test's dynamic description"
                        , "Just now");
        Random random = new Random();

        ProfileCardInformations test;
        for (int i = 0; i < 25; i++) {
            int x = random.nextInt(3) + 1;
            switch (x) {
                case 1:
                    test = first;
                    break;
                case 2:
                    test = second;
                    break;
                case 3:
                    test = third;
                    break;
                case 4:
                    test = fourth;
                    break;
                default:
                    test = first;
                    break;
            }
            informations.add(test);
        }
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewProfileImages.setLayoutManager(llm);
        ProfileImagesAdapter adapter = new ProfileImagesAdapter(informations);
        recyclerViewProfileImages.setAdapter(adapter);

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
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("Id", 2);
                intent.putExtra("bundle", extras);
                startActivityForResult(intent, EDIT_PROFILE_REQ_CODE);
            }
        });
    }

    private void showFollowers() {
        ArrayList<FollowsInformation> informations = new ArrayList<>();
        String[] strings = getResources().getStringArray(R.array.follows_names);
        for (int i = 0; i < strings.length; i++) {
            if (i % 2 == 0) {
                informations.add(new FollowsInformation(strings[i], R.drawable.user_icon_stroke));
                continue;
            }
            informations.add(new FollowsInformation(strings[i], R.drawable.user_icon_fill));
        }
        FollowersFolloingFragment fragment = FollowersFolloingFragment.newInstance(informations);
        fragment.show(getFragmentManager(), "Follows fragment");
    }

    private void showFollowing() {
        showFollowers();
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

//    @Override
//    public void HeightChange(int position, int height) {
//        itemHeight.put(position, height);
//        sumHeight = SumHashItem(itemHeight);
//
//        float density = ProfileFragment.this.getResources().getDisplayMetrics().density;
//        float viewHeight = sumHeight * density;
//        recyclerViewProfileImages.getLayoutParams().height = (int) sumHeight;
//
//        int i = recyclerViewProfileImages.getLayoutParams().height;
//    }
}
