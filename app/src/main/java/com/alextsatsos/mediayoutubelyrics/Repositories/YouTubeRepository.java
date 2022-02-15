package com.alextsatsos.mediayoutubelyrics.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alextsatsos.mediayoutubelyrics.InterfacesApi.YouTubeV3Api;
import com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean.YoutubeV3Details;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YouTubeRepository {
    private YouTubeV3Api youTubeV3Api;
    private MutableLiveData<YoutubeV3Details> youtubeV3LiveDetails;

    public YouTubeRepository() {

        youtubeV3LiveDetails = new MutableLiveData<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        youTubeV3Api = retrofit.create(YouTubeV3Api.class);

    }

    public void getYouTubeV3Details(String q) {
        Call<YoutubeV3Details> call = youTubeV3Api.youtubev3details("snippet", 50, q, "video", "AIzaSyDRRMgvJzr4gFF0F78I5MCZSSSadsj6k0U");
        call.enqueue(new Callback<YoutubeV3Details>() {
            @Override
            public void onResponse(Call<YoutubeV3Details> call, Response<YoutubeV3Details> response) {
                youtubeV3LiveDetails.postValue(response.body());
            }

            @Override
            public void onFailure(Call<YoutubeV3Details> call, Throwable t) {

            }
        });
    }

    public LiveData<YoutubeV3Details> getyoutubeV3LiveDetails() {
        return youtubeV3LiveDetails;
    }

    public String getIdForYoutubeVideo(int id) {
        return youtubeV3LiveDetails.getValue().getItems().get(id).getId().getVideoId();
    }

   public String getTitleByIdYouTube(int id) {
        return youtubeV3LiveDetails.getValue().getItems().get(id).getSnippet().getTitle();
    }
}
