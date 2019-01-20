package com.example.mohammad.instagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AddImageFragment extends Fragment {
    private View rootView;
    private Button gallery, image;
    private int GALLERY_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_image, container, false);
        initials(rootView);
        onClickListeners();
        return rootView;
    }

    private void initials(View rootView) {
        gallery = rootView.findViewById(R.id.gallery);
        image = rootView.findViewById(R.id.image);
    }

    private void onClickListeners() {
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cam_ImagesIntent = new Intent(Intent.ACTION_PICK);
                cam_ImagesIntent.setType("image/*");
                startActivityForResult(cam_ImagesIntent, GALLERY_REQUEST);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
    }
}
