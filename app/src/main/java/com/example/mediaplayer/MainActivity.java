package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
TextView tvTitle,tvTimePlayed,tvTimeTotal,tvArtist;
ImageButton btnPrev, btnPlay, btnStop,btnNext;
MediaPlayer mediaPlayer;
CircleImageView discImage;
SeekBar sk;
ArrayList<Song> listSongs;
int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhxa();
        addSongs();
        createMediaPlayer();
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play4);
                createMediaPlayer();
                stopAnimation();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play4);
                    stopAnimation();
                }else{
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause1);
                    startAnimation();
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if (position < 0) {
                    position = listSongs.size() - 1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                sk.setProgress(0);
                    createMediaPlayer();
                    mediaPlayer.start();
                    startAnimation();
                    btnPlay.setImageResource(R.drawable.pause1);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discImage.clearAnimation();
                position ++;
                if(position > listSongs.size()-1){
                    position = 0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                sk.setProgress(0);
                createMediaPlayer();
                mediaPlayer.start();
                startAnimation();
                btnPlay.setImageResource(R.drawable.pause1);
            }
        });
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(sk.getProgress());
            }
        });
    }
    private void setTotalTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tvTimeTotal.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
    }
    private void updateTimeSong() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhdanggio = new SimpleDateFormat("mm:ss");
                tvTimePlayed.setText(dinhdanggio.format(mediaPlayer.getCurrentPosition()));
                sk.setProgress(mediaPlayer.getCurrentPosition());

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position ++;
                        if(position > listSongs.size()-1){
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        sk.setProgress(0);
                        createMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause1);
                    }
                });
                handler.postDelayed(this,500);
            }
        },100);

    }
    private void createMediaPlayer() {
        mediaPlayer = MediaPlayer.create(MainActivity.this,listSongs.get(position).getFile());
        tvTitle.setText(listSongs.get(position).getName());
        discImage.setImageResource(listSongs.get(position).getThumb());
        tvArtist.setText(listSongs.get(position).getArtist());

        sk.setMax(mediaPlayer.getDuration());
        setTotalTime();
        updateTimeSong();
    }

    private void addSongs() {
        listSongs = new ArrayList<>();
        listSongs.add(new Song("Ái nộ","Masew x KhoiVu",R.drawable.aino, R.raw.aino));
        listSongs.add(new Song("Điều khác lạ", "Đạt G x Masew",R.drawable.dieukhacla, R.raw.dieukhacla));
        listSongs.add(new Song("Faded","Allan Walker",R.drawable.faded, R.raw.faded));
        listSongs.add(new Song("Save me","Allan Walker",R.drawable.saveme, R.raw.saveme));

    }

    private void anhxa() {

        tvArtist = findViewById(R.id.textViewArtist);
        discImage = findViewById(R.id.disc);
        tvTitle = findViewById(R.id.textViewTitle);
        tvTimePlayed = findViewById(R.id.tvTotalTimePlayed);
        tvTimeTotal = findViewById(R.id.tvtimeTotal);
        btnPrev = findViewById(R.id.imgButtonPrev);
        btnPlay = findViewById(R.id.imgButtonPlay);
        btnStop = findViewById(R.id.imgButtonPause);
        btnNext = findViewById(R.id.imgButtonNext);
        sk = findViewById(R.id.seekbarProgress);

    }
    private void startAnimation(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                discImage.animate().rotationBy(360).withEndAction(this).setDuration(10000).setInterpolator(new LinearInterpolator()).start();
            }
        };
        discImage.animate().rotationBy(360).withEndAction(runnable).setDuration(10000).setInterpolator(new LinearInterpolator()).start();

    }
    private void stopAnimation(){
        discImage.animate().cancel();
    }
}
