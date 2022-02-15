package com.alextsatsos.mediayoutubelyrics.MyViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


import com.alextsatsos.mediayoutubelyrics.Entities.YouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.Repositories.YouTubeDatabaseRepository;


public class YouTubeEntityAddToFavouriteForYouTubeViewModel extends AndroidViewModel {
    private YouTubeDatabaseRepository youTubeDatabaseRepository;

    public YouTubeEntityAddToFavouriteForYouTubeViewModel(@NonNull Application application) {
        super(application);
        youTubeDatabaseRepository = new YouTubeDatabaseRepository(application);
    }
    public void insert(YouTubeEntity youTubeEntity) {
        youTubeDatabaseRepository.insert(youTubeEntity);
    }
}
