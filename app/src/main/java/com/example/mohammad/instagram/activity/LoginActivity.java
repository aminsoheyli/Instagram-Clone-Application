package com.example.mohammad.instagram.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammad.instagram.R;

/**
 * Created by Mohammad Amin Soheyli on 02/01/2019.
 */
public class LoginActivity extends AppCompatActivity {
    private static final int SIGN_UP_REQUEST_CODE = 1;
    private EditText username, password;
    private Button login;
    private TextView signUp;
    private View userRoundContainer, passwordRoundContainer;
    private ImageView userIcon, passwordIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initials();
        clickListeners();

    }


    private void initials() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        userRoundContainer = findViewById(R.id.user_container_round);
        passwordRoundContainer = findViewById(R.id.password_container_round);
        signUp = findViewById(R.id.sign_up);
        userIcon = findViewById(R.id.user_icon);
        passwordIcon = findViewById(R.id.password_icon);
        login = findViewById(R.id.login);
        login.setClickable(false);

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int normalUser = R.drawable.ic_user, focusedUser = R.drawable.ic_user_focused;
            int normalUserContainer = R.drawable.edittext_stroke,
                    focusedUserContainer = R.drawable.edittext_stroke_focused;

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int drawableId = hasFocus ? focusedUser : normalUser;
                int containerId = hasFocus ? focusedUserContainer : normalUserContainer;
                userIcon.setImageResource(drawableId);
                userRoundContainer.setBackground(getResources().getDrawable(containerId));
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int normalPass = R.drawable.ic_lock, focusedPass = R.drawable.ic_lock_focused;
            int normalPassContainer = R.drawable.edittext_stroke,
                    focusedPassContainer = R.drawable.edittext_stroke_focused;

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int drawableId = hasFocus ? focusedPass : normalPass;
                int containerId = hasFocus ? focusedPassContainer : normalPassContainer;
                passwordIcon.setImageResource(drawableId);
                passwordRoundContainer.setBackground(getResources().getDrawable(containerId));
            }
        });
    }

    private void clickListeners() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
//                if (user.isEmpty()) {
//                    setFocus(username, "username");
//                    return;
//                }
//                if (pass.isEmpty()) {
//                    setFocus(password, "password");
//                    return;
//                }
                if (isCorrect(user, pass)) {
                    Toast.makeText(LoginActivity.this, "User Successfully Logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    MainActivity.currentUserId = user;
                    startActivity(intent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Username or Password is wrong!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 200);
                    toast.show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_UP_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK)
                finish();
        }
    }

    // Method for authentication.
    private boolean isCorrect(String user, String pass) {
        return true;
    }

}
