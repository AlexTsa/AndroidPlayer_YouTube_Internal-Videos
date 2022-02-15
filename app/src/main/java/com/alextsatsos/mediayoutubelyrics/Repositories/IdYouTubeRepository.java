package com.alextsatsos.mediayoutubelyrics.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.alextsatsos.mediayoutubelyrics.Entities.IdYouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.InterfacesDao.YoutubeDao;
import com.alextsatsos.mediayoutubelyrics.MyVideoYouTubeDatabase;

import java.util.List;

public class IdYouTubeRepository {
    private YoutubeDao youtubeDao;
   // private LiveData<List<IdYouTubeEntity>> listWithStringYouTubeId;
    private LiveData<List<IdYouTubeEntity>> ifIdExist;

    public IdYouTubeRepository(Application application) {
        MyVideoYouTubeDatabase database = MyVideoYouTubeDatabase.getInstance(application);
        youtubeDao = database.youtubeDao();
    }

    public void insertIdYouTubeEntity(IdYouTubeEntity idYouTubeEntity) {
        new InsertIdYouTubeEntityAsyncTask(youtubeDao).execute(idYouTubeEntity);
    }
       //edw einai ola
   // public LiveData<List<IdYouTubeEntity>> checkIfIdExistsInIdYoutubeEntity() {
    //   listWithStringYouTubeId =youtubeDao.checkIfIdExistsInIdYoutubeEntity();
    //    return listWithStringYouTubeId;
  //  }
    public  LiveData<List<IdYouTubeEntity>> filterYoutubeIdIfExist(String ytid){
        ifIdExist = youtubeDao.filterYoutubeIdIfExist(ytid);
        return  ifIdExist;
    }


    private static class InsertIdYouTubeEntityAsyncTask extends AsyncTask<IdYouTubeEntity, Void, Void> {
        private YoutubeDao youtubeDao;

        private InsertIdYouTubeEntityAsyncTask(YoutubeDao youtubeDao) {
            this.youtubeDao = youtubeDao;
        }

        @Override
        protected Void doInBackground(IdYouTubeEntity... idYouTubeEntities) {
            youtubeDao.insertIdYouTubeEntity(idYouTubeEntities[0]);
            return null;
        }
    }
}
