package com.example.mohammad.instagram.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mohammad.instagram.DirectableType;
import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.recycler_view.profile.ProfileAdapter;
import com.example.mohammad.instagram.recycler_view.profile.ProfileCard;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {
    private RecyclerView recyclerViewProfileImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        recyclerViewProfileImages = findViewById(R.id.rv);
        ArrayList<ProfileCard> informations = new ArrayList<>();
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
//
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
//                    test = null;
//                    break;
//            }
//            test = fourth;
//            informations.add(test);
//        }
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewProfileImages.setLayoutManager(llm);
        ProfileAdapter adapter = new ProfileAdapter(informations, DirectableType.GLOBAL_FRAGMENT);
        new Exception("handel above coed");
        recyclerViewProfileImages.setAdapter(adapter);
    }
}
