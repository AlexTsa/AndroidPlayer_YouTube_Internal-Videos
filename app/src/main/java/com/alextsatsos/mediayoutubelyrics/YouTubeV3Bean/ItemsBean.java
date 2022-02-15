package com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean;

import com.google.gson.annotations.SerializedName;

public class ItemsBean {
    @SerializedName("kind")
    private String kind;
    @SerializedName("etag")
    private String etag;
    @SerializedName("id")
    private IdBean id;
    @SerializedName("snippet")
    private SnippetBean snippet;
    @SerializedName("contentDetails")
    private ContentDetailsBean contentDetails;

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public IdBean getId() {
        return id;
    }

    public SnippetBean getSnippet() {
        return snippet;
    }

    public ContentDetailsBean getContentDetails() {
        return contentDetails;
    }
}
