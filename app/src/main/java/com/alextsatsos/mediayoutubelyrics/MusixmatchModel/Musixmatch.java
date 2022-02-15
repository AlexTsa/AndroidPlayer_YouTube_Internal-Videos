package com.alextsatsos.mediayoutubelyrics.MusixmatchModel;

import com.google.gson.annotations.SerializedName;

public class Musixmatch {
    @SerializedName("message")
    private  MessageLyrics message;

    public MessageLyrics getMessage() {
        return message;
    }
}
