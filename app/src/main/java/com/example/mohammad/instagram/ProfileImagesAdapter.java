package com.example.mohammad.instagram;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mohammad Amin Soheyli on 04/01/2019.
 */
public class ProfileImagesAdapter extends RecyclerView.Adapter<ProfileImagesAdapter.ProfileViewHolder> {
    //    private DynamicHeight dynamicHeight;
    private ArrayList<ProfileCardInformations> informations;

    public ProfileImagesAdapter(ArrayList<ProfileCardInformations> informations) {
        this.informations = informations;
    }

//    public ProfileImagesAdapter(ArrayList<ProfileCardInformations> informations, DynamicHeight dynamicHeight, Context context) {
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
        viewHolder.profileImage.setImageResource(R.drawable.like_icon_fill);
        String username = informations.get(i).getUsername();
        viewHolder.usernameProfile.setText(username);
        viewHolder.image.setImageBitmap(informations.get(i).getImage());

        viewHolder.likes.setText(informations.get(i).getLikeNumber());
        viewHolder.usernameDescription.setText(username);
        viewHolder.description.setText(informations.get(i).getDescription());
        viewHolder.date.setText(informations.get(i).getDate());

        viewHolder.save.setOnClickListener(new View.OnClickListener() {
            boolean savedState = false;

            @Override
            public void onClick(View v) {
                if (savedState == false) {
                    viewHolder.save.setImageResource(R.drawable.saved_icon_fill);
                    savedState = true;
                } else {
                    viewHolder.save.setImageResource(R.drawable.saved_icon_stroke);
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
                    likedState = true;
                } else {
                    viewHolder.like.setImageResource(R.drawable.like_icon_stroke);
                    likedState = false;

                }
            }
        });

        final int x = i;
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), informations.get(x).getDate(), Toast.LENGTH_SHORT).show();
            }
        });
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

//    public interface DynamicHeight {
//        void HeightChange(int position, int height);
//    }

    @Override
    public int getItemCount() {
        return informations.size();
    }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private ImageView image, save, comment, like;
        private TextView usernameProfile,
                usernameDescription, description,
                date, likes;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);

            initials(itemView);
        }


        private void initials(View rootView) {
            profileImage = rootView.findViewById(R.id.profile_image);
            usernameProfile = rootView.findViewById(R.id.username_tv);
            image = rootView.findViewById(R.id.image);
            usernameDescription = rootView.findViewById(R.id.description_username);
            description = rootView.findViewById(R.id.description);
            likes = rootView.findViewById(R.id.like_numbers_tv);
            date = rootView.findViewById(R.id.date);
            save = rootView.findViewById(R.id.saved);
            comment = rootView.findViewById(R.id.comment);
            like = rootView.findViewById(R.id.like);

        }
    }
}
