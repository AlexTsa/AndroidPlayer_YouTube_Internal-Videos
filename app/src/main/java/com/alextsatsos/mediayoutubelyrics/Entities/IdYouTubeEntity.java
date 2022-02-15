package com.alextsatsos.mediayoutubelyrics.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "idyoutubeentity_table")
public class IdYouTubeEntity {

    @PrimaryKey
    @NonNull
    private String youTubeId;

    @NonNull
    public String getYouTubeId() {
        return youTubeId;
    }

    public void setYouTubeId(@NonNull String youTubeId) {
        this.youTubeId = youTubeId;
    }
}
