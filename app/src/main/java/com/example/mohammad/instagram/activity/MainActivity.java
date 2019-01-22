package com.example.mohammad.instagram.activity;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
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

public class MainActivity extends AppCompatActivity {
    //    private CustomPerson person;
//    private ViewPager viewPager;
    public static final int HOME_ID = 0;
    public static final int ADD_IMAGE_ID = 1;
    public static final int PROFILE_ID = 2;
    private static final String CURRENT_SATET_TAG = "currentTabState";
    public static String currentUserId;
    public static SQLiteDatabase db;
    public static ContentResolver cr;
    public static PackageManager pm;
    public static int currentTabState = -1;
    public static ImageView homeButton;
    public static FragmentManager sfm;
    public static MainActivity self;
    private ImageView addButton, profileButton;

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
            default:
                break;
        }
        return true;
    }
//
//    public static void changeOtherImage(int pressedIconState){
//
//    }

//    private void setUpViewPager() {
//        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
//        ProfileFragment profileFragment = new ProfileFragment();
//        ProfileFragment profileFragment2 = new ProfileFragment();
//        pagerAdapter.addFragment(profileFragment);
//        pagerAdapter.addFragment(profileFragment2);
//        viewPager.setAdapter(pagerAdapter);
//    }


//    private class MyPagerAdapter extends FragmentPagerAdapter {
//        List<Fragment> fragments;
//
//        public MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//            fragments = new ArrayList<>();
//        }
//
//        public void addFragment(Fragment fragment) {
//            fragments.add(fragment);
//            notifyDataSetChanged();
//        }
//
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragments.size();
//        }
//
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentTabState = -1;
        db.close();
        self = null;
    }
}