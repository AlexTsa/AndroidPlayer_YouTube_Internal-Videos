package com.alextsatsos.mediayoutubelyrics.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "loopentity_table",indices = {
       @Index(value="youTuBeEntityid",unique = false)},
        foreignKeys =
        @ForeignKey(entity = IdYouTubeEntity.class,
                parentColumns = "youTubeId",
                childColumns = "youTuBeEntityid",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE))
public class LoopEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long loopid;

    @NonNull
    private String youTuBeEntityid;

    @NonNull
    private long loopStart;

    @NonNull
    private long loopEnd;

    public long getLoopid() {
        return loopid;
    }


    public void setLoopid(long loopid) {
        this.loopid = loopid;
    }

    public String getYouTuBeEntityid() {
        return youTuBeEntityid;
    }

    public void setYouTuBeEntityid(String youTuBeEntityid) {
        this.youTuBeEntityid = youTuBeEntityid;
    }

    public long getLoopStart() {
        return loopStart;
    }

    public void setLoopStart(long loopStart) {
        this.loopStart = loopStart;
    }

    public long getLoopEnd() {
        return loopEnd;
    }

    public void setLoopEnd(long loopEnd) {
        this.loopEnd = loopEnd;
    }


}
