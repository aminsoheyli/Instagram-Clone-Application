package com.example.mohammad.instagram;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class AddImageFragment extends Fragment {
    private static Bitmap gottenImage;
    private View rootView;
    private ImageView image;
    private Button gallery, camera, add;
    private int GALLERY_REQUEST = 1;
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
                startActivityForResult(cam_ImagesIntent, GALLERY_REQUEST);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = MainActivity.cr.query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            Log.i("sagsafsafsaf", "onActivityResult: " + bitmap);

            // Do something with the bitmap
            image.setImageBitmap(bitmap);
            gottenImage = bitmap;


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }
}
