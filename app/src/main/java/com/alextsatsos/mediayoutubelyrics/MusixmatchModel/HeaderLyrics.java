package com.alextsatsos.mediayoutubelyrics.MusixmatchModel;

import com.google.gson.annotations.SerializedName;

public class HeaderLyrics {
    @SerializedName("status_code")
    private int status_code;
    @SerializedName("execute_time")
    private double execute_time;

    public int getStatus_code() {
        return status_code;
    }

    public double getExecute_time() {
        return execute_time;
    }
}
