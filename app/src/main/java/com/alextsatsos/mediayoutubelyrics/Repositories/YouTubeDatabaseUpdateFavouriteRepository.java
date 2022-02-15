package com.alextsatsos.mediayoutubelyrics.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.alextsatsos.mediayoutubelyrics.Entities.YouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.InterfacesDao.YoutubeDao;
import com.alextsatsos.mediayoutubelyrics.MyVideoYouTubeDatabase;

public class YouTubeDatabaseUpdateFavouriteRepository {
    private YoutubeDao youtubeDao;

   public YouTubeDatabaseUpdateFavouriteRepository(Application application){
       MyVideoYouTubeDatabase database = MyVideoYouTubeDatabase.getInstance(application);
       youtubeDao =database.youtubeDao();
    }

    public  void updateYoutubeEntity(YouTubeEntity youTubeEntity){
       new updateYoutubeEntityAsyncTask(youtubeDao).execute(youTubeEntity);
    }
    private  static  class updateYoutubeEntityAsyncTask extends AsyncTask<YouTubeEntity,Void,Void> {
        private  YoutubeDao youtubeDao;
        private  updateYoutubeEntityAsyncTask(YoutubeDao youtubeDao){
            this.youtubeDao = youtubeDao;
        }
        @Override
        protected Void doInBackground(YouTubeEntity... youtubeEntities) {
            youtubeDao.updateYoutubeEntity(youtubeEntities[0]);
            return null;
        }
    }


}
