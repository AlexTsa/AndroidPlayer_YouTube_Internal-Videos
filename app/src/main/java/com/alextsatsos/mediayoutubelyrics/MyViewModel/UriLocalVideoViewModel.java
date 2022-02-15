package com.alextsatsos.mediayoutubelyrics.MyViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.alextsatsos.mediayoutubelyrics.Entities.UriLocalVideoEntity;
import com.alextsatsos.mediayoutubelyrics.Repositories.UriLocalVideoDatabaseRepository;

import java.util.List;

public class UriLocalVideoViewModel extends AndroidViewModel {
    private UriLocalVideoDatabaseRepository uriLocalVideoDatabaseRepository;
    private MutableLiveData<String> ifUriExist = new MutableLiveData<>();
    private LiveData<List<UriLocalVideoEntity>> listifuriexist;

    public UriLocalVideoViewModel(@NonNull Application application) {
        super(application);
        uriLocalVideoDatabaseRepository = new UriLocalVideoDatabaseRepository(application);
    }

    public void insertUriLocalVideoEntity(UriLocalVideoEntity uriLocalVideoEntity) {
        uriLocalVideoDatabaseRepository.insertUriLocalVideoEntity(uriLocalVideoEntity);
    }
    public void updateUriLocalVideoEntity(UriLocalVideoEntity uriLocalVideoEntity){
        uriLocalVideoDatabaseRepository.updateUriLocalVideoEntity(uriLocalVideoEntity);
    }

    public LiveData<List<UriLocalVideoEntity>> checkSpeficicUriLocalVideoEntity() {
        listifuriexist= Transformations.switchMap(ifUriExist,uri->uriLocalVideoDatabaseRepository.filterSpeficicUriLocalVideoEntityIfExist(uri));
        return listifuriexist;
    }
    public void setUri(String uri){
        ifUriExist.setValue(uri);
    }
}
