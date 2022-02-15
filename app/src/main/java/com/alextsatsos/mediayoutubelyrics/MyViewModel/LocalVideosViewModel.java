package com.alextsatsos.mediayoutubelyrics.MyViewModel;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alextsatsos.mediayoutubelyrics.LocalVideoModel.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class LocalVideosViewModel  extends AndroidViewModel {
private MutableLiveData<List<VideoModel>> mutableLiveData;
private List<VideoModel > videoArrayList;
    public LocalVideosViewModel(@NonNull Application application) {
        super(application);
        mutableLiveData = new MutableLiveData<>();
        videoArrayList = new ArrayList<>();

    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void getTheVideos(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                VideoModel videoModel  = new VideoModel(title,duration,uri);
                videoModel .setVideoUri(Uri.parse(data));
                videoModel .setVideoDuration(duration);
                videoArrayList.add(videoModel);

            } while (cursor.moveToNext());
        }
        if(cursor !=null){
            cursor.close();
        }
        mutableLiveData.postValue(videoArrayList);
    }
    public LiveData<List<VideoModel>> arrayListLiveData(){
        return  mutableLiveData;
    }
    private List<VideoModel> grt(){
        return  videoArrayList;
    }




}
