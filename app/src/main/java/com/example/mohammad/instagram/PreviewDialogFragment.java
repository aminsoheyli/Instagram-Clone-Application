package com.example.mohammad.instagram;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PreviewDialogFragment extends DialogFragment {
    private View rootView;
    private ImageView userProfileImage, imageToPreview;
    private TextView userName;
    private ImageView like, comment, share;
    private PreviewDetail previewDetail;
    private static final String DETAIL_KEY = "detail";

    public static PreviewDialogFragment newInstance(PreviewDetail previewDetail) {
        Bundle args = new Bundle();
        args.putSerializable(DETAIL_KEY, previewDetail);
        PreviewDialogFragment fragment = new PreviewDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        previewDetail = (PreviewDetail) getArguments().getSerializable(DETAIL_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_preview_dialog, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        userProfileImage = rootView.findViewById(R.id.user_profile);
        imageToPreview = rootView.findViewById(R.id.image);
        userName = rootView.findViewById(R.id.user_name);
        like = rootView.findViewById(R.id.like);
        comment = rootView.findViewById(R.id.comment);
        share = rootView.findViewById(R.id.share);

        if (previewDetail.isLiked())
            like.setImageResource(R.drawable.like_icon_fill);
        else
            like.setImageResource(R.drawable.like_icon_stroke);

        userProfileImage.setImageBitmap(previewDetail.getUserProfile());
        imageToPreview.setImageBitmap(previewDetail.getImage());
    }

    @SuppressLint("ValidFragment")
    private PreviewDialogFragment() {
    }
}
