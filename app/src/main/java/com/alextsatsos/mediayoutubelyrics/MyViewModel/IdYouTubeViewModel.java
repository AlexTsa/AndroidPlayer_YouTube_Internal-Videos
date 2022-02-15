package com.alextsatsos.mediayoutubelyrics.MyViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.alextsatsos.mediayoutubelyrics.Entities.IdYouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.Repositories.IdYouTubeRepository;

import java.util.List;


public class IdYouTubeViewModel extends AndroidViewModel {
   private IdYouTubeRepository idYouTubeRepository;
    private LiveData<List<IdYouTubeEntity>> listWithStringYouTubeId;
    private MutableLiveData<String> ifIdExist = new MutableLiveData<>();

    public IdYouTubeViewModel(@NonNull Application application) {
        super(application);
        idYouTubeRepository = new IdYouTubeRepository(application);

    }

    public void insertIdYouTubeEntity(IdYouTubeEntity idYouTubeEntity){
        idYouTubeRepository.insertIdYouTubeEntity(idYouTubeEntity);
    }

    public LiveData<List<IdYouTubeEntity>> checkIfIdExistsInIdYoutubeEntity(){
        listWithStringYouTubeId = Transformations.switchMap(ifIdExist,id->idYouTubeRepository.filterYoutubeIdIfExist(id));
        return  listWithStringYouTubeId;
    }

    public void setId(String id){
        ifIdExist.setValue(id);
    }


}
