package com.example.mohammad.instagram.recycler_view.profile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.activity.MainActivity;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mohammad Amin Soheyli on 04/01/2019.
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    //    private DynamicHeight dynamicHeight;
    private ArrayList<ProfileCard> informations;

    public ProfileAdapter(ArrayList<ProfileCard> informations) {
        this.informations = informations;
    }

//    public ProfileAdapter(ArrayList<ProfileCard> informations, DynamicHeight dynamicHeight, Context context) {
//        this.informations = informations;
//        this.dynamicHeight = dynamicHeight;
//    }


    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_profile_image, viewGroup, false);
        return new ProfileViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileViewHolder viewHolder, final int i) {

        String username = informations.get(i).getUsername();
        viewHolder.usernameProfile.setText(username);
        String firstChar = String.valueOf(username.charAt(0));
        viewHolder.profileImageName.setText(firstChar);
        viewHolder.image.setImageBitmap(informations.get(i).getImage());
        viewHolder.likes.setText(informations.get(i).getLikeNumber());
        viewHolder.usernameDescription.setText(username);
        viewHolder.description.setText(informations.get(i).getDescription());
        viewHolder.date.setText(informations.get(i).getDate());
        if (informations.get(i).isLiked()) {
            viewHolder.like.setImageResource(R.drawable.like_icon_fill);
        } else {
            viewHolder.like.setImageResource(R.drawable.like_icon_stroke);
        }
        if (informations.get(i).isSaved()) {
            viewHolder.save.setImageResource(R.drawable.saved_icon_fill);
        } else {
            viewHolder.save.setImageResource(R.drawable.saved_icon_stroke);
        }

        onClickListeners(viewHolder, i);
//        int position = i;
//        viewHolder.itemView.post(new Runnable() {
//            @Override
//            public void run() {
//
//                int cellWidth = viewHolder.itemView.getWidth();// this will give you cell width dynamically
//                int cellHeight = viewHolder.itemView.getHeight();// this will give you cell height dynamically
//
//                dynamicHeight.HeightChange(position, cellHeight); //call your iterface hear
//            }
//        });

    }

    private void onClickListeners(final ProfileViewHolder viewHolder, final int i) {
        viewHolder.save.setOnClickListener(new View.OnClickListener() {
            boolean savedState = false;

            @Override
            public void onClick(View v) {
                if (savedState == false) {
                    viewHolder.save.setImageResource(R.drawable.saved_icon_fill);
                    save(informations.get(i).getPostId(), MainActivity.currentUserId);
                    savedState = true;
                } else {
                    viewHolder.save.setImageResource(R.drawable.saved_icon_stroke);
                    unsave(informations.get(i).getPostId(), MainActivity.currentUserId);
                    savedState = false;

                }
            }
        });
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            boolean likedState = false;

            @Override
            public void onClick(View v) {
                if (likedState == false) {
                    viewHolder.like.setImageResource(R.drawable.like_icon_fill);
                    like(informations.get(i).getPostId(), MainActivity.currentUserId);
                    likedState = true;
                } else {
                    viewHolder.like.setImageResource(R.drawable.like_icon_stroke);
                    dislike(informations.get(i).getPostId(), MainActivity.currentUserId);
                    likedState = false;

                }
            }
        });

        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.commentLayout.setVisibility(View.VISIBLE);
            }
        });

        viewHolder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEmpty = viewHolder.commentEditText.getText().toString().isEmpty() ? true : false;
                if (!isEmpty) {
                    // Query
                    //THe comment parent is entered null
                    MainActivity.db.execSQL("insert into comment values('" + new Random().nextLong() + "' , '" +
                            viewHolder.commentEditText.getText().toString() + "' , '" + informations.get(i).getPostId() + "', '" +
                            MainActivity.currentUserId + "', '');");
                    viewHolder.commentLayout.setVisibility(View.GONE);
                }

            }
        });
    }


//    public interface DynamicHeight {
//        void HeightChange(int position, int height);
//    }

    @Override
    public int getItemCount() {
        return informations.size();
    }

    private void unsave(String postId, String currentUserId) {
        MainActivity.db.execSQL("delete from save where post_id = '" + postId + "' and user_id = '" + currentUserId + "';");
    }

    private void save(String postId, String currentUserId) {
        MainActivity.db.execSQL("insert into save values('" + postId + "','" + currentUserId + "');");
    }

    private void like(String postId, String currentUserId) {
        MainActivity.db.execSQL("insert into likes values('" + postId + "','" + currentUserId + "');");
    }

    private void dislike(String postId, String currentUserId) {
        MainActivity.db.execSQL("delete from likes where post_id = '" + postId + "' and user_id = '" + currentUserId + "';");
    }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private ImageView image, save, comment, like;
        private TextView usernameProfile,
                usernameDescription, description,
                date, likes;
        private EditText commentEditText;
        private Button commentButton;
        private LinearLayout commentLayout;
        private TextView profileImageName;


        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            initials(itemView);
        }


        private void initials(View rootView) {
            profileImage = rootView.findViewById(R.id.profile_image);
            usernameProfile = rootView.findViewById(R.id.username_tv);
            image = rootView.findViewById(R.id.camera);
            usernameDescription = rootView.findViewById(R.id.description_username);
            description = rootView.findViewById(R.id.description);
            likes = rootView.findViewById(R.id.like_numbers_tv);
            date = rootView.findViewById(R.id.date);
            save = rootView.findViewById(R.id.saved);
            comment = rootView.findViewById(R.id.comment);
            like = rootView.findViewById(R.id.like);
            commentButton = rootView.findViewById(R.id.comment_button);
            commentEditText = rootView.findViewById(R.id.comment_edit_text);
            commentLayout = rootView.findViewById(R.id.comment_layout);
            profileImageName = rootView.findViewById(R.id.profile_image_name);

        }
    }
}
