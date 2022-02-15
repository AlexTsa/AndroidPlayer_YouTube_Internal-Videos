package com.alextsatsos.mediayoutubelyrics.MusixmatchModel;

import com.google.gson.annotations.SerializedName;

public class MessageLyrics {
    @SerializedName("header")
    private HeaderLyrics header;
    @SerializedName("body")
    private BodyLyrics body;

    public HeaderLyrics getHeader() {
        return header;
    }

    public BodyLyrics getBody() {
        return body;
    }
}
