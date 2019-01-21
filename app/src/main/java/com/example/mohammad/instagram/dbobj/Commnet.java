package com.example.mohammad.instagram.dbobj;

/**
 * Created by Mohammad Amin Soheyli on 20/01/2019.
 */
public class Commnet {
    private String commentId;
    private String commentText;

    public Commnet(String commentId, String commentText) {
        this.commentId = commentId;
        this.commentText = commentText;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
