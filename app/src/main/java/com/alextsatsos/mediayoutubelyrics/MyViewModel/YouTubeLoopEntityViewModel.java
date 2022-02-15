package com.alextsatsos.mediayoutubelyrics.MyViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alextsatsos.mediayoutubelyrics.Entities.LoopEntity;
import com.alextsatsos.mediayoutubelyrics.Repositories.YoutubeDatabaseLoopRepository;

import java.util.List;

public class YouTubeLoopEntityViewModel extends AndroidViewModel {
  //  private LiveData<List<LoopEntity>> allFromLoopEntity;
    private YoutubeDatabaseLoopRepository youtubeDatabaseLoopRepository;
    private LiveData<List<LoopEntity>> specificLoopFromLoopEntity;


    public YouTubeLoopEntityViewModel(@NonNull Application application) {
        super(application);
        youtubeDatabaseLoopRepository = new YoutubeDatabaseLoopRepository(application);
       // allFromLoopEntity = youtubeDatabaseLoopRepository.getAllFromLoopEntity();

    }

    public void initSpecificLoopFromLoopEntityViewModel(String idYouTube) {
        youtubeDatabaseLoopRepository.initSpecificLoopFromLoopEntity(idYouTube);
        specificLoopFromLoopEntity = youtubeDatabaseLoopRepository.getSpecificLoopFromLoopEntity();

    }



    public void insertLoops(LoopEntity loopEntity) {
        youtubeDatabaseLoopRepository.insertLoops(loopEntity);
    }


    public void deleteLoop(LoopEntity loopEntity) {
        youtubeDatabaseLoopRepository.deleteLoop(loopEntity);
    }

   // public LiveData<List<LoopEntity>> getAllFromLoopEntity() {
    //    return allFromLoopEntity;
   // }

    public LiveData<List<LoopEntity>> getSpecificLoopFromLoopEntity() {
        return specificLoopFromLoopEntity;
    }


    public Long getLoopStartForSeek(int position) {
        return specificLoopFromLoopEntity.getValue().get(position).getLoopStart();
    }

    public Long getLoopEndForSeek(int position) {
        return specificLoopFromLoopEntity.getValue().get(position).getLoopEnd();
    }

}
