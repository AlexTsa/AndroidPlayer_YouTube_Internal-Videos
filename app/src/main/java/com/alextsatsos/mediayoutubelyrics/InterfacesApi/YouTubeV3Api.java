package com.alextsatsos.mediayoutubelyrics.InterfacesApi;

import com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean.YoutubeV3Details;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface YouTubeV3Api {

    @Headers({"Content-Type: application/json"})
    @GET("search")
    Call<YoutubeV3Details> youtubev3details(
            @Query("part") String part,
            @Query("maxResults") int maxResults,
            @Query("q") String q,
            @Query("type") String type,
            @Query("key") String key);
}
