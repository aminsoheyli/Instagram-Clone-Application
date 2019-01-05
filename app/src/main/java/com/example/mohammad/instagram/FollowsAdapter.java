package com.example.mohammad.instagram;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mohammad Amin Soheyli on 04/01/2019.
 */
public class FollowsAdapter extends RecyclerView.Adapter<FollowsAdapter.FollowsViewHolder> {
    private List<FollowsInformation> informations;

    public FollowsAdapter(List<FollowsInformation> informations) {
        this.informations = informations;
    }

    @NonNull
    @Override
    public FollowsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_follows, viewGroup, false);
        return new FollowsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowsViewHolder followsViewHolder, int i) {
        followsViewHolder.name.setText(informations.get(i).getName());
        followsViewHolder.image.setImageResource(informations.get(i).getImageId());
    }

    @Override
    public int getItemCount() {
        return informations.size();
    }

    public static class FollowsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private CircleImageView image;

        public FollowsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.imageId);
            onClickListeners();
        }

        private void onClickListeners() {
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}