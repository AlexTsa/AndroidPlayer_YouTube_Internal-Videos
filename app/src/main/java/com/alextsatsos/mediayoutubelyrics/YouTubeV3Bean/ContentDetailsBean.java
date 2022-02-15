package com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean;

import com.google.gson.annotations.SerializedName;

public class ContentDetailsBean {
    @SerializedName("videoId")
    private String videoId;
    @SerializedName("videoPublishedAt")
    private String videoPublishedAt;

    public String getVideoId() {
        return videoId;
    }

    public String getVideoPublishedAt() {
        return videoPublishedAt;
    }
}
