package com.alextsatsos.mediayoutubelyrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.alextsatsos.mediayoutubelyrics.Entities.LoopForLocalVideoEntity;
import com.alextsatsos.mediayoutubelyrics.Entities.UriLocalVideoEntity;
import com.alextsatsos.mediayoutubelyrics.LocalVideoModel.VideoModel;
import com.alextsatsos.mediayoutubelyrics.MyAdapters.LoopsLocalVideoAdapter;
import com.alextsatsos.mediayoutubelyrics.MyViewModel.UriLocalVideoLoopViewModel;
import com.alextsatsos.mediayoutubelyrics.MyViewModel.UriLocalVideoViewModel;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PlayLocalVideoActivity extends AppCompatActivity {
    public static final int Add_lyrics_RequestCode = 1;
    private VideoView videoView;
    private MediaController mediaController;
    private VideoModel videoModel;
    private CrystalRangeSeekbar crystalRangeSeekbar;
    private RecyclerView recyclerViewLocalVideo;
    private TextView minValueTextView, maxValueTextView, lyricsLocalVideoEditTextView;
    private MediaPlayer mediaPlayer;
    private long loopLVstart = 0;
    private long loopLVend = 0;
    private UriLocalVideoViewModel uriLocalVideoViewModel;
    private UriLocalVideoLoopViewModel uriLocalVideoLoopViewModel;
    private LoopsLocalVideoAdapter loopsLocalVideoAdapter;
    private boolean flagifuriExists = false;
    private Spinner spinnerSpeed;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private Handler loopHandler;
    private FloatingActionButton floatingActionButtonAddLyrics;
    private String urlLocal, speficiclocalvideoLyrics = "";
    private Switch localVideoSwitchLyrics, localVideoSwitchLoop;
    private ScrollView scrollViewLocalVideoLyrics;
    private Button createLoopBtnForLocalVideo;
    private int mCurrentPosition = 0;
    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";
    private boolean loopflag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_local_video);
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        videoModel = intent.getParcelableExtra("localVideoModelItem");
        uriLocalVideoViewModel = new ViewModelProvider(this).get(UriLocalVideoViewModel.class);
        uriLocalVideoLoopViewModel = new ViewModelProvider(this).get(UriLocalVideoLoopViewModel.class);
        uriLocalVideoViewModel.setUri(videoModel.getVideoUri().toString());
        recyclerViewLocalVideo = findViewById(R.id.locaVideoLoopRecyclerView);
        localVideoSwitchLyrics = findViewById(R.id.localVideoSwitchLyrics);
        localVideoSwitchLoop = findViewById(R.id.localVideoSwitchLoop);
        scrollViewLocalVideoLyrics = findViewById(R.id.scrollViewLocalVideoLyrics);
        floatingActionButtonAddLyrics = findViewById(R.id.floatingActionButtonAddLyrics);
        createLoopBtnForLocalVideo = findViewById(R.id.createLoopBtnForLocalVideo);
        spinnerSpeed = findViewById(R.id.spinnerSpeed);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.local_video_array_speed, R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSpeed.setAdapter(arrayAdapter);
        lyricsLocalVideoEditTextView = findViewById(R.id.lyricsLocalVideoEditTextView);
        loopsLocalVideoAdapter = new LoopsLocalVideoAdapter();

        uriLocalVideoViewModel.checkSpeficicUriLocalVideoEntity().observe(this, new Observer<List<UriLocalVideoEntity>>() {
            @Override
            public void onChanged(List<UriLocalVideoEntity> uriLocalVideoEntities) {
                if (uriLocalVideoEntities.size() == 0) {
                    flagifuriExists = false;
                } else {
                    flagifuriExists = true;
                    urlLocal = uriLocalVideoEntities.get(0).getUriVideo();
                    speficiclocalvideoLyrics = uriLocalVideoEntities.get(0).getLocalVideoLyrics();
                }

            }
        });
        uriLocalVideoLoopViewModel.getSpecificLoopForLocalVideoEntity().observe(this, new Observer<List<LoopForLocalVideoEntity>>() {
            @Override
            public void onChanged(List<LoopForLocalVideoEntity> loopForLocalVideoEntities) {
                loopsLocalVideoAdapter.setLoopForLocalVideoEntityList(loopForLocalVideoEntities);
            }
        });
        recyclerViewLocalVideo.setAdapter(loopsLocalVideoAdapter);
        recyclerViewLocalVideo.setLayoutManager(layoutManager);
        loopsLocalVideoAdapter.setListener(new LoopsLocalVideoAdapter.Listener() {
            @Override
            public void onClick(int position) {
                loopflag=true;
                seekToAndPause(loopsLocalVideoAdapter.getLoopLocalVideoEntity(position).getLoopLocalVideoStart(), loopsLocalVideoAdapter.getLoopLocalVideoEntity(position).getLoopLocalVideoEnd());

            }
        });

        floatingActionButtonAddLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentl = new Intent(PlayLocalVideoActivity.this, AddLyricsToLocalVideo.class);
                intentl.putExtra("LyricsForSpecificLocalVideoIntent", speficiclocalvideoLyrics);
                startActivityForResult(intentl, Add_lyrics_RequestCode);

            }
        });
        localVideoSwitchLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localVideoSwitchLyrics.isChecked()) {
                    scrollViewLocalVideoLyrics.setVisibility(View.VISIBLE);
                    createLoopBtnForLocalVideo.setVisibility(View.GONE);
                    recyclerViewLocalVideo.setVisibility(View.GONE);
                    localVideoSwitchLoop.setChecked(false);
                    lyricsLocalVideoEditTextView.setText(speficiclocalvideoLyrics);
                }
            }
        });
        localVideoSwitchLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localVideoSwitchLoop.isChecked()) {
                    scrollViewLocalVideoLyrics.setVisibility(View.GONE);
                    createLoopBtnForLocalVideo.setVisibility(View.VISIBLE);
                    recyclerViewLocalVideo.setVisibility(View.VISIBLE);
                    localVideoSwitchLyrics.setChecked(false);
                }
            }
        });
        //6/7
        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(String.valueOf(Uri.parse(videoModel.getVideoUri().toString())));
        videoView.getHolder().setSizeFromLayout();

        //
       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
           @Override
           public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
               return false;
           }

           @Override
           public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               uriLocalVideoLoopViewModel.deleteLoopForLocalVideoEntity( loopsLocalVideoAdapter.getLoopLocalVideoEntity(viewHolder.getAdapterPosition()));
               Toast.makeText(PlayLocalVideoActivity.this, "Loop deleted ", Toast.LENGTH_SHORT).show();

           }
       }).attachToRecyclerView(recyclerViewLocalVideo);


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this, "On save " + mCurrentPosition, Toast.LENGTH_SHORT).show();
        outState.putInt(PLAYBACK_TIME,mCurrentPosition);

    }

    private void configureVideoView() {
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer = mp;
                // mediaPlayer.setLooping(true);
                uriLocalVideoLoopViewModel.setUri(videoModel.getVideoUri().toString());
                initCrystalRangeSeekBar(videoView.getDuration());
                setSpinnerOnItem();
            }
        });

        videoView.start();

    }

    private void initCrystalRangeSeekBar(int duration) {
        crystalRangeSeekbar = findViewById(R.id.crystalRangeSeekbarLocalVideo);
        minValueTextView = findViewById(R.id.minValueLocalVideoTextView);
        maxValueTextView = findViewById(R.id.maxValueLocalVideoTextView);
        crystalRangeSeekbar.setMinValue(0);
        crystalRangeSeekbar.setMaxValue(duration);
        crystalRangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minValueTextView.setText(timeConvert((long) minValue));
                maxValueTextView.setText(timeConvert((long) maxValue));
                loopLVstart = (long) minValue;
                loopLVend = (long) maxValue;
            }
        });
    }

    public void creatLoopForLocalVideo(View view) {
        //  mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(0.3f));
        //  Toast.makeText(PlayLocalVideoActivity.this, "ola kala", Toast.LENGTH_LONG).show();


        try {
            if (flagifuriExists) {
                LoopForLocalVideoEntity loopForLocalVideoEntity = new LoopForLocalVideoEntity();
                loopForLocalVideoEntity.setUriVideoloop(videoModel.getVideoUri().toString());
                loopForLocalVideoEntity.setLoopLocalVideoStart(loopLVstart);
                loopForLocalVideoEntity.setLoopLocalVideoEnd(loopLVend);
                uriLocalVideoLoopViewModel.insertLoopForLocalVideoEntity(loopForLocalVideoEntity);
            } else {
                UriLocalVideoEntity uriLocalVideoEntity = new UriLocalVideoEntity();
                uriLocalVideoEntity.setUriVideo(videoModel.getVideoUri().toString());
                uriLocalVideoViewModel.insertUriLocalVideoEntity(uriLocalVideoEntity);

                LoopForLocalVideoEntity loopForLocalVideoEntity = new LoopForLocalVideoEntity();
                loopForLocalVideoEntity.setUriVideoloop(videoModel.getVideoUri().toString());
                loopForLocalVideoEntity.setLoopLocalVideoStart(loopLVstart);
                loopForLocalVideoEntity.setLoopLocalVideoEnd(loopLVend);
                uriLocalVideoLoopViewModel.insertLoopForLocalVideoEntity(loopForLocalVideoEntity);
            }
            Toast.makeText(this, "Loop Added", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void setSpinnerOnItem() {
        spinnerSpeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setMediaSpeed(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setMediaSpeed(int position) {

        switch (position) {
            case 0:
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(0.5f));
                break;
            case 1:
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(0.75f));
                break;
            case 3:
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(1.25f));
                break;
            case 4:
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(1.75f));
                break;
            case 2:
            default:
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(1f));
        }
    }

    private void seekToAndPause(long start, long end) {
        videoView.seekTo((int) start);
        loopHandler = new Handler();
        loopHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (videoView.getCurrentPosition() <= end) {
                    loopHandler.postDelayed(this, 1000);
                } else {
                    loopHandler.removeCallbacks(this);
                    videoView.pause();
                    //seekToAndPause(start,end);
                }
            }
        }, 1000);
    }

    private String timeConvert(long value) {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Add_lyrics_RequestCode && resultCode == RESULT_OK) {
            String lyrics = data.getStringExtra(AddLyricsToLocalVideo.Lyrics_Save);
            if (flagifuriExists) {
                UriLocalVideoEntity uriLocalVideoEntity = new UriLocalVideoEntity();
                uriLocalVideoEntity.setUriVideo(urlLocal);
                uriLocalVideoEntity.setLocalVideoLyrics(lyrics);
                uriLocalVideoViewModel.updateUriLocalVideoEntity(uriLocalVideoEntity);
            } else {
                UriLocalVideoEntity uriLocalVideoEntity = new UriLocalVideoEntity();
                uriLocalVideoEntity.setUriVideo(videoModel.getVideoUri().toString());
                uriLocalVideoEntity.setLocalVideoLyrics(lyrics);
                uriLocalVideoViewModel.insertUriLocalVideoEntity(uriLocalVideoEntity);
            }
            Toast.makeText(this, "Lyrics Updated Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "You don't save the Lyrics ", Toast.LENGTH_SHORT).show();
        }

    }

    //6/7
    @Override
    protected void onStart() {
        super.onStart();
        configureVideoView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.stopPlayback();
    }

    @Override
    protected void onResume() {
        super.onResume();
            videoView.seekTo(mCurrentPosition);
            videoView.start();
            // Skipping to 1 shows the first frame of the video.
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentPosition = videoView.getCurrentPosition();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
           videoView.pause();
        }
    }
    @Override
    public void onBackPressed() {
        if(loopflag){
            loopHandler.removeCallbacksAndMessages(null);
        }
        finish();
    }


}
