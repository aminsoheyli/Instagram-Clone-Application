package com.example.mohammad.instagram.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;

import com.example.mohammad.instagram.PersonalFragmentType;
import com.example.mohammad.instagram.ProfileType;
import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.fragment.HomeFragment;
import com.example.mohammad.instagram.fragment.PersonalsFragment;
import com.example.mohammad.instagram.fragment.ProfileFragment;
import com.example.mohammad.instagram.recycler_view.comment.CommentCard;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    private CustomPerson person;
    public static final int HOME_TAB_ID = 0;
    //    private ViewPager viewPager;
    public static final int ADD_IMAGE_TAB_ID = 1;
    public static final int PROFILE_TAB_ID = 2;
    public static final int SEARCH_TAB_ID = 3;
    public static final int ACTIVITIES_TAB_ID = 4;
    public static final int DEFAULT_TAB_ID = -1;
    private static final int UPLOAD_REQ = 1;
    private static final String CURRENT_SATET_TAG = "currentTabState";
    public static int currentTabState = DEFAULT_TAB_ID;
    public static String currentUserId;
    public static ContentResolver cr;
    public static PackageManager pm;
    public static MainActivity self;
    public static FragmentManager fm;

    public static boolean isDirectedToOtherProfiles = false;
    private ImageView addButton, profileButton, homeButton, searchButton, activitiesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initials();
        onClickListeners();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    private void initials() {
        self = this;
        pm = getPackageManager();
        cr = getContentResolver();
        fm = getSupportFragmentManager();
        addButton = findViewById(R.id.add_tab);
        homeButton = findViewById(R.id.home_tab);
        profileButton = findViewById(R.id.profile_tab);
        searchButton = findViewById(R.id.search_tab);
        activitiesButton = findViewById(R.id.activity_tab);

        onProfileButtonClicked();

    }

    private void onClickListeners() {

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeBackOtherImageResources(HOME_TAB_ID)) {
                    homeButton.setImageResource(R.drawable.home_icon_fill);
//                    PersonalsFragment personalsFragment = PersonalsFragment.newInstance(PersonalFragmentType.HOME_FRAGMENT, null);
//                    getSupportFragmentManager().beginTransaction().addToBackStack(null);
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, personalsFragment).commit();
                    HomeFragment homeFragment = HomeFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().addToBackStack(null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                            , homeFragment).commit();
                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeBackOtherImageResources(ADD_IMAGE_TAB_ID)) {
//                    addButton.setImageResource(R.drawable.plus_icon_fill);
                    /*AddImageFragment addImageFragment = new AddImageFragment();
                    getSupportFragmentManager().beginTransaction().addToBackStack(null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addImageFragment).commit();*/
                    Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                    startActivityForResult(intent, UPLOAD_REQ);
                }
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileButtonClicked();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeBackOtherImageResources(SEARCH_TAB_ID)) {
                    searchButton.setImageResource(R.drawable.search_icon_fill);
                    PersonalsFragment personalsFragment = PersonalsFragment.newInstance(PersonalFragmentType.SEARCH_FRAGMENT, null);
                    getSupportFragmentManager().beginTransaction().addToBackStack(null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, personalsFragment).commit();
                }
            }
        });
        activitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeBackOtherImageResources(ACTIVITIES_TAB_ID)) {
                    activitiesButton.setImageResource(R.drawable.like_icon_fill_black);
                    PersonalsFragment personalsFragment = PersonalsFragment.newInstance(PersonalFragmentType.ACTIVITY_FRAGMENT, null);
                    getSupportFragmentManager().beginTransaction().addToBackStack(null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, personalsFragment).commit();
                }
            }
        });

    }

    public void onProfileButtonClicked() {
        if (changeBackOtherImageResources(PROFILE_TAB_ID)) {
            profileButton.setImageResource(R.drawable.user_icon_fill);
            ProfileFragment profileFragment = ProfileFragment.newInstance(MainActivity.currentUserId, ProfileType.LOGGED_IN_USER_PROFILE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(CURRENT_SATET_TAG, currentTabState);
    }

    /**
     * @param pressedIconState Id of pressed icon.
     * @return True if the user pressed a new icon, false if the user pressed the current tab icon.
     */
    private boolean changeBackOtherImageResources(int pressedIconState) {
        int preTabState = currentTabState;
        currentTabState = pressedIconState;
        if (preTabState == currentTabState)
            return false;
        switch (preTabState) {
            case HOME_TAB_ID:
                homeButton.setImageResource(R.drawable.home_icon_stroke);
                break;
            case ADD_IMAGE_TAB_ID:
//                addButton.setImageResource(R.drawable.plus_icon_stroke);
                break;
            case PROFILE_TAB_ID:
                profileButton.setImageResource(R.drawable.user_icon_stroke);
                break;
            case SEARCH_TAB_ID:
                searchButton.setImageResource(R.drawable.search_icon_stroke);
                break;
            case ACTIVITIES_TAB_ID:
                activitiesButton.setImageResource(R.drawable.like_icon_stroke_black);
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentTabState = -1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case UPLOAD_REQ:
                if (requestCode == RESULT_OK) {

                }
                break;
        }
    }
}
