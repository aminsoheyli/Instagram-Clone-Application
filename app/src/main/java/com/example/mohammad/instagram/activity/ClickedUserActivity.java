package com.example.mohammad.instagram.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mohammad.instagram.ProfileType;
import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.fragment.ProfileFragment;
import com.example.mohammad.instagram.recycler_view.profile.PostAdapter;

/**
 * Created by Mohammad Amin Soheyli on 23/01/2019.
 */
public class ClickedUserActivity extends AppCompatActivity {
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_user);
        init();
    }

    private void init() {
        userId = getIntent().getStringExtra(PostAdapter.CLICKED_USER_ID_KEY);
        if (userId == null)
            new Exception("No user id found to show profile's posts with it.");
        ProfileFragment profileFragment = ProfileFragment.newInstance(userId, ProfileType.CLICKED_USER_PROFILE);
        getSupportFragmentManager().beginTransaction().replace(R.id.clicked_user_profile_fragment_container, profileFragment).commit();
    }
}
