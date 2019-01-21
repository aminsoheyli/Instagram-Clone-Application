package com.example.mohammad.instagram.temp;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtility {
    private float dpWidth, dpHeight, density, realPxWidth, realPxHeight, smallestScreenWidth;
    private Activity activity;

    public ScreenUtility(Activity activity) {
        this.activity = activity;

        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        this.realPxWidth = metrics.widthPixels;
        this.realPxHeight = metrics.heightPixels;

        this.density = activity.getResources().getDisplayMetrics().density;

        dpWidth = realPxWidth / density;
        dpHeight = realPxHeight / density;

        smallestScreenWidth = dpWidth <= dpHeight ? dpWidth : dpHeight;
    }

    public float getSmallestScreenWidth() {
        return smallestScreenWidth;
    }

    public float getRealPxWidth() {
        return realPxWidth;
    }

    public float getRealPxHeight() {
        return realPxHeight;
    }

    public float getDpWidth() {
        return dpWidth;
    }

    public float getDensity() {
        return density;
    }

    public float getDpHeight() {
        return dpHeight;
    }

}
