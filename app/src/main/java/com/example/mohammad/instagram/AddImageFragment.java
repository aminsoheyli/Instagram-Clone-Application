package com.example.mohammad.instagram;

import android.content.Intent;
import android.database.sqlite.SQLiteStatement;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class AddImageFragment extends Fragment {
    private static Bitmap gottenImage;
    private static int CAMERA_REQUEST = 1;
    private static int GALLERY_REQUEST = 2;
    private View rootView;
    private ImageView image;
    private Button gallery, camera, upload;
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
        upload = rootView.findViewById(R.id.upload);
        image = rootView.findViewById(R.id.gotten_image);
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
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gottenImage == null) {
                    Toast.makeText(getContext(), "Take or pick an image!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (description.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Add a description!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteStatement sqLiteStatement = MainActivity.db.compileStatement("insert into post values(?,?,?,?,?);");
                sqLiteStatement.bindString(1, new Random().nextLong() + "");
                sqLiteStatement.bindString(2, MainActivity.currentUserId);
                sqLiteStatement.bindString(3, new Date().toString());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                gottenImage.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] bytes = bos.toByteArray();
                sqLiteStatement.bindBlob(4, bytes);
                sqLiteStatement.bindString(5, description.getText().toString());
                sqLiteStatement.execute();
//                    goToHomeFragment();
                MainActivity.self.onProfileButtonClicked();
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
                gottenImage = (Bitmap) extras.get("data");
                image.setImageBitmap(gottenImage);
                image.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Log.e("image load", "onActivityResult: loading image from capture", e);
            }
        }

        if (requestCode == GALLERY_REQUEST && requestCode == RESULT_OK) {

        }
    }
}
