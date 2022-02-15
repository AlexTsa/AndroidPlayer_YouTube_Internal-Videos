package com.alextsatsos.mediayoutubelyrics.MusixmatchModel;

import com.google.gson.annotations.SerializedName;

public class Lyrics {
    @SerializedName("lyrics_id")
    private  int lyrics_id;
    @SerializedName("explicit")
    private int explicit;
    @SerializedName("lyrics_body")
    private String lyrics_body;
    @SerializedName("script_tracking_url")
    private  String script_tracking_url;
    @SerializedName("pixel_tracking_url")
    private String pixel_tracking_url;
    @SerializedName("lyrics_copyright")
    private  String lyrics_copyright;
     @SerializedName("updated_time")
    private String updated_time;

    public int getLyrics_id() {
        return lyrics_id;
    }

    public int getExplicit() {
        return explicit;
    }

    public String getLyrics_body() {
        return lyrics_body;
    }

    public String getScript_tracking_url() {
        return script_tracking_url;
    }

    public String getPixel_tracking_url() {
        return pixel_tracking_url;
    }

    public String getLyrics_copyright() {
        return lyrics_copyright;
    }

    public String getUpdated_time() {
        return updated_time;
    }
}
