package com.alextsatsos.mediayoutubelyrics.Entities;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "loopforlocalvideoentity_table",
        foreignKeys =
        @ForeignKey(entity = UriLocalVideoEntity.class,
                parentColumns = "uriVideo",
                childColumns = "uriVideoloop",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE))


public class LoopForLocalVideoEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "idlooplocalvideo")
    private int idlooplocalvideo;

    @NonNull
    private String uriVideoloop;

    @NonNull
    public String getUriVideoloop() {
        return uriVideoloop;
    }

    public void setUriVideoloop(@NonNull String uriVideoloop) {
        this.uriVideoloop = uriVideoloop;
    }

    @NonNull
    @ColumnInfo(name = "loopLocalVideoStart")
    private long loopLocalVideoStart;

    @NonNull
    @ColumnInfo(name = "loopLocalVideoEnd")
    private long loopLocalVideoEnd;

    public int getIdlooplocalvideo() {
        return idlooplocalvideo;
    }

    public void setIdlooplocalvideo(int idlooplocalvideo) {
        this.idlooplocalvideo = idlooplocalvideo;
    }

    public long getLoopLocalVideoStart() {
        return loopLocalVideoStart;
    }

    public void setLoopLocalVideoStart(long loopLocalVideoStart) {
        this.loopLocalVideoStart = loopLocalVideoStart;
    }

    public long getLoopLocalVideoEnd() {
        return loopLocalVideoEnd;
    }

    public void setLoopLocalVideoEnd(long loopLocalVideoEnd) {
        this.loopLocalVideoEnd = loopLocalVideoEnd;
    }
}
