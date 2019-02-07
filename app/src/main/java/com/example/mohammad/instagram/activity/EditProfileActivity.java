package com.example.mohammad.instagram.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammad.instagram.R;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ImageView discardBtn, submitBtn, profileImage;
    private TextView changePhoto;
    private EditText name, username, website, bio, email, phoneNumber;
    private Spinner gender;
    private String[] genderTypes = {"Not Specified", "Male", "Female"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
        onClickListeners();
    }

    private void init() {
        discardBtn = findViewById(R.id.discard_changes);
        submitBtn = findViewById(R.id.submit_changes);
        profileImage = findViewById(R.id.profile_image);
        changePhoto = findViewById(R.id.change_photo_textview);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        website = findViewById(R.id.website);
        bio = findViewById(R.id.bio);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phone_number);
        gender = findViewById(R.id.gender);

        SpinnerAdapter spinnerAdapter;
        gender.setOnItemSelectedListener(this);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderTypes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        gender.setAdapter(aa);

    }

    private void onClickListeners() {
        discardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo: complete the checkInputs() method.
                checkInputs();
                finish();
            }
        });

        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheProfilePhoto();
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheProfilePhoto();
            }
        });


    }

    private void changeTheProfilePhoto() {
    }

    private void checkInputs() {

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Type: " + genderTypes[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
