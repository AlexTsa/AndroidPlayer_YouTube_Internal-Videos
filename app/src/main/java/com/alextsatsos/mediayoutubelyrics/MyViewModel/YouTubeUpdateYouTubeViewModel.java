package com.alextsatsos.mediayoutubelyrics.MyViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.alextsatsos.mediayoutubelyrics.Entities.YouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.Repositories.YouTubeDatabaseUpdateFavouriteRepository;

public class YouTubeUpdateYouTubeViewModel extends AndroidViewModel {
private YouTubeDatabaseUpdateFavouriteRepository youTubeDatabaseUpdateFavouriteRepository;

    public YouTubeUpdateYouTubeViewModel(@NonNull Application application) {
        super(application);
        youTubeDatabaseUpdateFavouriteRepository = new YouTubeDatabaseUpdateFavouriteRepository(application);
    }

    public void updateYoutubeEntity(YouTubeEntity youTubeEntity){
        youTubeDatabaseUpdateFavouriteRepository.updateYoutubeEntity(youTubeEntity);
    }
}
