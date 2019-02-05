package com.example.mohammad.instagram.recycler_view.comment;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mohammad.instagram.R;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mohammad Amin Soheyli on 22/01/2019.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<CommentCard> informations;
    private View rootView;
    private Faker faker;

    public CommentAdapter(ArrayList<CommentCard> commentInformations) {
        this.informations = commentInformations;
        this.faker = new Faker(new Locale("fa"));
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_comment, viewGroup, false);
        return new CommentViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder vh, int i) {
        // ToDo: Clear the use of Faker class, and use the commented informations
//        vh.commentText.setText(faker.name().lastName() + " : " + informations.get(i).getCommentText());
        vh.commentText.setText(faker.name().lastName() + " : " + faker.color().name());
        Glide.with(rootView.getContext())
                .load("https://static.independent.co.uk/s3fs-public/thumbnails/image/2017/09/12/11/naturo-monkey-selfie.jpg?w968h681")
                .apply(RequestOptions.centerCropTransform().circleCrop()).into(vh.profileImage);
    }

    @Override
    public int getItemCount() {
        return informations.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView commentText;
        private ImageView profileImage;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.comment_text_view);
            profileImage = itemView.findViewById(R.id.profile_image);
        }
    }
}
