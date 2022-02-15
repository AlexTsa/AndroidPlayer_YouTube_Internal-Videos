package com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean;

import com.google.gson.annotations.SerializedName;

public class HighBean {
    @SerializedName("url")
    private String url;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
