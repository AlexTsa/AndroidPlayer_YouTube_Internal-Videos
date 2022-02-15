package com.alextsatsos.mediayoutubelyrics.MyViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alextsatsos.mediayoutubelyrics.Entities.YouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.Repositories.YouTubeDatabaseRepository;

import java.util.List;

public class YouTubeEntityViewModel extends AndroidViewModel {
    private YouTubeDatabaseRepository youTubeDatabaseRepository;
    private LiveData<List<YouTubeEntity>> allFromYouTubeEntity;
    private LiveData<List<YouTubeEntity>> allFromYouTubeEntityORDERBYtitleDESC;
    private LiveData<List<YouTubeEntity>> allFromYouTubeEntityORDERBYtitleASC;

    public YouTubeEntityViewModel(@NonNull Application application) {
        super(application);
        youTubeDatabaseRepository = new YouTubeDatabaseRepository(application);
        allFromYouTubeEntity = youTubeDatabaseRepository.getAllFromYouTubeEntity();
        allFromYouTubeEntityORDERBYtitleDESC =youTubeDatabaseRepository.getAllFromYouTubeEntityORDERBYtitleDESC();
        allFromYouTubeEntityORDERBYtitleASC =youTubeDatabaseRepository.getAllFromYouTubeEntityORDERBYtitleASC();

    }
    //thn insert na mhn ksexasw na thn sbhsw den thn xrisimopoiw
    public void insert(YouTubeEntity youTubeEntity) {
        youTubeDatabaseRepository.insert(youTubeEntity);
    }
    public void deleteYoutubeEntity(YouTubeEntity youTubeEntity){
        youTubeDatabaseRepository.deleteYoutubeEntity(youTubeEntity);
    }

    public LiveData<List<YouTubeEntity>> getAllFromYouTubeEntity() {
        return allFromYouTubeEntity;
    }

    public LiveData<List<YouTubeEntity>> getAllFromYouTubeEntityORDERBYtitleDESC() {
        return allFromYouTubeEntityORDERBYtitleDESC;
    }

    public LiveData<List<YouTubeEntity>> getAllFromYouTubeEntityORDERBYtitleASC() {
        return allFromYouTubeEntityORDERBYtitleASC;
    }
}
