package com.alextsatsos.mediayoutubelyrics.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.alextsatsos.mediayoutubelyrics.Entities.LoopForLocalVideoEntity;
import com.alextsatsos.mediayoutubelyrics.InterfacesDao.YoutubeDao;
import com.alextsatsos.mediayoutubelyrics.MyVideoYouTubeDatabase;

import java.util.List;

public class UriLocalVideoDatabaseLoopRepository {
    private YoutubeDao youtubeDao;
    private LiveData<List<LoopForLocalVideoEntity>> specificLoopForLocalVideoEntity;

    public UriLocalVideoDatabaseLoopRepository(Application application) {
        MyVideoYouTubeDatabase database = MyVideoYouTubeDatabase.getInstance(application);
        youtubeDao = database.youtubeDao();
    }

    public void insertLoopForLocalVideoEntity(LoopForLocalVideoEntity loopForLocalVideoEntity) {
        new InsertLoopForLocalVideoEntityAsynTask(youtubeDao).execute(loopForLocalVideoEntity);
    }
    public void  deleteLoopForLocalVideoEntity(LoopForLocalVideoEntity loopForLocalVideoEntity){
        new DeleteLoopForLocalVideoEntityAsynTask(youtubeDao).execute(loopForLocalVideoEntity);
    }

    public LiveData<List<LoopForLocalVideoEntity>> getSpecificLoopForLocalVideoEntity(String uri){
        return specificLoopForLocalVideoEntity = youtubeDao.getSpecificLoopForLocalVideoEntity(uri);
    }
    public static class InsertLoopForLocalVideoEntityAsynTask extends AsyncTask<LoopForLocalVideoEntity, Void, Void> {
        private YoutubeDao youtubeDao;

        private InsertLoopForLocalVideoEntityAsynTask(YoutubeDao youtubeDao) {
            this.youtubeDao = youtubeDao;
        }


        @Override
        protected Void doInBackground(LoopForLocalVideoEntity... loopForLocalVideoEntities) {
            youtubeDao.insertLoopForLocalVideoEntity(loopForLocalVideoEntities[0]);
            return null;
        }
    }
    public static class DeleteLoopForLocalVideoEntityAsynTask extends AsyncTask<LoopForLocalVideoEntity, Void, Void> {
        private YoutubeDao youtubeDao;

        private DeleteLoopForLocalVideoEntityAsynTask(YoutubeDao youtubeDao) {
            this.youtubeDao = youtubeDao;
        }


        @Override
        protected Void doInBackground(LoopForLocalVideoEntity... loopForLocalVideoEntities) {
            youtubeDao.deleteLoopForLocalVideoEntity(loopForLocalVideoEntities[0]);
            return null;
        }
    }

}
