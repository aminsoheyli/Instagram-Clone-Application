package com.example.mohammad.instagram.recycler_view.profile;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mohammad.instagram.R;
import com.example.mohammad.instagram.activity.ClickedUserActivity;
import com.example.mohammad.instagram.activity.MainActivity;
import com.example.mohammad.instagram.fragment.CommentDialogFragment;
import com.example.mohammad.instagram.fragment.FollowersFollowingFragment;
import com.example.mohammad.instagram.recycler_view.comment.CommentCard;
import com.example.mohammad.instagram.temp.TestDataGenerator;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


/**
 * Created by Mohammad Amin Soheyli on 04/01/2019.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ProfileViewHolder> {
    public static final String CLICKED_USER_ID_KEY = "key of clicked user id";
    private int i;
    //    private DynamicHeight dynamicHeight;
    private ArrayList<PostCard> informations;
    private View rootView;
    private Random random;
    private ArrayList<String> stringUriOfImages;
    private Faker faker;

    public PostAdapter(ArrayList<PostCard> informations) {
        this.informations = informations;
        this.random = new Random();
        this.stringUriOfImages = TestDataGenerator.generateSomeStringImageUri();
        this.faker = new Faker(new Locale("fa"));
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_post, viewGroup, false);
        return new ProfileViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileViewHolder viewHolder, final int i) {

//        String username = informations.get(i).getUsername();
//        viewHolder.usernameProfile.setText(username);
        // ToDO: set the profile image of logged in user or clicked user.
        /**
         * Use of Faker
         */
        String username = faker.name().fullName();
        viewHolder.usernameProfile.setText(username);


        // ToDo: Change the commented bellow code later
        /**
         * (Maybe with Glide, although in this case it's better to use the ImageView directly.
         * viewHolder.image.setImageBitmap(informations.get(i).getImage());*/
        int randomPick = random.nextInt(stringUriOfImages.size());
        Glide.with(rootView.getContext())
                .load(faker.internet().image()).into(viewHolder.image);

        final Animation firstAnimation = AnimationUtils.loadAnimation(rootView.getContext(), R.anim.double_click_like_first);
        final Animation secondAnimation = AnimationUtils.loadAnimation(rootView.getContext(), R.anim.double_click_like_first);
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            long currentClickTime = 0;
            long previousClickTime = 0;

            @Override
            public void onClick(View v) {
                currentClickTime = System.currentTimeMillis();
                long duration = currentClickTime - previousClickTime;
                // Animation start if the time between two click is less than or equal to 600 milli second
                if (duration <= 600 && duration != 0) {
                    viewHolder.like.setImageResource(R.drawable.like_icon_fill);
                    informations.get(i).setLiked(true);
                    viewHolder.centerLike.setVisibility(View.VISIBLE);
                    viewHolder.centerLike.startAnimation(firstAnimation);
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            viewHolder.centerLike.startAnimation(secondAnimation);
                            viewHolder.centerLike.setVisibility(View.GONE);
                        }
                    }.start();
                }
                previousClickTime = currentClickTime;
            }
        });
        /**
         * Use of Glide
         */
        Glide.with(rootView.getContext())
                .load(faker.internet().image())
                .apply(RequestOptions.centerCropTransform().circleCrop())
                .into(viewHolder.profileImage);
        // ToDO: set the profile image of logged in user or clicked user.


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

        viewHolder.userContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clickedUserId = informations.get(i).getUsername();
                if (!MainActivity.currentUserId.equals(clickedUserId)
                        && MainActivity.currentTabState != MainActivity.PROFILE_TAB_ID) {
                    Intent intent = new Intent(rootView.getContext(), ClickedUserActivity.class);
                    intent.putExtra(CLICKED_USER_ID_KEY, clickedUserId);
                    MainActivity.self.startActivity(intent);
                }
            }
        });


        viewHolder.save.setOnClickListener(new View.OnClickListener() {
            boolean savedState = informations.get(i).isSaved();

            @Override
            public void onClick(View v) {
                if (!savedState) {
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
            boolean likedState = informations.get(i).isLiked();

            @Override
            public void onClick(View v) {
//                int numbers = Integer.valueOf(informations.get(index).getLikeNumber());
//                int newValue;
                if (!informations.get(i).isLiked()) {
//                    newValue = numbers + 1;
                    viewHolder.like.setImageResource(R.drawable.like_icon_fill);
                    like(informations.get(i).getPostId(), MainActivity.currentUserId);
                    informations.get(i).setLiked(true);
                } else {
//                    newValue = numbers - 1;
                    viewHolder.like.setImageResource(R.drawable.like_icon_stroke);
                    dislike(informations.get(i).getPostId(), MainActivity.currentUserId);
                    informations.get(i).setLiked(false);
                }
//                viewHolder.likes.setText(String.valueOf(newValue));
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
//                    String commentId = String.valueOf(new Random().nextLong());
                    String commentId = getSaltString();
                    Toast.makeText(rootView.getContext(), comment, Toast.LENGTH_SHORT).show();
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
                String postId = PostAdapter.this.informations.get(i).getPostId();
                Toast.makeText(rootView.getContext(), postId, Toast.LENGTH_SHORT).show();
                ArrayList<CommentCard> commentInformations = getComments(postId);
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
        viewHolder.image.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                ArrayList<String> likers = getLikersOfThisPost(informations.get(i).getPostId());
                FollowersFollowingFragment fragment = FollowersFollowingFragment.newInstance(likers);
                fragment.show(MainActivity.fm, "likers");
                return false;
            }
        });

    }

    private ArrayList<String> getLikersOfThisPost(String postId) {
        ArrayList<String> likers = TestDataGenerator.generateSomeName();
        return likers;
    }


//    public interface DynamicHeight {
//        void HeightChange(int position, int height);
//    }


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


    @Override
    public int getItemCount() {
        // ToDo: comment out bellow code
//        return informations.size();
        return TestDataGenerator.getItemCount();
    }

    private void unsave(String postId, String currentUserId) {
    }

    private void save(String postId, String currentUserId) {
    }

    private void like(String postId, String currentUserId) {
    }

    private void dislike(String postId, String currentUserId) {
    }

    private ArrayList<CommentCard> getComments(String postId) {
        ArrayList<CommentCard> commentsData = TestDataGenerator.generateSomeComments();
        return commentsData;
    }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private ImageView image, save, comment, like, centerLike;
        private TextView usernameProfile,
                usernameDescription, description,
                date, likes, viewCommentsTV;
        private EditText commentEditText;
        private Button commentButton;
        private LinearLayout commentLayout;
        private View userContainer;


        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            initials(itemView);
        }


        private void initials(View rootView) {
            profileImage = rootView.findViewById(R.id.profile_image);
            usernameProfile = rootView.findViewById(R.id.username_tv);
            image = rootView.findViewById(R.id.image_of_post);
            usernameDescription = rootView.findViewById(R.id.description_username);
            description = rootView.findViewById(R.id.description);
            likes = rootView.findViewById(R.id.like_numbers_tv);
            date = rootView.findViewById(R.id.date);
            save = rootView.findViewById(R.id.saved);
            comment = rootView.findViewById(R.id.comment);
            like = rootView.findViewById(R.id.like);
            centerLike = rootView.findViewById(R.id.double_click_center_like);
            commentButton = rootView.findViewById(R.id.comment_button);
            commentEditText = rootView.findViewById(R.id.comment_edit_text);
            commentLayout = rootView.findViewById(R.id.comment_layout);
            viewCommentsTV = rootView.findViewById(R.id.viewComments_tv);
            userContainer = rootView.findViewById(R.id.user_container);

        }
    }
}
