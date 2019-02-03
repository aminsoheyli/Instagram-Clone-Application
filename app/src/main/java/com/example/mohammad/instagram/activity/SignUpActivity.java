package com.example.mohammad.instagram.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohammad.instagram.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText email, fullName, username, password;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initials();
        onClickListeners();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private void initials() {

        email = findViewById(R.id.email);
        fullName = findViewById(R.id.full_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signUp = findViewById(R.id.sign_up);
    }

    private void onClickListeners() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String mail = email.getText().toString();
//                String name = fullName.getText().toString();
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (user.isEmpty()) {
                    setFocus(username, "username");
                    return;
                }
                if (pass.isEmpty()) {
                    setFocus(password, "password");
                    return;
                }
                if (isInConflict()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "This user is already exist!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 300);
                    toast.show();

                } else {



                    // If registration process was successful.
                    setResult(Activity.RESULT_OK);
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    // Send the user primary key with the intent.
                    intent.putExtra("userPrimaryKey", 1);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setFocus(EditText field, String error) {
        Toast toast = Toast.makeText(getApplicationContext(), "Enter your " + error + "!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        field.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(field, InputMethodManager.SHOW_IMPLICIT);
    }

    // Whether it is in conflict with information in database or not.
    private boolean isInConflict() {
        // ...
        return false;
    }
}
