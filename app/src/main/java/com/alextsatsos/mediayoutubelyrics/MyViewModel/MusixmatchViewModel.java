package com.alextsatsos.mediayoutubelyrics.MyViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alextsatsos.mediayoutubelyrics.MusixmatchModel.Musixmatch;
import com.alextsatsos.mediayoutubelyrics.Repositories.MusixmatchResository;

public class MusixmatchViewModel extends AndroidViewModel {
    private MusixmatchResository musixmatchResository;
    private LiveData<Musixmatch> musixmatchLiveData;

    public MusixmatchViewModel(@NonNull Application application) {
        super(application);
    }
    public  void init(){
        musixmatchResository = new MusixmatchResository();
        musixmatchLiveData = musixmatchResository.getTheLyricsLiveData();
    }
    public void getTheLyrics(String title){
        musixmatchResository.getTheLyrics(title);
    }
    public LiveData<Musixmatch>getTheLyricsLiveData(){
        return musixmatchLiveData;
    }
}
