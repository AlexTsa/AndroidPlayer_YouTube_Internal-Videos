package com.alextsatsos.mediayoutubelyrics.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.alextsatsos.mediayoutubelyrics.Entities.LoopEntity;
import com.alextsatsos.mediayoutubelyrics.InterfacesDao.YoutubeDao;
import com.alextsatsos.mediayoutubelyrics.MyVideoYouTubeDatabase;

import java.util.List;

public class YoutubeDatabaseLoopRepository {
    private YoutubeDao youtubeDao;
    private LiveData<List<LoopEntity>> allFromLoopEntity;
    private LiveData<List<LoopEntity>> specificLoopFromLoopEntity;


    public YoutubeDatabaseLoopRepository(Application application) {
        MyVideoYouTubeDatabase database = MyVideoYouTubeDatabase.getInstance(application);
        youtubeDao = database.youtubeDao();
        allFromLoopEntity = youtubeDao.getAllFromLoopEntity();
    }

    public void initSpecificLoopFromLoopEntity(String idYouTube) {
        specificLoopFromLoopEntity = youtubeDao.getSpecificLoopFromLoopEntity(idYouTube);
    }


    public void insertLoops(LoopEntity loopEntity) {
        new InsertLoopEntityAsyncTask(youtubeDao).execute(loopEntity);
    }


    public void deleteLoop(LoopEntity loopEntity) {
        new deleteLoopEntityAsyncTask(youtubeDao).execute(loopEntity);
    }

    public LiveData<List<LoopEntity>> getAllFromLoopEntity() {
        return allFromLoopEntity;
    }

    public LiveData<List<LoopEntity>> getSpecificLoopFromLoopEntity() {
        return specificLoopFromLoopEntity;
    }



   // insert gia Loop Entity
    private static class InsertLoopEntityAsyncTask extends AsyncTask<LoopEntity, Void, Void> {
        private YoutubeDao youtubeDao;

        private InsertLoopEntityAsyncTask(YoutubeDao youtubeDao) {
            this.youtubeDao = youtubeDao;
        }

        @Override
        protected Void doInBackground(LoopEntity... loopEntities) {
            youtubeDao.insertLoops(loopEntities[0]);
            return null;
        }
    }
  // delete gia LoopEntity
    private static class deleteLoopEntityAsyncTask extends AsyncTask<LoopEntity, Void, Void> {
        private YoutubeDao youtubeDao;

        private deleteLoopEntityAsyncTask(YoutubeDao youtubeDao) {
            this.youtubeDao = youtubeDao;
        }

        @Override
        protected Void doInBackground(LoopEntity... loopEntities) {
            youtubeDao.deleteLoopEntity(loopEntities[0]);
            return null;
        }
    }

    }

