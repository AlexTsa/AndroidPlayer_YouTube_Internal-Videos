package com.alextsatsos.mediayoutubelyrics.LocalVideoModel;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class VideoModel implements Parcelable {
    private String videoTitle;
    private String videoDuration;
    private Uri videoUri;

    public VideoModel(String title, String duration, Uri uri) {
        this.videoTitle = title;
        this.videoDuration = duration;
        this.videoUri = uri;
    }


    protected VideoModel(Parcel in) {
        videoTitle = in.readString();
        videoDuration = in.readString();
        videoUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
        @Override
        public VideoModel createFromParcel(Parcel in) {
            return new VideoModel(in);
        }

        @Override
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public Uri getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(Uri videoUri) {
        this.videoUri = videoUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoTitle);
        dest.writeString(videoDuration);
        dest.writeParcelable(videoUri, flags);
    }
}
