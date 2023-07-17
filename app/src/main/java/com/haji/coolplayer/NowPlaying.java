package com.haji.coolplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class NowPlaying extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        UpdateSeekBar.interrupt();
    }

    SeekBar seekBar;
    TextView SongName;
    ImageView play, previous, next;
    ArrayList<File> songs;
    MediaPlayer mediaPlayer;
    String textContent;
    int position;

    Thread UpdateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        SongName = findViewById(R.id.SongNameTextView);
        play = findViewById(R.id.playpauseBTN);
        previous = findViewById(R.id.prevBTN);
        next = findViewById(R.id.nextBTN);
        seekBar = findViewById(R.id.seekBar);
        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        songs = (ArrayList) bundle.getParcelableArrayList("SongList");
        textContent = in.getStringExtra("CurrentSong");
        SongName.setText(textContent);
        position = bundle.getInt("position");
        Uri uri = Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(this, uri);
        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration());


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        UpdateSeekBar = new Thread() {
            @Override
            public void run() {
                int current_position = 0;
                try {
                    while (current_position < mediaPlayer.getDuration())
                        current_position = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(current_position);
                    sleep(800);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        UpdateSeekBar.start();


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    play.setImageResource(R.drawable.play);

                    mediaPlayer.pause();
                } else {
                    play.setImageResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if (position != 0) {
                    position = position - 1;
                } else {
                    position = songs.size() - 1;
                }
                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
                play.setImageResource(R.drawable.pause);
                textContent= songs.get(position).getName().toString();
                SongName.setText(textContent);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if (position != songs.size() - 1) {
                    position = position + 1;
                } else {
                    position = 0;
                }
                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
                play.setImageResource(R.drawable.pause);
                textContent= songs.get(position).getName().toString();
                SongName.setText(textContent);
            }
        });
    }
}