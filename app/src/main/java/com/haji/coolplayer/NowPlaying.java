package com.haji.coolplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class NowPlaying extends AppCompatActivity {
    TextView SongName;
    ImageView play, previous, next;
    ArrayList<File> songs;
    MediaPlayer mediaPlayer;
    String textContent;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        SongName = findViewById(R.id.SongNameTextView);
        play = findViewById(R.id.playBTN);
        previous = findViewById(R.id.prevBTN);
        next = findViewById(R.id.nextBTN);

        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        songs = (ArrayList) bundle.getParcelableArrayList("SongList");
        textContent = in.getStringExtra("CurrentSong");
        SongName.setText(textContent);
        position = bundle.getInt("position");
        Uri uri = Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(this,uri);
        mediaPlayer.start();
    }
}