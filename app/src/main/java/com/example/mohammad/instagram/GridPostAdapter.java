package com.example.mohammad.instagram;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mohammad.instagram.activity.MainActivity;
import com.example.mohammad.instagram.temp.TestDataGenerator;
import com.github.javafaker.Faker;

import java.util.ArrayList;

public class GridPostAdapter extends RecyclerView.Adapter<GridPostAdapter.GridViewHolder> {
    private View rootView;
    private Faker faker;
    private int lastIndex;
    private int rowSpan;
    private int columnCount = 0;
    private int widthAndHeight;
    private ArrayList<Bitmap> images;
    private boolean isClicked = false;
    private PreviewDialogFragment previewDialogFragment;

    public GridPostAdapter(ArrayList<Bitmap> imagesBitmap) {
        this.images = imagesBitmap;
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
    public void onBindViewHolder(@NonNull final GridViewHolder vh, final int index) {
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


//        vh.image.setImageBitmap(images.get(index));
        Glide.with(rootView.getContext())
                .asBitmap()
                .load(faker.internet().image())
                .apply(RequestOptions.centerCropTransform())
                .into(vh.image);

        vh.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo: Dialog for long clicked image
                isClicked = true;

                Faker faker = new Faker();
                Bitmap bitmap = BitmapFactory.decodeResource(rootView.getContext().getResources(),
                        R.drawable.grid_icon_blue);

//                try {
//                    bitmap = Glide.with(rootView.getContext())
//                            .asBitmap()
//                            .load(faker.internet().image())
//                            .submit()
//                            .get();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                Bitmap bitmapImage = null;
                if (vh.image.getDrawable() instanceof BitmapDrawable) {
                    bitmapImage = ((BitmapDrawable) vh.image.getDrawable()).getBitmap();
                } else {
                    Drawable d = vh.image.getDrawable();
                    bitmapImage = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    d.draw(canvas);
                }
                PreviewDetail previewDetail = new PreviewDetail("hasan gholi", bitmap, bitmapImage, true);
                previewDialogFragment = PreviewDialogFragment.newInstance(previewDetail);
                previewDialogFragment.show(MainActivity.fm, "key");
//                vh.image.getDrawable();
            }
        });
        vh.image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    Toast.makeText(rootView.getContext(), "ACTION_BUTTON_PRESS", Toast.LENGTH_SHORT).show();
                // We're only interested in when the button is released.
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Toast.makeText(rootView.getContext(), "ACTION_UP", Toast.LENGTH_SHORT).show();
                    // Do something when the button is released.
                    isClicked = false;

                    // We're only interested in anything if our speak button is currently pressed.
                    if (isClicked) {
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_BUTTON_RELEASE)
                    Toast.makeText(rootView.getContext(), "ACTION_BUTTON_RELEASE", Toast.LENGTH_SHORT).show();

                return false;
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

