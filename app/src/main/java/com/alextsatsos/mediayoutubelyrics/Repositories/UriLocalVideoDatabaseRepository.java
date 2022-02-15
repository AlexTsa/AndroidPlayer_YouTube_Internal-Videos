package com.alextsatsos.mediayoutubelyrics.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.alextsatsos.mediayoutubelyrics.Entities.UriLocalVideoEntity;
import com.alextsatsos.mediayoutubelyrics.InterfacesDao.YoutubeDao;
import com.alextsatsos.mediayoutubelyrics.MyVideoYouTubeDatabase;

import java.util.List;

public class UriLocalVideoDatabaseRepository {
    private YoutubeDao youtubeDao;
    private   LiveData<List<UriLocalVideoEntity>> ifuriexist;

    public UriLocalVideoDatabaseRepository(Application application) {
        MyVideoYouTubeDatabase database = MyVideoYouTubeDatabase.getInstance(application);
        youtubeDao = database.youtubeDao();
    }
    public LiveData<List<UriLocalVideoEntity>> filterSpeficicUriLocalVideoEntityIfExist(String uri){
        ifuriexist = youtubeDao.filterSpeficicUriLocalVideoEntityIfExist(uri);
        return ifuriexist;
    }


    public void insertUriLocalVideoEntity(UriLocalVideoEntity uriLocalVideoEntity) {
        new InsertUriLocalVideoEntityAsynTask(youtubeDao).execute(uriLocalVideoEntity);
    }
    public void updateUriLocalVideoEntity(UriLocalVideoEntity uriLocalVideoEntity){
      new UpdateUriLocalVideoEntityAsynTask(youtubeDao).execute(uriLocalVideoEntity);
    }


    private static class InsertUriLocalVideoEntityAsynTask extends AsyncTask<UriLocalVideoEntity, Void, Void> {
        private YoutubeDao youtubeDao;

        private InsertUriLocalVideoEntityAsynTask(YoutubeDao youtubeDao) {
            this.youtubeDao = youtubeDao;
        }

        @Override
        protected Void doInBackground(UriLocalVideoEntity... uriLocalVideoEntities) {
            youtubeDao.insertUriLocalVideoEntity(uriLocalVideoEntities[0]);
            return null;
        }
    }
    private static class UpdateUriLocalVideoEntityAsynTask extends AsyncTask<UriLocalVideoEntity, Void, Void>{
        private  YoutubeDao youtubeDao;

        private UpdateUriLocalVideoEntityAsynTask(YoutubeDao youtubeDao){
            this.youtubeDao=youtubeDao;
        }

        @Override
        protected Void doInBackground(UriLocalVideoEntity... uriLocalVideoEntities) {
           youtubeDao.updateUriLocalVideoEntity(uriLocalVideoEntities[0]);
            return null;
        }
    }

}
