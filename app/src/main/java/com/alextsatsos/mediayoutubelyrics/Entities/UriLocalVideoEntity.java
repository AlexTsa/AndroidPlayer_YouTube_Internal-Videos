package com.alextsatsos.mediayoutubelyrics.Entities;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "urilocalvideoentity_table")
public class UriLocalVideoEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uriVideo")
    private String uriVideo;

    @NonNull
    public String getUriVideo() {
        return uriVideo;
    }

    public void setUriVideo(@NonNull String uriVideo) {
        this.uriVideo = uriVideo;
    }

    @ColumnInfo(name="localVideoLyrics")
    private String localVideoLyrics;



    public String getLocalVideoLyrics() {
        return localVideoLyrics;
    }

    public void setLocalVideoLyrics(String localVideoLyrics) {
        this.localVideoLyrics = localVideoLyrics;
    }



}
