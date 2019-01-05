package com.example.mohammad.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProfileImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        recyclerViewProfileImages = findViewById(R.id.rv);
        ArrayList<ProfileCardInformations> informations = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProfileCardInformations info =
                    new ProfileCardInformations(R.drawable.like_icon_fill
                            , R.drawable.instagram_icon
                            , "aminsoheyli77"
                            , "120 likes"
                            , "This is a test dynamic description"
                            , "14/9/12");
            informations.add(info);
        }
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewProfileImages.setLayoutManager(llm);
        ProfileImagesAdapter adapter = new ProfileImagesAdapter(informations);
        recyclerViewProfileImages.setAdapter(adapter);
    }
}
