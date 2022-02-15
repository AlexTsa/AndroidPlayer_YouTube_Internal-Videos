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
import android.widget.Toast;

import com.alextsatsos.mediayoutubelyrics.Entities.YouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.MyViewModel.YouTubeEntityAddToFavouriteForYouTubeViewModel;

public class AddYouTubeVideoToFavouriteActivity extends AppCompatActivity {
    private EditText titleAddToFavouriteEditText, genreAddToFavouriteEditText, categoryAddToFavouriteEditText;
    private String titleYt;
    private String idYt;
    private YouTubeEntityAddToFavouriteForYouTubeViewModel youTubeEntityAddToFavouriteForYouTubeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_you_tube_video_to_favourite);
        titleAddToFavouriteEditText = findViewById(R.id.titleAddToFavouriteEditText);
        genreAddToFavouriteEditText = findViewById(R.id.genreAddToFavouriteEditText);
        categoryAddToFavouriteEditText = findViewById(R.id.categoryAddToFavouriteEditText);
        titleYt = getIntent().getExtras().getString("titleYouTubeVideoForAddToFavourite");
        idYt = getIntent().getExtras().getString("idYouTubeVideoForAddToFavourite");
        titleAddToFavouriteEditText.setText(titleYt);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Favourites");

    }

    private void saveTheVideoForYouTube() {
        String title, genre, category;
        title = titleAddToFavouriteEditText.getText().toString();
        genre = genreAddToFavouriteEditText.getText().toString();
        category = categoryAddToFavouriteEditText.getText().toString();
        if (genre.trim().isEmpty() || category.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a genre and a category", Toast.LENGTH_SHORT).show();
            return;
        }
        YouTubeEntity youTubeEntity= new YouTubeEntity();
        youTubeEntity.setId(idYt);
        youTubeEntity.setTitle(titleYt);
        youTubeEntity.setGenre(genre);
        youTubeEntity.setCategory(category);
        youTubeEntityAddToFavouriteForYouTubeViewModel =new ViewModelProvider(this).get(YouTubeEntityAddToFavouriteForYouTubeViewModel.class);
        youTubeEntityAddToFavouriteForYouTubeViewModel.insert(youTubeEntity);
        Intent intentf = new Intent();
        setResult(RESULT_OK,intentf);
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
                saveTheVideoForYouTube();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
