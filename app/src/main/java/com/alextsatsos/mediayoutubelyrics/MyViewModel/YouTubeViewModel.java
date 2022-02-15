package com.alextsatsos.mediayoutubelyrics.MyViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alextsatsos.mediayoutubelyrics.InterfacesApi.YouTubeV3Api;
import com.alextsatsos.mediayoutubelyrics.Repositories.YouTubeRepository;
import com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean.YoutubeV3Details;


public class YouTubeViewModel  extends AndroidViewModel {
    private LiveData<YoutubeV3Details> youtubeV3LiveDetails;
    private YouTubeRepository youTubeRepository;

    public YouTubeViewModel(@NonNull Application application) {
        super(application);
    }
    public void init() {
            youTubeRepository = new YouTubeRepository();
        youtubeV3LiveDetails = youTubeRepository.getyoutubeV3LiveDetails();
    }
    public void getYouTubeV3Details(String q){
        youTubeRepository.getYouTubeV3Details(q);
    }
    public LiveData<YoutubeV3Details> getyoutubeV3LiveDetails() {
        return youtubeV3LiveDetails;}

    public String getIdForYoutubeVideo(int id){
        return youTubeRepository.getIdForYoutubeVideo(id);
    }
    public String getTitleByIdYouTube(int id){
       return  youTubeRepository.getTitleByIdYouTube(id);
    }

}
