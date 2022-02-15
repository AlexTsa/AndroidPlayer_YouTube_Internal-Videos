package com.alextsatsos.mediayoutubelyrics.MusixmatchModel;

import com.google.gson.annotations.SerializedName;

public class BodyLyrics {
  @SerializedName("lyrics")
    private Lyrics lyrics;

  public Lyrics getLyrics() {
    return lyrics;
  }
}
