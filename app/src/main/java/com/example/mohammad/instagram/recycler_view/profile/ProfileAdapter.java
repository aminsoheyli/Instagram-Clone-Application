package com.example.mohammad.instagram.recycler_view.profile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.activity.ClickedUserActivity;
import com.example.mohammad.instagram.activity.MainActivity;
import com.example.mohammad.instagram.fragment.CommentDialogFragment;
import com.example.mohammad.instagram.recycler_view.comment.CommentCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mohammad Amin Soheyli on 04/01/2019.
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    //    private DynamicHeight dynamicHeight;
    private ArrayList<ProfileCard> informations;
    private static int index;

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
                .inflate(R.layout.card_profile, viewGroup, false);
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

    private void onClickListeners(final ProfileViewHolder viewHolder,int i) {
        index = i;
        viewHolder.userContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.currentUserId.equals(informations.get(index).getUsername())
                        && MainActivity.currentTabState != MainActivity.DEFAULT_TAB_ID) {
                    Intent intent = new Intent(viewHolder.comment.getContext(), ClickedUserActivity.class);
                    MainActivity.self.startActivity(intent);
                }
            }
        });
        viewHolder.save.setOnClickListener(new View.OnClickListener() {
            boolean savedState = false;

            @Override
            public void onClick(View v) {
                if (!savedState) {
                    viewHolder.save.setImageResource(R.drawable.saved_icon_fill);
                    save(informations.get(index).getPostId(), MainActivity.currentUserId);
                    savedState = true;
                } else {
                    viewHolder.save.setImageResource(R.drawable.saved_icon_stroke);
                    unsave(informations.get(index).getPostId(), MainActivity.currentUserId);
                    savedState = false;

                }
            }
        });
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            boolean likedState = false;

            @Override
            public void onClick(View v) {
                if (!likedState) {
                    viewHolder.like.setImageResource(R.drawable.like_icon_fill);
                    like(informations.get(index).getPostId(), MainActivity.currentUserId);
                    likedState = true;
                } else {
                    viewHolder.like.setImageResource(R.drawable.like_icon_stroke);
                    dislike(informations.get(index).getPostId(), MainActivity.currentUserId);
                    likedState = false;

                }
            }
        });
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.commentLayout.setVisibility(View.VISIBLE);
                viewHolder.commentEditText.requestFocus();
                InputMethodManager imm = (InputMethodManager) MainActivity.self.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(viewHolder.commentEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        viewHolder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = viewHolder.commentEditText.getText().toString();
                if (!comment.isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) MainActivity.self.getSystemService(Context.INPUT_METHOD_SERVICE);
                    //The comment parent is entered null
                    MainActivity.db.execSQL(
                            "insert into comment values('" + new Random().nextLong() + "' , '" +
                                    comment + "' , '" +
                                    informations.get(index).getPostId() + "', '" +
                                    MainActivity.currentUserId + "', '');");
                    viewHolder.commentEditText.getText().clear();
                    viewHolder.commentLayout.setVisibility(View.GONE);
                    imm.hideSoftInputFromWindow(viewHolder.commentEditText.getWindowToken(), 0);
                } else {
                    viewHolder.commentEditText.requestFocus();
                }

            }
        });

        viewHolder.viewCommentsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postId = ProfileAdapter.this.informations.get(index).getPostId();
                ArrayList<CommentCard> commentInformations = (ArrayList<CommentCard>) getComments(postId);
                if (commentInformations == null) {
                    Toast toast = Toast.makeText(MainActivity.self, "No comment found!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                CommentDialogFragment fragment = CommentDialogFragment.newInstance(commentInformations);
                fragment.show(MainActivity.fm, "Follows fragment");
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

    private List<CommentCard> getComments(String postId) {
        Cursor c = MainActivity.db.rawQuery("select * from comment where post_id ='" + postId + "';", null);
        if (c.getColumnCount() != 0) {
            List commentsData = new ArrayList<CommentCard>();
            c.moveToFirst();
            if (c.getCount() == 0)
                return null;
            do {
                commentsData.add(new CommentCard(c.getString(3), c.getString(1)));
            } while (c.moveToNext());
            return commentsData;
        } else {
            return null;
        }
    }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private ImageView image, save, comment, like;
        private TextView usernameProfile,
                usernameDescription, description,
                date, likes, viewCommentsTV;
        private EditText commentEditText;
        private Button commentButton;
        private LinearLayout commentLayout;
        private TextView profileImageName;
        private View userContainer;


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
            viewCommentsTV = rootView.findViewById(R.id.viewComments_tv);
            userContainer = rootView.findViewById(R.id.user_container);

        }
    }
}
