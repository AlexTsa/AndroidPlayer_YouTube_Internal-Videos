package com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class YoutubeV3Details {
    @SerializedName("kind")
    private String kind;
    @SerializedName("etag")
    private String etag;
    @SerializedName("nextPageToken")
    private String nextPageToken;
    @SerializedName("pageInfo")
    private PageInfoBean pageInfo;
    @SerializedName("items")
    private List<ItemsBean> items;

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public List<ItemsBean> getItems() {
        return items;
    }
}
