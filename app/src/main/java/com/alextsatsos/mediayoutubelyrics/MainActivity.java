package com.alextsatsos.mediayoutubelyrics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private Button youTubeScreenBtn;
private Button myVideosScreenBtn;
private Button reloadBtn;
private ConnectivityManager connectivityManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        youTubeScreenBtn = findViewById(R.id.youtubeScreenBtn);
        myVideosScreenBtn = findViewById(R.id.myVideosScreenBtn);
        reloadBtn=findViewById(R.id.reloadBtn);
        connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        reloadBtn.setVisibility(View.GONE);

        youTubeScreenBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(networkInfo !=null && networkInfo.isConnected()) {

                    Intent intent = new Intent(MainActivity.this, SearchForYouTubeActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "Enable wifi or mobile data", Toast.LENGTH_SHORT).show();
                    youTubeScreenBtn.setVisibility(View.GONE);
                    reloadBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        //Dhmioyrgia enos Click Listener ilopiontas thn onclick methodo
       /*
        youTubeScreenBtn.post(new Runnable() {
            @Override
            public void run() {
                youTubeScreenBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,SearchForYouTubeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        */


        myVideosScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent my videos Screen
                Intent intent = new Intent(MainActivity.this,DisplayLocalVideosActivity.class);
                startActivity(intent);
            }
        });

    }

    public void reloadActivity(View view) {
        reloadBtn.setVisibility(View.GONE);
        finish();
        startActivity(getIntent());
    }
}
