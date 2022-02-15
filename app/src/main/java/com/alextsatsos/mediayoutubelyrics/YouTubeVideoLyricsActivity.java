package com.alextsatsos.mediayoutubelyrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alextsatsos.mediayoutubelyrics.Entities.IdYouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.Entities.LoopEntity;
import com.alextsatsos.mediayoutubelyrics.MusixmatchModel.Musixmatch;
import com.alextsatsos.mediayoutubelyrics.MyAdapters.LoopsYouTubeAdapter;
import com.alextsatsos.mediayoutubelyrics.MyViewModel.IdYouTubeViewModel;
import com.alextsatsos.mediayoutubelyrics.MyViewModel.MusixmatchViewModel;
import com.alextsatsos.mediayoutubelyrics.MyViewModel.YouTubeLoopEntityViewModel;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;


import java.util.List;


public class YouTubeVideoLyricsActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
    public static final String SPECIFIC_YouTubeId = "";
    public static int Add_To_Favourite_Request = 1;
    private MyPlayerStateChangeListener myPlayerStateChangeListener;
    private MyPlaybackEventListener myPlaybackEventListener;
    private YouTubePlayer youTubePlayer;
    private YouTubePlayerFragment youTubePlayerFragment;
    private SeekBar seekBar;
    private Button playYtVideoBtn;
    private TextView currentTextView, totalTextView, lyricsTextView, minRangeSeektextView, maxRangeSeektextView;
    private String specificYouTubeId, specificTitleByYouYubeId;
    private double current_pos, total_duration;
    private MusixmatchViewModel musixmatchViewModel;
    private YouTubeLoopEntityViewModel youTubeLoopEntityViewModel;
    private IdYouTubeViewModel idYouTubeViewModel;
    private String lyricsmm;
    private FloatingActionButton floatBtnAddToFavouriteYouTube;
    private Switch lyricsYTSwitch, loopsYTSwitch;
    private RecyclerView loopsYouTubeRecyclerView;
    private Group group, group2;
    private LoopsYouTubeAdapter loopsYouTubeAdapter;
    private CrystalRangeSeekbar crystalRangeSeekbar2;
    private long loopYTstart = 0;
    private long loopYTend = 0;
    private  long startseek,endseek;
    private Handler loopHandler;
    private boolean loopflag=false;
    private boolean flagifIdExists= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_video_lyrics);
        getSupportActionBar().hide();
        specificYouTubeId = getIntent().getExtras().getString(SPECIFIC_YouTubeId);
        specificTitleByYouYubeId = getIntent().getExtras().getString("lyricsMusixMatch");
        youTubeLoopEntityViewModel = new ViewModelProvider(this).get(YouTubeLoopEntityViewModel.class);
        idYouTubeViewModel = new ViewModelProvider(this).get(IdYouTubeViewModel.class);
        loopsYouTubeAdapter = new LoopsYouTubeAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        group = findViewById(R.id.group);
        group2 = findViewById(R.id.group2);
        playYtVideoBtn = findViewById(R.id.playYtVideoBtn);
        lyricsTextView = findViewById(R.id.lyricsTextView);
        lyricsYTSwitch = findViewById(R.id.lyricsYTSwith);
        loopsYTSwitch = findViewById(R.id.loopYTSwitch);
        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                .findFragmentById(R.id.youtubeplayerfragment);
        loopsYouTubeRecyclerView = findViewById(R.id.loopsYouTubeRecyclerView);
        floatBtnAddToFavouriteYouTube = findViewById(R.id.floatBtnAddToFavouriteYouTube);

        musixmatchViewModel = new ViewModelProvider(this).get(MusixmatchViewModel.class);
        musixmatchViewModel.init();
        musixmatchViewModel.getTheLyricsLiveData().observe(this, new Observer<Musixmatch>() {
            @Override
            public void onChanged(Musixmatch musixmatch) {

                lyricsmm = musixmatch.getMessage().getBody().getLyrics().getLyrics_body();
                if(lyricsmm==null){
                    lyricsmm="We did not find lyrics for this video";
                }
            }
        });
        youTubeLoopEntityViewModel.initSpecificLoopFromLoopEntityViewModel(specificYouTubeId);
        youTubeLoopEntityViewModel.getSpecificLoopFromLoopEntity().observe(this, new Observer<List<LoopEntity>>() {
            @Override
            public void onChanged(List<LoopEntity> loopEntities) {
                loopsYouTubeAdapter.setYouTubeEntityList(loopEntities);
            }
        });



        idYouTubeViewModel.checkIfIdExistsInIdYoutubeEntity().observe(YouTubeVideoLyricsActivity.this, new Observer<List<IdYouTubeEntity>>() {
            @Override
            public void onChanged(List<IdYouTubeEntity> idYouTubeEntities) {
                if(idYouTubeEntities.size()==0){
                    flagifIdExists= false;
                }else {
                    flagifIdExists = true;
                }

            }
        });

        /*
        youTubeLoopEntityViewModel.getAllFromLoopEntity().observe(this, new Observer<List<LoopEntity>>() {
            @Override
            public void onChanged(List<LoopEntity> loopEntities) {
                loopsYouTubeAdapter.setYouTubeEntityList(loopEntities);
            }
        });

         */

        loopsYouTubeRecyclerView.setAdapter(loopsYouTubeAdapter);
        loopsYouTubeRecyclerView.setLayoutManager(layoutManager);

        myPlayerStateChangeListener = new MyPlayerStateChangeListener();
        myPlaybackEventListener = new MyPlaybackEventListener();


        floatBtnAddToFavouriteYouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // loopHandler.removeCallbacksAndMessages(null);
                Intent intent = new Intent(YouTubeVideoLyricsActivity.this, AddYouTubeVideoToFavouriteActivity.class);
                intent.putExtra("idYouTubeVideoForAddToFavourite", specificYouTubeId);
                intent.putExtra("titleYouTubeVideoForAddToFavourite", specificTitleByYouYubeId);
                startActivityForResult(intent, Add_To_Favourite_Request);
            }
        });
        loopsYouTubeAdapter.setListener(new LoopsYouTubeAdapter.Listener() {
            @Override
            public void onClick(int position) {
                 startseek = youTubeLoopEntityViewModel.getLoopStartForSeek(position);
                 endseek = youTubeLoopEntityViewModel.getLoopEndForSeek(position);
                 loopflag=true;
                loopandSeekMethod(startseek,endseek);

            }
        });
         //ola ta eixa edw eksw dhladh ayta poy einai sta handler f

        Handler f = new Handler();
        f.post(new Runnable() {
            @Override
            public void run() {
                lyricsYTSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lyricsYTSwitch.isChecked()) {
                            loopsYTSwitch.setChecked(false);
                            group.setVisibility(View.VISIBLE);
                            group2.setVisibility(View.GONE);
                        }
                    }
                });
                loopsYTSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (loopsYTSwitch.isChecked()) {
                            lyricsYTSwitch.setChecked(false);
                            group2.setVisibility(View.VISIBLE);
                            group.setVisibility(View.GONE);
                        }
                    }
                });
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        youTubeLoopEntityViewModel.deleteLoop(loopsYouTubeAdapter.getLoopEntityAt(viewHolder.getAdapterPosition()));
                        Toast.makeText(YouTubeVideoLyricsActivity.this,"Loop Deleted",Toast.LENGTH_SHORT).show();
                    }
                }).attachToRecyclerView(loopsYouTubeRecyclerView);
            }
        });

    }
    private void loopandSeekMethod(long startseek,final long endseek){
        youTubePlayer.seekToMillis((int) startseek);
        // final Handler handler = new Handler();
        loopHandler = new Handler();
        loopHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //For every 1 second, check the current time and endTime
                if (youTubePlayer.getCurrentTimeMillis() <= endseek) {
                    loopHandler.postDelayed(this, 1000);
                } else {
                    loopHandler.removeCallbacks(this); //no longer required
                    //and Pause the video
                    youTubePlayer.pause();
                }
            }
        }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Add_To_Favourite_Request && resultCode == RESULT_OK) {
            Toast.makeText(this, "You saved a video to Favourite List", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, " You did not save a video", Toast.LENGTH_SHORT).show();

        }


    }

    public void playYoutubeBtnMethod(View view) {
        idYouTubeViewModel.setId(specificYouTubeId);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                youTubePlayerFragment.initialize("AIzaSyDRRMgvJzr4gFF0F78I5MCZSSSadsj6k0U", YouTubeVideoLyricsActivity.this);
                musixmatchViewModel.getTheLyrics(specificTitleByYouYubeId);
            }
        });

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
        this.youTubePlayer = youTubePlayer;
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        youTubePlayer.setPlaybackEventListener(myPlaybackEventListener);
        youTubePlayer.setPlayerStateChangeListener(myPlayerStateChangeListener);
        youTubePlayer.loadVideo(specificYouTubeId);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    public void setVideoProgress() {

        currentTextView = findViewById(R.id.currentTextView);
        totalTextView = findViewById(R.id.totalTextView);
        seekBar = findViewById(R.id.seekbar);   //ta eixa sta onclick gia to youtbe


        lyricsTextView.setText(lyricsmm);
        current_pos = youTubePlayer.getCurrentTimeMillis();
        total_duration = youTubePlayer.getDurationMillis();

        totalTextView.setText(timeConversion((long) total_duration));
        currentTextView.setText(timeConversion((long) current_pos));
        seekBar.setMax((int) total_duration);
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                if (youTubePlayer != null) {
                    try {

                        current_pos = youTubePlayer.getCurrentTimeMillis();
                        currentTextView.setText(timeConversion((long) current_pos));
                        seekBar.setProgress((int) current_pos);
                        handler.postDelayed(this, 1000);
                    } catch (IllegalStateException ed) {
                        ed.printStackTrace();
                    }
                }
            }
        };
        handler.postDelayed(runnable, 1000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_pos = seekBar.getProgress();
                youTubePlayer.seekToMillis((int) current_pos);
            }
        });

    }

    private void initRangeSeekBarMinAndMaxText() {
        crystalRangeSeekbar2 = findViewById(R.id.crystalRangeSeekbar2);
        minRangeSeektextView = findViewById(R.id.minRangeSeektextView);
        maxRangeSeektextView = findViewById(R.id.maxRangeSeektextView);
        crystalRangeSeekbar2.setMinValue(0);
        crystalRangeSeekbar2.setMaxValue(youTubePlayer.getDurationMillis());
        crystalRangeSeekbar2.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minRangeSeektextView.setText(timeConversion((long) minValue));
                maxRangeSeektextView.setText(timeConversion((long) maxValue));
                loopYTstart = (long) minValue;
                loopYTend = (long) maxValue;
            }
        });
    }

    public void createLoopForYouTube(View view) {
      try {
          if(flagifIdExists){
              LoopEntity loopEntity = new LoopEntity();
              loopEntity.setYouTuBeEntityid(specificYouTubeId);
              loopEntity.setLoopStart(loopYTstart);
              loopEntity.setLoopEnd(loopYTend);
              youTubeLoopEntityViewModel.insertLoops(loopEntity);
          }else{
              IdYouTubeEntity idYouTubeEntity = new IdYouTubeEntity();
              idYouTubeEntity.setYouTubeId(specificYouTubeId);
              idYouTubeViewModel.insertIdYouTubeEntity(idYouTubeEntity);

              LoopEntity loopEntity = new LoopEntity();
              loopEntity.setYouTuBeEntityid(specificYouTubeId);
              loopEntity.setLoopStart(loopYTstart);
              loopEntity.setLoopEnd(loopYTend);
              youTubeLoopEntityViewModel.insertLoops(loopEntity);
          }
          Toast.makeText(YouTubeVideoLyricsActivity.this,"Loop Added",Toast.LENGTH_SHORT).show();
      }catch (Exception ex){
          Toast.makeText(YouTubeVideoLyricsActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();

      }


    }

    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {


        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {
            Handler handlervideo = new Handler();
            handlervideo.post(new Runnable() {
                @Override
                public void run() {
                    setVideoProgress();
                    initRangeSeekBarMinAndMaxText();
                }
            });

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    }

    public String timeConversion(long value) {
        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;
    }

    @Override
    protected void onPause() {
        super.onPause();
      //
    }
    @Override
    public void onBackPressed() {
        if(loopflag){
            loopHandler.removeCallbacksAndMessages(null);
        }
        youTubePlayer = null;
        finish();
    }

}