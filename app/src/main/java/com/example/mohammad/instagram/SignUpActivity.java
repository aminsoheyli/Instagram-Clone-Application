package com.example.mohammad.instagram;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SignUpActivity extends AppCompatActivity {
    private EditText email, fullName, username, password;
    private Button signUp;
    private static SQLiteDatabase db;

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
        db = openOrCreateDatabase("project", MODE_PRIVATE, null);

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
                if (isInConflict()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "This user is already exist!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 300);
                    toast.show();

                } else {


                    db.execSQL("insert into user values (" + user +");");

                    db.close();
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

    // Whether it is in conflict with information in database or not.
    private boolean isInConflict() {
        // ...
        return false;
    }
}
