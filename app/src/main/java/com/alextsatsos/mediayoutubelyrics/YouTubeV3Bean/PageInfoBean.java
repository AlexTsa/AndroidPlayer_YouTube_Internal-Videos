package com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean;

import com.google.gson.annotations.SerializedName;

public class PageInfoBean {

    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("resultsPerPage")
    private int resultsPerPage;

    public int getTotalResults() {
        return totalResults;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }
}
