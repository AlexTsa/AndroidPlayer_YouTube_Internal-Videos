package com.alextsatsos.mediayoutubelyrics.InterfacesApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SuggestYouTubeApi {
    @Headers({"Content-Type: application/json"})
    @GET("complete/search")
    Call<ResponseBody> getAutoComplete(
            @Query("client") String client,
            @Query("ds")String ds,
            @Query("client")String client2,
            @Query("q") String query);
}
