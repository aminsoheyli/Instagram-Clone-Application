package com.example.mohammad.instagram.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mohammad.instagram.R;

public class EditProfileActivity extends AppCompatActivity {
    private RecyclerView recyclerViewProfileImages;
    private EditText user;
    private EditText pass;
    private Button updateUsername;
    private Button updatePassword;
    private TextView toggle;
    private boolean isUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        isUsername = false;
        user = findViewById(R.id.field);
        pass = findViewById(R.id.field_pass);
        updateUsername = findViewById(R.id.update);
        updatePassword = findViewById(R.id.update_pass);

//        toggle = findViewById(R.id.toggle);
//        toggle.setText("Enter your new password:");

        updateUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = user.getText().toString();
                updateUserId(s, MainActivity.currentUserId);
                MainActivity.currentUserId = s;
//                user.getText().clear();
                finish();

            }
        });
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = pass.getText().toString();
                updatePassword(s, getOldPass(MainActivity.currentUserId));
//                    MainActivity.currentUserId = s;
//                user.getText().clear();
                finish();

            }
        });


    }

    private String getOldPass(String userId) {
        Cursor c = MainActivity.db.rawQuery("select user_password from user where user_id = '" + userId + "';", null);
        if (c.moveToFirst()) {
            return c.getString(0);
        }
        return null;
    }

    public void updateUserId(String newId, String oldId) {
        MainActivity.db.execSQL("update user set user_id = '" + newId + "' where user_id = '" + oldId + "';");
        MainActivity.db.execSQL("update comment set user_id = '" + newId + "' where user_id = '" + oldId + "';");
        MainActivity.db.execSQL("update post set user_id = '" + newId + "' where user_id = '" + oldId + "';");
        MainActivity.db.execSQL("update follow set user_id = '" + newId + "' where user_id = '" + oldId + "';");
        MainActivity.db.execSQL("update save set user_id = '" + newId + "' where user_id = '" + oldId + "';");
        MainActivity.db.execSQL("update likes set user_id = '" + newId + "' where user_id = '" + oldId + "';");
    }

    public void updatePassword(String newPass, String oldPass) {
        MainActivity.db.execSQL("update user set user_password = '" + newPass + "' where user_id = '" + oldPass + "'");
    }
}
