package com.alextsatsos.mediayoutubelyrics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class AddLyricsToLocalVideo extends AppCompatActivity {
private EditText localVideolyricsTextView;
public static  final String Lyrics_Save="com.alextsatsos.mediayoutubelyrics.Lyrics_Save";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lyrics_to_local_video);
        localVideolyricsTextView= findViewById(R.id.localVideolyricsTextView);
        localVideolyricsTextView.setText(getIntent().getExtras().getString("LyricsForSpecificLocalVideoIntent"));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Your Lyrics");
       // saveLyrics();
    }
    private  void  saveLyrics(){
        String lyrics = localVideolyricsTextView.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(Lyrics_Save,lyrics);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_forlocalvideo_lyrics_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.save_theLyrics:
               saveLyrics();
               return true;
               default:
                   return super.onOptionsItemSelected(item);
       }

    }


}
