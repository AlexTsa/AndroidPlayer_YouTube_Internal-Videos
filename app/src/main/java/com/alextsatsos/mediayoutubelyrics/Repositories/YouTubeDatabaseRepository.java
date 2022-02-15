package com.alextsatsos.mediayoutubelyrics.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.alextsatsos.mediayoutubelyrics.Entities.YouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.InterfacesDao.YoutubeDao;
import com.alextsatsos.mediayoutubelyrics.MyVideoYouTubeDatabase;

import java.util.List;

public class YouTubeDatabaseRepository {
    private LiveData<List<YouTubeEntity>> allFromYouTubeEntity;
    private LiveData<List<YouTubeEntity>> allFromYouTubeEntityORDERBYtitleDESC;
    private LiveData<List<YouTubeEntity>> allFromYouTubeEntityORDERBYtitleASC;
    private YoutubeDao youtubeDao;


    public YouTubeDatabaseRepository(Application application) {
        MyVideoYouTubeDatabase database = MyVideoYouTubeDatabase.getInstance(application);
        youtubeDao = database.youtubeDao();
        allFromYouTubeEntity = youtubeDao.getAllFromYouTubeEntity();
        allFromYouTubeEntityORDERBYtitleDESC= youtubeDao.getAllFromYouTubeEntityORDERBYtitleDESC();
        allFromYouTubeEntityORDERBYtitleASC=youtubeDao.getAllFromYouTubeEntityORDERBYtitleASC();

    }

    public void insert(YouTubeEntity youTubeEntity) {
        new InsertYoutubeEntityAsyncTask(youtubeDao).execute(youTubeEntity);
    }
    public void deleteYoutubeEntity(YouTubeEntity youTubeEntity){
        new DeleteYoutubeEntityAsyncTask(youtubeDao).execute(youTubeEntity);
    }

    public LiveData<List<YouTubeEntity>> getAllFromYouTubeEntity() {
        return allFromYouTubeEntity;
    }
    public LiveData<List<YouTubeEntity>>  getAllFromYouTubeEntityORDERBYtitleDESC() {
        return allFromYouTubeEntityORDERBYtitleDESC;
    }
    public LiveData<List<YouTubeEntity>>  getAllFromYouTubeEntityORDERBYtitleASC() {
        return  allFromYouTubeEntityORDERBYtitleASC;
    }


    private static class InsertYoutubeEntityAsyncTask extends AsyncTask<YouTubeEntity, Void, Void> {
        private YoutubeDao youtubeDao;

        private InsertYoutubeEntityAsyncTask(YoutubeDao youtubeDao) {
            this.youtubeDao = youtubeDao;
        }

        @Override
        protected Void doInBackground(YouTubeEntity... youtubeEntities) {
            youtubeDao.insert(youtubeEntities[0]);
            return null;
        }
    }
    private static class DeleteYoutubeEntityAsyncTask extends AsyncTask<YouTubeEntity, Void, Void> {
        private YoutubeDao youtubeDao;

        private DeleteYoutubeEntityAsyncTask(YoutubeDao youtubeDao) {
            this.youtubeDao = youtubeDao;
        }

        @Override
        protected Void doInBackground(YouTubeEntity... youtubeEntities) {
            youtubeDao.deleteYoutubeEntity(youtubeEntities[0]);
            return null;
        }
    }

}
