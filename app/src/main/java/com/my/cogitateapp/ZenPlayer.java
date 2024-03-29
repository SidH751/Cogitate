package com.my.cogitateapp;

import androidx.appcompat.app.AppCompatActivity;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.my.cogitateapp.R;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ZenPlayer extends AppCompatActivity {
    Button btnPlay, btnNext, btnPrevious, btnFastForward, btnFastBackWard;
    TextView txtSongName, txtSongStart, txtSongEnd;
    SeekBar seekMusicBar;
    ImageView imageView;
//    int Songs[]={R.raw.forestlullaby,R.raw.mindfulnessrelaxationmeditation22174,R.raw.underwaterformeditationbyob14278};





    String songName;
    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;

    ArrayList<Integer> mySongs;
    ArrayList<Integer> mArraylist;

    Thread updateSeekBar;
    private Object view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zen_player);

        btnPlay = (Button) findViewById(R.id.BtnPlay);
        btnNext = (Button) findViewById(R.id.BtnNext);
        btnPrevious = (Button) findViewById(R.id.BtnPrevious);
        btnFastForward = (Button) findViewById(R.id.BtnFastForward);
        btnFastBackWard = (Button) findViewById(R.id.BtnFastRewind);
        seekMusicBar = (SeekBar) findViewById(R.id.SeekBar);
        mArraylist=new ArrayList<Integer>();
        mySongs=new ArrayList<Integer>();


        txtSongName = (TextView) findViewById(R.id.SongTxt);
        txtSongStart = (TextView) findViewById(R.id.TxtSongStart);
        txtSongEnd = (TextView) findViewById(R.id.TxtSongEnd);
        imageView = (ImageView) findViewById(R.id.MusicImage);

        if (mediaPlayer != null) {

            //we will start mediaPlayer if currently there is no songs in it
            mediaPlayer.start();
            mediaPlayer.release();
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mySongs.add(R.raw.forestlullaby);
        mySongs.add(R.raw.mindfulnessrelaxationmeditation22174);
        mySongs.add(R.raw.underwaterformeditationbyob14278);


        Uri uri = Uri.parse(mySongs.get(0).toString());
        position=0;







//        mySongs = (ArrayList) bundle.getIntegerArrayList("songs");
//        String sName = intent.getStringExtra("songname");
//        position = bundle.getInt("pos");
//        txtSongName.setSelected(true);

//        Uri uri = Uri.parse(Songs.get(position).toString());
//        songName = mySongs.get(position).getName();
//        txtSongName.setText(songName);

        mediaPlayer=MediaPlayer.create(this,mySongs.get(position%mySongs.size()));
//        mediaPlayer.start();
        songEndTime();
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Checking playing any songs or not
                if (mediaPlayer.isPlaying()) {

                    //setting the play icon
                    btnPlay.setBackgroundResource(R.drawable.play_song_icon);

                    //Pausing the current media
                    mediaPlayer.pause();

                } else {

                    //Setting the pause icon
                    btnPlay.setBackgroundResource(R.drawable.pause_song_icon);

                    //Starting the media player
                    mediaPlayer.start();

                    //Creating the Animation
                    TranslateAnimation moveAnim = new TranslateAnimation(-25, 25, -25, 25);
                    moveAnim.setInterpolator(new AccelerateInterpolator());
                    moveAnim.setDuration(600);
                    moveAnim.setFillEnabled(true);
                    moveAnim.setFillAfter(true);
                    moveAnim.setRepeatMode(Animation.REVERSE);
                    moveAnim.setRepeatCount(1);

                    //Setting the Animation for the Image
                    imageView.startAnimation(moveAnim);

                    //Calling the BarVisualizer

                }
            }
        });

        updateSeekBar = new Thread() {
            @Override
            public void run() {

                int TotalDuration = mediaPlayer.getDuration();
                int CurrentPosition = 0;

                while (CurrentPosition < TotalDuration) {
                    try {

                        sleep(500);
                        CurrentPosition = mediaPlayer.getCurrentPosition();
                        seekMusicBar.setProgress(CurrentPosition);

                    } catch (InterruptedException | IllegalStateException e) {

                        e.printStackTrace();
                    }
                }

            }
        };

        seekMusicBar.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();

        seekMusicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                //getting the progress of the seek bar and setting it to Media Player
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //Getting the current duration from the media player
                String currentTime = createDuration(mediaPlayer.getCurrentPosition());

                //Setting the current duration in textView
                txtSongStart.setText(currentTime);
                handler.postDelayed(this, delay);

            }
        }, delay);

        btnFastForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {

                    //Getting the current position and adding 10sec to it
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);

                }
            }
        });


        //Implementing the FastBackWard
        btnFastBackWard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {

                    //Getting the curent Position of the song and decrease 10sec from it
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);

                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Stoping the currently playing media
                mediaPlayer.stop();
                mediaPlayer.release();


                //Getting the Current media position and incrementing it by 1
//                position = ((position + 1) % mySongs.size());

                //Extracting the media path form the array List
                position=(position+1)%mySongs.size();
//                Uri uri1 = Uri.parse(mySongs.get(position).toString());


                //Setting the path to the media player
                mediaPlayer = MediaPlayer.create(getApplicationContext(), mySongs.get(position));


                //Getting the current song Name and setting it to TextView
//                songName = mySongs.get(position).getName();
//                txtSongName.setText(songName);

                //Starting the Media Player
                mediaPlayer.start();

                //Extracting the duration of the song
                songEndTime();


                //Animating the ImageView
                startAnimation(imageView, 360f);
//                position++;




            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Stoping the media Player
                mediaPlayer.stop();
                mediaPlayer.release();


                //getting the  current media position and decrementing it by one
                position = ((position - 1) % mySongs.size());
                if (position < 0)
                    position = mySongs.size() - 1;

                //Extracting the media path
                Uri uri1 = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), mySongs.get(position));

                //Setting the media path to the media player
//                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri1);
//                songName = mySongs.get(position).getName();
//                txtSongName.setText(songName);
                mediaPlayer.start();
                songEndTime();


                //Animating the imageView
                startAnimation(imageView, -360f);


            }

        });





    }
    public void startAnimation(View view, Float degree) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, degree);
        objectAnimator.setDuration(1000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator);
        animatorSet.start();

    }

    public String createDuration(int duration) {

        String time = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        time = time + min + ":";

        if (sec < 10) {

            time += "0";

        }
        time += sec;
        return time;

    }

    public void songEndTime() {
        String endTime = createDuration(mediaPlayer.getDuration());
        txtSongEnd.setText(endTime);
    }


}