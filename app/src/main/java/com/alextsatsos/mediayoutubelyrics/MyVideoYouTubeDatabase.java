package com.alextsatsos.mediayoutubelyrics;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.alextsatsos.mediayoutubelyrics.Entities.IdYouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.Entities.LoopEntity;
import com.alextsatsos.mediayoutubelyrics.Entities.LoopForLocalVideoEntity;
import com.alextsatsos.mediayoutubelyrics.Entities.UriLocalVideoEntity;
import com.alextsatsos.mediayoutubelyrics.Entities.YouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.InterfacesDao.YoutubeDao;

@Database(entities = {YouTubeEntity.class, LoopEntity.class, IdYouTubeEntity.class, UriLocalVideoEntity.class, LoopForLocalVideoEntity.class}, version = 1,exportSchema = false)
public abstract class MyVideoYouTubeDatabase extends RoomDatabase {
    //Metratropi tis class se singleton pattern;
    private static MyVideoYouTubeDatabase myVideoYouTubeDatabase;

    public abstract YoutubeDao youtubeDao();

    public static synchronized MyVideoYouTubeDatabase getInstance(Context context) {
         if(myVideoYouTubeDatabase ==null){
             myVideoYouTubeDatabase = Room.databaseBuilder(context.getApplicationContext(),MyVideoYouTubeDatabase.class,"myvideoyoutube_database").fallbackToDestructiveMigration().build();
         }
         return  myVideoYouTubeDatabase;
    }

}
