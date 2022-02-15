package com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean;

import com.google.gson.annotations.SerializedName;

public class SnippetBean {

    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("channelId")
    private String channelId;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("thumbnails")
    private ThumbnailsBean thumbnails;
    @SerializedName("channelTitle")
    private String channelTitle;
    @SerializedName("playlistId")
    private String playlistId;
    @SerializedName("position")
    private int position;
    @SerializedName("resourceId")
    private ResourceIdBean resourceId;

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ThumbnailsBean getThumbnails() {
        return thumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public int getPosition() {
        return position;
    }

    public ResourceIdBean getResourceId() {
        return resourceId;
    }
}
