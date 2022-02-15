package com.alextsatsos.mediayoutubelyrics;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.alextsatsos.mediayoutubelyrics.LocalVideoModel.VideoModel;
import com.alextsatsos.mediayoutubelyrics.MyAdapters.LocalVideoAdapter;
import com.alextsatsos.mediayoutubelyrics.MyViewModel.LocalVideosViewModel;

import java.util.ArrayList;
import java.util.List;

public class DisplayLocalVideosActivity extends AppCompatActivity {
    public static final int PERMISSION_READ = 0;
    private RecyclerView localVideosRecyclerView;
    private LocalVideoAdapter localVideoAdapter;
    private LocalVideosViewModel localVideosViewModel;
    private boolean flagonce = false;
    private static final String once = "once";

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_local_videos);

        if (checkPermission()) {
            displayLocalVideo();

        }


    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void displayLocalVideo() {
        localVideosRecyclerView = findViewById(R.id.localVideosRecyclerView);
        localVideoAdapter = new LocalVideoAdapter();
        localVideosViewModel = new ViewModelProvider(this).get(LocalVideosViewModel.class);
        localVideosViewModel.getTheVideos(this);

        localVideosViewModel.arrayListLiveData().observe(this, new Observer<List<VideoModel>>() {
            @Override
            public void onChanged(List<VideoModel> videoModels) {
                localVideoAdapter.setResults(videoModels);
            }
        });
        localVideosRecyclerView.setAdapter(localVideoAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        localVideosRecyclerView.setLayoutManager(layoutManager);
        localVideoAdapter.setListener(new LocalVideoAdapter.Listener() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(DisplayLocalVideosActivity.this, PlayLocalVideoActivity.class);
                intent.putExtra("localVideoModelItem", localVideoAdapter.getVideoModelAt(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.localvideosearch_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.localvideo_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                localVideoAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getApplicationContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {


                    }
                }
            }
        }
    }
}
