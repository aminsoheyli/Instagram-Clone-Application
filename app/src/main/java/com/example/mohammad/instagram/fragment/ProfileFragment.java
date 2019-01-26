package com.example.mohammad.instagram.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
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

import com.example.mohammad.instagram.DirectableType;
import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.activity.EditProfileActivity;
import com.example.mohammad.instagram.activity.LoginActivity;
import com.example.mohammad.instagram.activity.MainActivity;
import com.example.mohammad.instagram.recycler_view.profile.ProfileAdapter;
import com.example.mohammad.instagram.recycler_view.profile.ProfileCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final int EDIT_PROFILE_REQ_CODE = 1;
    private static final String USER_ID_KEY = "USER_ID";
    private CircleImageView profileImage;
    private TextView postsNumbers, followersNumbers,
            followingNumbers, name, biography, profileImageName;
    private View followersParent, followingParent;
    private Button editProfile;

    private String userId;
    private RecyclerView recyclerViewProfileImages;
    private View signout;

    private ProfileFragment() {

    }

    public static ProfileFragment newInstance(String userId) {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        args.putString(USER_ID_KEY, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userId = getArguments().getString(USER_ID_KEY);
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
        name.setText(this.userId);
        biography = rootView.findViewById(R.id.biography);
        editProfile = rootView.findViewById(R.id.edit_profile);
        signout = rootView.findViewById(R.id.sign_out);
        followingParent = rootView.findViewById(R.id.following_parent);
        followersParent = rootView.findViewById(R.id.followers_parent);
        profileImageName = rootView.findViewById(R.id.profile_image_name);
        profileImageName.setText(this.userId.charAt(0) + "");
        recyclerViewProfileImages = rootView.findViewById(R.id.recycler_view_profile_images);
        prepareProfileImagesRecyclerView();


        String bioText;
        if ((bioText = hasBiography()) != null && bioText.length() != 0) {
            biography.setVisibility(View.VISIBLE);
            biography.setText(bioText);
        }

        prepareNumbers();


    }

    private void prepareNumbers() {
        Cursor c = MainActivity.db.rawQuery("select count(post_id) from post where user_id = '" + this.userId + "';", null);
        if (c.moveToFirst()) {
            postsNumbers.setText(c.getString(0));
        }

        c = MainActivity.db.rawQuery("select count(follower_id) from follow where user_id = '" + this.userId + "';", null);
        if (c.moveToFirst()) {
            followersNumbers.setText(c.getString(0));
        }

        c = MainActivity.db.rawQuery("select count(user_id) from follow where follower_id = '" + this.userId + "';", null);
        if (c.moveToFirst()) {
            followingNumbers.setText(c.getString(0));
        }
        c.close();

    }

    private void prepareProfileImagesRecyclerView() {
        recyclerViewProfileImages.setNestedScrollingEnabled(false);
        recyclerViewProfileImages.setHasFixedSize(true);
        ArrayList<ProfileCard> informations;
        informations = prepareInformations();
/**        Fake data generator*/
//        ProfileCard first =

//                new ProfileCard(R.drawable.like_icon_fill
//                        , R.drawable.instagram_icon
//                        , "example"
//                        , "16 likes"
//                        , "This is a example's dynamic description"
//                        , "2 Days ago");
//        ProfileCard second =
//                new ProfileCard(R.drawable.like_icon_stroke
//                        , R.drawable.saved_icon_stroke
//                        , "alisafri98"
//                        , "120 likes"
//                        , "This is a Ali Safari's dynamic description "
//                        , "14 May 2018");
//        ProfileCard third =
//                new ProfileCard(R.drawable.instagram_icon
//                        , R.drawable.like_icon_fill
//                        , "amisoheyli77"
//                        , "200 likes"
//                        , "This is a Amin Soheyli's dynamic description"
//                        , "20 minutes ago");
//        ProfileCard fourth =
//                new ProfileCard(R.drawable.saved_icon_fill
//                        , R.drawable.comment_icon
//                        , "test19"
//                        , "17 likes"
//                        , "This is a test's dynamic description"
//                        , "Just now");
//        Random random = new Random();
//
//        ProfileCard test;
//        for (int i = 0; i < 25; i++) {
//            int x = random.nextInt(3) + 1;
//            switch (x) {
//                case 1:
//                    test = first;
//                    break;
//                case 2:
//                    test = second;
//                    break;
//                case 3:
//                    test = third;
//                    break;
//                case 4:
//                    test = fourth;
//                    break;
//                default:
//                    test = first;
//                    break;
//            }
//            informations.add(test);
//        }
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewProfileImages.setLayoutManager(llm);
        ProfileAdapter adapter = new ProfileAdapter(informations, DirectableType.PROFILE_FRAGMENT);
        recyclerViewProfileImages.setAdapter(adapter);

    }

    private ArrayList<ProfileCard> homeFragmentInformationsQuery() {
        ArrayList<ProfileCard> informations = new ArrayList<>();
        // Home fragment informations query
        Cursor c = MainActivity.db.rawQuery("select distinct * from post where user_id ='" + this.userId + "' order by post.post_date desc;", null);

        if (c.moveToFirst()) {
            do {
                Cursor cc = MainActivity.db.rawQuery("select count(user_id) from likes where post_id = '" + c.getString(0) + "';", null);
                Cursor ccc = MainActivity.db.rawQuery("select count(user_id) from likes where user_id = '" + this.userId + "' and post_id = '" + c.getString(0) + "';", null);
                Cursor cccc = MainActivity.db.rawQuery("select count(user_id) from save where user_id = '" + this.userId + "'  and post_id = '" + c.getString(0) + "';", null);
                boolean liked = false;
                boolean saved = false;
                if (ccc.moveToFirst()) {
                    liked = (ccc.getString(0).matches("0")) ? false : true;
                }
                if (cccc.moveToFirst()) {
                    saved = (cccc.getString(0).matches("0")) ? false : true;
                }
                if (cc.moveToFirst()) {
                    ProfileCard temp =
                            new ProfileCard(c.getString(0), BitmapFactory.decodeByteArray(c.getBlob(3), 0, c.getBlob(3).length),
                                    c.getString(1),
                                    cc.getString(0),
                                    c.getString(4),
                                    c.getString(2),
                                    liked,
                                    saved
                            );
                    informations.add(temp);
                    cc.close();

                }
            }
            while (c.moveToNext());

        }
        c.close();
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
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("Id", 2);
                intent.putExtra("bundle", extras);
                startActivityForResult(intent, EDIT_PROFILE_REQ_CODE);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                MainActivity.db.execSQL("delete from last_user where 1;");
                MainActivity.self.finish();
                startActivity(intent);
            }
        });
    }


    private void showFollowers() {
        ArrayList<String> informations = new ArrayList<>();
        FollowersFolloingFragment fragment = FollowersFolloingFragment.newInstance(informations);
        fragment.show(getFragmentManager(), "Follows fragment");
    }

    private void showFollowing() {
        ArrayList<String> informations;
        informations = (ArrayList<String>) MainActivity.followingsName(this.userId);
        FollowersFolloingFragment fragment = FollowersFolloingFragment.newInstance(informations);
        fragment.show(getFragmentManager(), "Follows fragment");
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
