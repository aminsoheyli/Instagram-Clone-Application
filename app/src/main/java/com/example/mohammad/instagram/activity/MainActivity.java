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
import android.widget.ImageView;

import com.example.mohammad.instagram.DirectableType;
import com.example.mohammad.instagram.ProfileType;
import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.fragment.DirectablesFragment;
import com.example.mohammad.instagram.fragment.ProfileFragment;
import com.example.mohammad.instagram.recycler_view.comment.CommentCard;

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
    public static SQLiteDatabase db;
    public static ContentResolver cr;
    public static PackageManager pm;
    public static MainActivity self;
    public static FragmentManager fm;

    public static boolean isDirectedToOtherProfiles = false;
    private ImageView addButton, profileButton, homeButton, searchButton, activitiesButton;

    public static void like(String postId, String currentUserId) {
        MainActivity.db.execSQL("insert into likes values('" + postId + "','" + currentUserId + "');");
    }

    public static void dislike(String postId, String currentUserId) {
        MainActivity.db.execSQL("delete from likes where post_id = '" + postId + "' and user_id = '" + currentUserId + "';");
    }

    public static void save(String postId, String currentUserId) {
        MainActivity.db.execSQL("insert into save values('" + postId + "','" + currentUserId + "');");
    }

    public static void unsave(String postId, String currentUserId) {
        MainActivity.db.execSQL("delete from save where post_id = '" + postId + "' and user_id = '" + currentUserId + "';");
    }

    public static List followersName(String currentUserId) {
        Cursor c = MainActivity.db.rawQuery("select distinct * from follow where user_id ='" + currentUserId + "');", null);
        if (c.getColumnCount() != 0) {
            List comments = new ArrayList<String>();
            c.moveToFirst();
            do {
                comments.add(c.getString(0));
            } while (c.moveToNext());
            return comments;
        } else {
            return null;
        }
    }

    public static List followingsName(String currentUserId) {
        Cursor c = MainActivity.db.rawQuery("select distinct * from follow where follower_id ='" + currentUserId + "';", null);
        if (c.getCount() != 0 && c != null) {
            List followings = new ArrayList<String>();
            c.moveToFirst();
            do {
                followings.add(c.getString(0));
            } while (c.moveToNext());
            return followings;
        } else {
            return new ArrayList();
        }
    }

    public static void follow(String toFollowUserId, String currentUserId) {
        MainActivity.db.execSQL("insert into follow values('" + toFollowUserId + "','" + currentUserId + "'); ");
    }

    public static void unfollow(String currentUserId, String toUnfolloweUserId) {
        MainActivity.db.execSQL("delete from follow where follower_id = '" + currentUserId + "' and user_id = '" + toUnfolloweUserId + "'; ");
    }

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
        db = openOrCreateDatabase("project", MODE_PRIVATE, null);
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
                    DirectablesFragment directablesFragment = DirectablesFragment.newInstance(DirectableType.HOME_FRAGMENT, null);
                    getSupportFragmentManager().beginTransaction().addToBackStack(null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, directablesFragment).commit();
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
                    DirectablesFragment directablesFragment = DirectablesFragment.newInstance(DirectableType.SEARCH_FRAGMENT, null);
                    getSupportFragmentManager().beginTransaction().addToBackStack(null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, directablesFragment).commit();
                }
            }
        });
        activitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeBackOtherImageResources(ACTIVITIES_TAB_ID)) {
                    activitiesButton.setImageResource(R.drawable.like_icon_fill_black);
                    DirectablesFragment directablesFragment = DirectablesFragment.newInstance(DirectableType.SAVED_FRAGMENT, null);
                    getSupportFragmentManager().beginTransaction().addToBackStack(null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, directablesFragment).commit();
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

    //Returns a list of list
    //index 0 returns the comments text
    //index 1 returns commenter id
    private List<CommentCard> comments(String postId) {
        Cursor c = MainActivity.db.rawQuery("select * from comment where post_id ='" + postId + "');", null);
        if (c.getColumnCount() != 0) {
            List commentsData = new ArrayList<CommentCard>();
            c.moveToFirst();
            do {
                commentsData.add(new CommentCard(c.getString(3), c.getString(0)));
            } while (c.moveToNext());
            return commentsData;
        } else {
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentTabState = -1;
        db.close();
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
