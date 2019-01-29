package com.example.mohammad.instagram.recycler_view.comment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohammad.instagram.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohammad Amin Soheyli on 22/01/2019.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<CommentCard> informations;
    private View rootView;

    public CommentAdapter(ArrayList<CommentCard> commentInformations) {
        this.informations = commentInformations;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_comment, viewGroup, false);
        return new CommentViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder vh, int i) {
        vh.profileName.setText(String.valueOf(informations.get(i).getProfileName().charAt(0)));
        vh.commentText.setText(informations.get(i).getProfileName().toString()+ " : " + informations.get(i).getCommentText());
    }

    @Override
    public int getItemCount() {
        return informations.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView profileName, commentText;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            profileName = itemView.findViewById(R.id.profile_image_name);
            commentText = itemView.findViewById(R.id.comment_text_view);
        }
    }
}
