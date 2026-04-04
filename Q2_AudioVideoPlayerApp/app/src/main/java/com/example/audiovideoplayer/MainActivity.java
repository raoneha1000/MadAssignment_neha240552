package com.example.audiovideoplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnOpenFile, btnOpenUrl, btnPlay, btnPause, btnStop, btnRestart;
    EditText urlInput;
    VideoView videoView;

    MediaPlayer mediaPlayer;
    boolean isAudio = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenFile = findViewById(R.id.btnOpenFile);
        btnOpenUrl = findViewById(R.id.btnOpenUrl);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        btnRestart = findViewById(R.id.btnRestart);

        urlInput = findViewById(R.id.urlInput);
        videoView = findViewById(R.id.videoView);

        // Open Audio File (from raw folder)
        btnOpenFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sample_audio);
                    isAudio = true;
                    Toast.makeText(MainActivity.this, "Audio Loaded", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error loading audio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Open Video URL
        btnOpenUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlInput.getText().toString();

                if (url.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter URL first", Toast.LENGTH_SHORT).show();
                } else {
                    videoView.setVideoURI(Uri.parse(url));
                    isAudio = false;
                    Toast.makeText(MainActivity.this, "Video Loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Play
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isAudio && mediaPlayer != null) {
                        mediaPlayer.start();
                    } else {
                        videoView.start();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Cannot Play", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Pause
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAudio && mediaPlayer != null) {
                    mediaPlayer.pause();
                } else {
                    videoView.pause();
                }
            }
        });

        // Stop
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAudio && mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                } else {
                    videoView.stopPlayback();
                }
            }
        });

        // Restart
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAudio && mediaPlayer != null) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                } else {
                    videoView.seekTo(0);
                    videoView.start();
                }
            }
        });
    }
}