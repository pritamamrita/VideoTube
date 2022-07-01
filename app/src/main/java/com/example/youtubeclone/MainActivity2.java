package com.example.youtubeclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity2 extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent() ;
        String videoID = intent.getStringExtra("vid") ;
        String name = intent.getStringExtra("name") ;
        youTubePlayerView = findViewById(R.id.ytPlayer) ;
        textView = findViewById(R.id.playVideoName) ;
        textView.setText(name);
        youTubePlayerView.initialize("AIzaSyAGKMazc5bagpZbUzCaoBNYDJyWql0guY0", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoID);
                youTubePlayer.play();
                //if(!b)youTubePlayerView.cueVideo(videoID);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

    }
}