package com.alextsatsos.mediayoutubelyrics.InterfacesDao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alextsatsos.mediayoutubelyrics.Entities.IdYouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.Entities.LoopEntity;
import com.alextsatsos.mediayoutubelyrics.Entities.LoopForLocalVideoEntity;
import com.alextsatsos.mediayoutubelyrics.Entities.UriLocalVideoEntity;
import com.alextsatsos.mediayoutubelyrics.Entities.YouTubeEntity;


import java.util.List;

@Dao
public interface YoutubeDao {

    //insert methoid
    @Insert
    void insert(YouTubeEntity youTubeEntity);

    @Insert
     void insertLoops(LoopEntity loopEntity);

    @Insert
    void insertIdYouTubeEntity(IdYouTubeEntity idYouTubeEntity);

    @Insert
    void insertUriLocalVideoEntity(UriLocalVideoEntity uriLocalVideoEntity);

    @Insert
    void insertLoopForLocalVideoEntity(LoopForLocalVideoEntity loopForLocalVideoEntity);


    @Delete
    void deletetUriLocalVideoEntity(UriLocalVideoEntity uriLocalVideoEntity);

    @Delete
    void deleteLoopForLocalVideoEntity(LoopForLocalVideoEntity loopForLocalVideoEntity);

    @Delete
    public void deleteYoutubeEntity(YouTubeEntity youTubeEntity);

    @Delete
    public void deleteLoopEntity(LoopEntity loopEntity);

    @Update
    void updateYoutubeEntity(YouTubeEntity youTubeEntity);

    @Update
    void updateUriLocalVideoEntity(UriLocalVideoEntity uriLocalVideoEntity);

    //query methodoi
    @Query("SELECT * FROM youtubeentity_table")
    LiveData<List<YouTubeEntity>> getAllFromYouTubeEntity();

    @Query("Select * From loopentity_table")
    LiveData<List<LoopEntity>> getAllFromLoopEntity();


    @Query("SELECT * FROM loopentity_table LPT" +
            " INNER JOIN idyoutubeentity_table IYTT" +
            " ON LPT.youTuBeEntityid=IYTT.youTubeId" +
            " WHERE  youTuBeEntityid = :ytid")
    LiveData<List<LoopEntity>> getSpecificLoopFromLoopEntity(String ytid);

    //gia ola
    @Query("SELECT * FROM idyoutubeentity_table")
    LiveData<List<IdYouTubeEntity>> checkIfIdExistsInIdYoutubeEntity();

    @Query("SELECT youTubeId FROM idyoutubeentity_table WHERE youTubeId =:ytid")
    LiveData<List<IdYouTubeEntity>> filterYoutubeIdIfExist(String ytid);

    @Query("SELECT * FROM youtubeentity_table ORDER BY title DESC")
    LiveData<List<YouTubeEntity>> getAllFromYouTubeEntityORDERBYtitleDESC();

    @Query("SELECT * FROM youtubeentity_table ORDER BY title ASC")
    LiveData<List<YouTubeEntity>> getAllFromYouTubeEntityORDERBYtitleASC();

    @Query("SELECT * FROM urilocalvideoentity_table")
    LiveData<List<UriLocalVideoEntity>> getAllFromUriLocalVideoEntity();

    @Query("SELECT * FROM urilocalvideoentity_table WHERE uriVideo =:uri")
    LiveData<List<UriLocalVideoEntity>> filterSpeficicUriLocalVideoEntityIfExist(String uri);

    @Query("SELECT * FROM loopforlocalvideoentity_table LLVE" +
            " INNER JOIN urilocalvideoentity_table ULVE" +
            " ON  LLVE.uriVideoloop = ULVE.uriVideo" +
            " WHERE uriVideoloop = :uriLocal")
    LiveData<List<LoopForLocalVideoEntity>> getSpecificLoopForLocalVideoEntity(String uriLocal);
}
