package com.alextsatsos.mediayoutubelyrics.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alextsatsos.mediayoutubelyrics.InterfacesApi.MusixmatchApi;
import com.alextsatsos.mediayoutubelyrics.MusixmatchModel.Musixmatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusixmatchResository {
    private MusixmatchApi musixmatchApi;
    private MutableLiveData<Musixmatch> musixmatchLiveData;

    public MusixmatchResository(){
        musixmatchLiveData = new MutableLiveData<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.musixmatch.com/ws/1.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        musixmatchApi = retrofit.create(MusixmatchApi.class);
    }
    public void getTheLyrics(String title){
        Call<Musixmatch> call = musixmatchApi.getTheLyrics("json","callback",title,"","73d2a5f27d171a616f3b83ef5c60bf6e");
        call.enqueue(new Callback<Musixmatch>() {
            @Override
            public void onResponse(Call<Musixmatch> call, Response<Musixmatch> response) {
                musixmatchLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Musixmatch> call, Throwable t) {

            }
        });
    }
    public LiveData<Musixmatch> getTheLyricsLiveData(){
        return musixmatchLiveData;
    }

}
