package com.alextsatsos.mediayoutubelyrics.MyViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.alextsatsos.mediayoutubelyrics.Entities.LoopForLocalVideoEntity;
import com.alextsatsos.mediayoutubelyrics.InterfacesDao.YoutubeDao;
import com.alextsatsos.mediayoutubelyrics.MyVideoYouTubeDatabase;
import com.alextsatsos.mediayoutubelyrics.Repositories.UriLocalVideoDatabaseLoopRepository;

import java.util.List;

public class UriLocalVideoLoopViewModel extends AndroidViewModel {
    private MutableLiveData<String> specificloopUri = new MutableLiveData<>();
    private LiveData<List<LoopForLocalVideoEntity>> specificLoopForLocalVideoEntity;
    private UriLocalVideoDatabaseLoopRepository uriLocalVideoDatabaseLoopRepository;
    public UriLocalVideoLoopViewModel(@NonNull Application application) {
        super(application);
        uriLocalVideoDatabaseLoopRepository = new UriLocalVideoDatabaseLoopRepository(application);
    }
    public void insertLoopForLocalVideoEntity(LoopForLocalVideoEntity loopForLocalVideoEntity){
        uriLocalVideoDatabaseLoopRepository.insertLoopForLocalVideoEntity(loopForLocalVideoEntity);
    }
    public  void  deleteLoopForLocalVideoEntity(LoopForLocalVideoEntity loopForLocalVideoEntity){
        uriLocalVideoDatabaseLoopRepository.deleteLoopForLocalVideoEntity(loopForLocalVideoEntity);
    }
    public LiveData<List<LoopForLocalVideoEntity>> getSpecificLoopForLocalVideoEntity(){
        specificLoopForLocalVideoEntity =  Transformations.switchMap(specificloopUri,uri-> uriLocalVideoDatabaseLoopRepository.getSpecificLoopForLocalVideoEntity(uri));
        return specificLoopForLocalVideoEntity;
    }
    public void setUri(String uri){
        specificloopUri.setValue(uri);
    }
}
