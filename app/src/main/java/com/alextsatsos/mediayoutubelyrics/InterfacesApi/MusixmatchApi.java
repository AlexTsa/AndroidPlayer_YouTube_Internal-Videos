package com.alextsatsos.mediayoutubelyrics.InterfacesApi;

import com.alextsatsos.mediayoutubelyrics.MusixmatchModel.Musixmatch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MusixmatchApi {
    @GET("matcher.lyrics.get")
    Call<Musixmatch> getTheLyrics(
            @Query("format") String format,
            @Query("callback") String callback,
            @Query("q_track") String q_track,
            @Query("q_artist") String q_artist,
            @Query("apikey") String apikey);
}
