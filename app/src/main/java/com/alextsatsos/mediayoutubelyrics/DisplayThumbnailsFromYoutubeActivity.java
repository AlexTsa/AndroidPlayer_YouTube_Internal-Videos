package com.alextsatsos.mediayoutubelyrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.IpSecManager;
import android.os.Bundle;
import android.os.Handler;

import com.alextsatsos.mediayoutubelyrics.MyAdapters.CaptionedThumnailAdapter;
import com.alextsatsos.mediayoutubelyrics.MyViewModel.YouTubeViewModel;
import com.alextsatsos.mediayoutubelyrics.YouTubeV3Bean.YoutubeV3Details;

import java.util.List;


public class DisplayThumbnailsFromYoutubeActivity extends AppCompatActivity {
    private RecyclerView youtubeRecycler;
    private CaptionedThumnailAdapter captionedThumnailAdapter;
    public static final String SPECIFIC_TEXT = "Metallica";
    private MutableLiveData<List<YoutubeV3Details>> youtubeV3DetailsList;
    private YouTubeViewModel youTubeViewModel;
    private String idYoutubeVideo;
    private String titleByIdYouTube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_thumbnails_from_youtube);
        String specificTextForYt = getIntent().getExtras().getString(SPECIFIC_TEXT);
        youtubeRecycler = (RecyclerView) findViewById(R.id.youtube_recycler);
        captionedThumnailAdapter = new CaptionedThumnailAdapter();
        youTubeViewModel = new ViewModelProvider(this).get(YouTubeViewModel.class);

        youTubeViewModel.init();

        youTubeViewModel.getyoutubeV3LiveDetails().observe(this, new Observer<YoutubeV3Details>() {
            @Override
            public void onChanged(YoutubeV3Details youtubeV3Details) {
                captionedThumnailAdapter.setResults(youtubeV3Details.getItems());
            }
        });

        youtubeRecycler.setAdapter(captionedThumnailAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        youtubeRecycler.setLayoutManager(layoutManager);
        youTubeViewModel.getYouTubeV3Details(specificTextForYt);
        captionedThumnailAdapter.setListener(new CaptionedThumnailAdapter.Listener() {
            @Override
            public void onClick(int position) {
                idYoutubeVideo = youTubeViewModel.getIdForYoutubeVideo(position);
                titleByIdYouTube =youTubeViewModel.getTitleByIdYouTube(position);
                 //ebala handler 19/6
                Handler displayHandler = new Handler();
                displayHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(DisplayThumbnailsFromYoutubeActivity.this,YouTubeVideoLyricsActivity.class);
                        intent.putExtra(YouTubeVideoLyricsActivity.SPECIFIC_YouTubeId,idYoutubeVideo);
                        intent.putExtra("lyricsMusixMatch", titleByIdYouTube);
                        startActivity(intent);
                    }
                });


            }
        });

    }

}
