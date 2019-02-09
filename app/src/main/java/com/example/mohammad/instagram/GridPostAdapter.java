package com.example.mohammad.instagram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mohammad.instagram.temp.TestDataGenerator;
import com.github.javafaker.Faker;

public class GridPostAdapter extends RecyclerView.Adapter<GridPostAdapter.GridViewHolder> {
    private View rootView;
    private Faker faker;
    private int lastIndex;
    private int rowSpan;
    private int columnCount = 0;
    private int widthAndHeight;

    public GridPostAdapter() {
        this.faker = new Faker();
        rowSpan = 3;
        int count = getItemCount();
        int extra = count % 3;
        int fromZeroTo = count - extra - 1;
        if (extra == 0)
            fromZeroTo -= rowSpan;
        lastIndex = fromZeroTo;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_grid_profile, viewGroup, false);
        // Measure the width and height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) rootView.getContext()).getWindowManager()
                .getDefaultDisplay().getMetrics(displayMetrics);
        this.widthAndHeight = displayMetrics.widthPixels / 3;
        return new GridViewHolder(rootView);
    }

    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(widthAndHeight, widthAndHeight);

    @Override
    public void onBindViewHolder(@NonNull final GridViewHolder vh, int index) {
        // ToDo: set margins as the commented bellow code
        //Set Margins
//        if (index <= lastIndex && columnCount != 0)
//            params.setMargins(2, 0, 0, 2);
//        else if (index > lastIndex && columnCount != 0)
//            params.setMargins(2, 0, 0, 0);
//        if (getItemCount() <= 3 && columnCount != 0)
//            params.setMargins(2, 0, 0, 0);
//        else
//            params.setMargins(0, 0, 0, 0);
//        vh.image.setLayoutParams(params);
//        vh.image.requestLayout();
//        columnCount = ++columnCount % 3;


        Glide.with(rootView.getContext())
                .load(faker.internet().image())
                .apply(RequestOptions.centerCropTransform())
                .into(vh.image);

        vh.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo: Dialog for long clicked image

//                vh.image.getDrawable();
            }
        });
    }

    @Override
    public int getItemCount() {
        return TestDataGenerator.getItemCount();
    }

    protected static class GridViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private static DisplayMetrics displayMetrics = new DisplayMetrics();

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            ((Activity) itemView.getContext()).getWindowManager()
                    .getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels / 3;
            image.getLayoutParams().width = width;
            image.getLayoutParams().height = width;

        }
    }
}
