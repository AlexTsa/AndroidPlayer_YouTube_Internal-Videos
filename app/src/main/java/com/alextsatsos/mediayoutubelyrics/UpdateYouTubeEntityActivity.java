package com.alextsatsos.mediayoutubelyrics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.alextsatsos.mediayoutubelyrics.Entities.YouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.MyViewModel.YouTubeUpdateYouTubeViewModel;

public class UpdateYouTubeEntityActivity extends AppCompatActivity {
    private EditText titleUpdateYoutubeEntityEditText, genreUpdateYoutubeEntityEditText, categoryUpdateYoutubeEntityEditText;
    private  String ytid, category, genre, title,did;
    private  YouTubeUpdateYouTubeViewModel youTubeUpdateYouTubeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_you_tube_entity);
        titleUpdateYoutubeEntityEditText = findViewById(R.id.titleUpdateYoutubeEntityEditText);
        genreUpdateYoutubeEntityEditText = findViewById(R.id.genreUpdateYoutubeEntityEditText);
        categoryUpdateYoutubeEntityEditText = findViewById(R.id.categoryUpdateYoutubeEntityEditText);
        ytid = getIntent().getExtras().getString("ytidForUpdate");
        category = getIntent().getExtras().getString("categoryForUpdate");
        genre=getIntent().getExtras().getString("genreForUpdate");
        title=getIntent().getExtras().getString("titleForUpdate");
        did = getIntent().getExtras().getString("didForUpdate");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Update Your Favourite Video");
        titleUpdateYoutubeEntityEditText.setText(title);
        genreUpdateYoutubeEntityEditText.setText(genre);
        categoryUpdateYoutubeEntityEditText.setText(category);

    }
    private void  updateTheVideoForYouTube(){
        int didInt = Integer.valueOf(did);
        YouTubeEntity youTubeEntity = new YouTubeEntity();
        youTubeEntity.setYid(didInt);
        youTubeEntity.setId(ytid);
        youTubeEntity.setCategory(categoryUpdateYoutubeEntityEditText.getText().toString());
        youTubeEntity.setGenre(genreUpdateYoutubeEntityEditText.getText().toString());
        youTubeEntity.setTitle( titleUpdateYoutubeEntityEditText.getText().toString());
        youTubeUpdateYouTubeViewModel=new ViewModelProvider(this).get(YouTubeUpdateYouTubeViewModel.class);
        youTubeUpdateYouTubeViewModel.updateYoutubeEntity(youTubeEntity);
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_to_favourite_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.save_theVideo:
                updateTheVideoForYouTube();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
