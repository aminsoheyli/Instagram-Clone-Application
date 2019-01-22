package com.example.mohammad.instagram.activity;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;

import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.fragment.AddImageFragment;
import com.example.mohammad.instagram.fragment.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    private CustomPerson person;
//    private ViewPager viewPager;
    public static final int HOME_ID = 0;
    public static final int ADD_IMAGE_ID = 1;
    public static final int PROFILE_ID = 2;
    public static final int GLOBAL_ID = 3;
    private static final String CURRENT_SATET_TAG = "currentTabState";
    public static String currentUserId;
    public static SQLiteDatabase db;
    public static ContentResolver cr;
    public static PackageManager pm;
    public static int currentTabState = -1;
    public static FragmentManager sfm;
    public static MainActivity self;
    private ImageView addButton, profileButton, homeButton, globalButton;

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
        addButton = findViewById(R.id.add_tab);
        homeButton = findViewById(R.id.home_tab);
        profileButton = findViewById(R.id.profile_tab);
        globalButton = findViewById(R.id.global_tab);

        onProfileButtonClicked();

        // ViewPager ...........

//        viewPager = findViewById(R.id.view_pager);
//        setUpViewPager();


//        TabLayout tabLayout = new TabLayout(this);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        addContentView(tabLayout, params);
//        tabLayout.setupWithViewPager(viewPager);
    }

    private void onClickListeners() {

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeBackOtherImageResources(HOME_ID)) {
                    homeButton.setImageResource(R.drawable.home_icon_fill);

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeBackOtherImageResources(ADD_IMAGE_ID)) {
                    addButton.setImageResource(R.drawable.plus_icon_fill);
                    AddImageFragment addImageFragment = new AddImageFragment();
                    getSupportFragmentManager().beginTransaction().addToBackStack(null);
                    sfm = getSupportFragmentManager();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addImageFragment).commit();
                }
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileButtonClicked();
            }
        });

        globalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeBackOtherImageResources(GLOBAL_ID)) {
                    globalButton.setImageResource(R.drawable.global_icon_fill);

                }
            }
        });

    }

    public void onProfileButtonClicked() {
        if (changeBackOtherImageResources(PROFILE_ID)) {
            profileButton.setImageResource(R.drawable.user_icon_fill);
            ProfileFragment profileFragment = new ProfileFragment();
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
            case HOME_ID:
                homeButton.setImageResource(R.drawable.home_icon_stroke);
                break;
            case ADD_IMAGE_ID:
                addButton.setImageResource(R.drawable.plus_icon_stroke);
                break;
            case PROFILE_ID:
                profileButton.setImageResource(R.drawable.user_icon_stroke);
                break;
            case GLOBAL_ID:
                globalButton.setImageResource(R.drawable.global_icon_stroke);
                break;
            default:
                break;
        }
        return true;
    }

    //Returns a list of list
    //index 0 returns the comments text
    //index 1 returns commenter id
    private List<List<String>> comments(String postId) {
        Cursor c = MainActivity.db.rawQuery("select * from comment where post_id ='" + postId + "');", null);
        if (c.getColumnCount() != 0) {
            List commentsData = new ArrayList<ArrayList<String>>();
            commentsData.add(new ArrayList<String>());
            commentsData.add(new ArrayList<String>());
            c.moveToFirst();
            do {
                ((ArrayList) commentsData.get(0)).add(c.getString(0));
                ((ArrayList) commentsData.get(1)).add(c.getString(3));
            } while (c.moveToNext());
            return commentsData;
        } else {
            return null;
        }
    }

    private void liking(String postId, String currentUserId) {
        MainActivity.db.execSQL("insert into likes values('" + postId + "','" + currentUserId + "');");
    }

    private void save(String postId, String currentUserId) {
        MainActivity.db.execSQL("insert into save values('" + postId + "','" + currentUserId + "');");
    }

    private void disLiking(String postId, String currentUserId) {
        MainActivity.db.execSQL("delete from likes where post_id = '" + postId + "' and user_id = '" + currentUserId + "';");
    }

    private void unsave(String postId, String currentUserId) {
        MainActivity.db.execSQL("delete from save where post_id = '" + postId + "' and user_id = '" + currentUserId + "';");
    }

    private List followersName(String currentUserId) {
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

    private List followingsName(String currentUserId) {
        Cursor c = MainActivity.db.rawQuery("select distinct * from follow where follower_id ='" + currentUserId + "');", null);
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

    private void follow(String toFollowUserId, String currentUserId) {
        MainActivity.db.execSQL("insert into follow values('" + toFollowUserId + "','" + currentUserId + "'); ");
    }

    private void unfollow(String currentUserId, String toUnfolloweUserId) {
        MainActivity.db.execSQL("delete from follow where follower_id = '" + currentUserId + "' and user_id = '" + toUnfolloweUserId + "'); ");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentTabState = -1;
        db.close();
    }
}
