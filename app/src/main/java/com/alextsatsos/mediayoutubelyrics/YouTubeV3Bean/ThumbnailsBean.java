package com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean;

import com.google.gson.annotations.SerializedName;

public class ThumbnailsBean {
    @SerializedName("default")
    private DefaultBean defaultX;
    @SerializedName("medium")
    private MediumBean medium;
    @SerializedName("high")
    private HighBean high;
    @SerializedName("standard")
    private StandardBean standard;
    @SerializedName("maxres")
    private MaxresBean maxres;

    public DefaultBean getDefaultX() {
        return defaultX;
    }

    public MediumBean getMedium() {
        return medium;
    }

    public HighBean getHigh() {
        return high;
    }

    public StandardBean getStandard() {
        return standard;
    }

    public MaxresBean getMaxres() {
        return maxres;
    }
}
