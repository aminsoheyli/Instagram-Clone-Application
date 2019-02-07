package com.example.mohammad.instagram.recycler_view.story;

public class StoryCard {
    private String storyUserName;
    // TODO: This bellow String url is for now, in the later maybe it can be an Image or Bitmap
    private String storyImageUrl;

    public StoryCard(String storyUserName, String storyImageUrl) {
        this.storyUserName = storyUserName;
        this.storyImageUrl = storyImageUrl;
    }

    public String getStoryUserName() {
        return storyUserName;
    }

    public String getStoryImageUrl() {
        return storyImageUrl;
    }
}
