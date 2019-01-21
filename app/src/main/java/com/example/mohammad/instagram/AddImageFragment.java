package com.example.mohammad.instagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static android.app.Activity.RESULT_OK;

public class AddImageFragment extends Fragment {
    private static Bitmap gottenImage;
    private View rootView;
    private ImageView image;
    private Button gallery, camera, add;
    private int CAMERA_REQUEST = 1;
    private EditText description;

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
        camera = rootView.findViewById(R.id.camera);
        description = rootView.findViewById(R.id.description);
        add = rootView.findViewById(R.id.add);
        image = rootView.findViewById(R.id.gotten_image);
    }

    private void onClickListeners() {
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cam_ImagesIntent = new Intent(Intent.ACTION_PICK);
                cam_ImagesIntent.setType("image/*");
                startActivityForResult(cam_ImagesIntent, CAMERA_REQUEST);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!description.getText().toString().matches("")) {
//                    MainActivity.db.execSQL("insert into ");
                }
            }
        });

    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(MainActivity.pm) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            final Bundle extras = data.getExtras();
            try {
                gottenImage = (Bitmap)extras.get("data");
                image.setImageBitmap(gottenImage);
            }catch (Exception e){
                Log.e("image load", "onActivityResult: loading image from capture", e);
            }
        }
    }
}
