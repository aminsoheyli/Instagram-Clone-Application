package com.example.mohammad.instagram.temp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.recycler_view.profile.ProfileCard;

import java.util.ArrayList;
import java.util.Random;

public class TestDataGenerator {

    public static ArrayList<ProfileCard> generateSomePosts(Context context) {
        ArrayList<ProfileCard> informations = new ArrayList<>();
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.like_icon_fill);
        ProfileCard first =
                new ProfileCard(null, bm
                        , "example"
                        , "16 likes"
                        , "This is a example's dynamic description"
                        , "2 Days ago", true, false);
        bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.like_icon_stroke);
        ProfileCard second =
                new ProfileCard(null, bm
                        , "alisafri98"
                        , "120 likes"
                        , "This is a Ali Safari's dynamic description "
                        , "14 May 2018",
                        false, true);
        bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.instagram_icon);
        ProfileCard third =
                new ProfileCard(null, bm
                        , "amisoheyli77"
                        , "200 likes"
                        , "This is a Amin Soheyli's dynamic description"
                        , "20 minutes ago", false, false);
        bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.saved_icon_fill);
        ProfileCard fourth =
                new ProfileCard(null, bm
                        , "test19"
                        , "17 likes"
                        , "This is a test's dynamic description"
                        , "Just now", true, true);
        Random random = new Random();

        ProfileCard test;
        for (int i = 0; i < 25; i++) {
            int x = random.nextInt(4) + 1;
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
        return informations;
    }
}
