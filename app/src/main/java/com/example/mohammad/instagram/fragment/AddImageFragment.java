package com.example.mohammad.instagram.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.activity.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import static android.app.Activity.RESULT_OK;



public class AddImageFragment extends Fragment {
    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;
    private static Bitmap gottenImage;
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
        camera = rootView.findViewById(R.id.image_of_post);
        description = rootView.findViewById(R.id.description);
        upload = rootView.findViewById(R.id.upload);
        image = rootView.findViewById(R.id.gotten_image);
    }

    private void onClickListeners() {
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImage = new Intent(Intent.ACTION_GET_CONTENT);
                pickImage.setType("image/*");
                startActivityForResult(pickImage, GALLERY_REQUEST);
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

                String postId = getSaltString();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                gottenImage.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] bytes = bos.toByteArray();
                gottenImage = null;
                MainActivity.self.onProfileButtonClicked();
            }
        });

    }

    String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
//            length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(MainActivity.pm) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    final Bundle extras = data.getExtras();
                    try {
                        gottenImage = (Bitmap) extras.get("data");
                        image.setImageBitmap(gottenImage);
                        image.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Log.e("image load", "onActivityResult: loading image from capture", e);
                    }
                }
                break;

            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        gottenImage = MediaStore.Images.
                                Media.getBitmap(MainActivity.cr, selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                        gottenImage = null;
                        return;
                    }
                    image.setImageBitmap(gottenImage);
                    image.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}

