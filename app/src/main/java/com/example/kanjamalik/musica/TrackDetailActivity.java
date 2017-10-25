package com.example.kanjamalik.musica;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.commit451.nativestackblur.NativeStackBlur;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kanja Malik on 10/17/2017.
 */

public class TrackDetailActivity extends AppCompatActivity {

    private static final String TAG = "TrackDetailActivity";
    private TextView mArtistName, mTrackName;
    private ImageView mAlbumArt;

    private MediaPlayer mMediaPlayer;
    private ImageView mPlayerPlay, mStopPlayer;
    //private SeekBar mSeekBar;
    private SeekBar mSeekBar;

    public static final String KEY_TRACK = "trackObj";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_detail_new);


        mArtistName = (TextView) findViewById(R.id.tv_artist_name);
        mTrackName = (TextView) findViewById(R.id.tv_track_name);
        mAlbumArt = (ImageView) findViewById(R.id.iv_album_art);
        mPlayerPlay = (ImageView) findViewById(R.id.playerControl);
        mStopPlayer = (ImageView) findViewById(R.id.stopControl);
        mSeekBar = (SeekBar) findViewById(R.id.seek);
        // mSeekBar = (SeekBar) findViewById(R.id.seek);

        Intent intent = getIntent();


        final Track track = (Track) intent.getSerializableExtra(TrackDetailActivity.KEY_TRACK);

        final String trackName = track.getTrackName();
        mTrackName.setText(trackName);
        mArtistName.setText(track.getArtistName());
        Log.d(TAG, "onCreate: " + track.getDuration());


        GlideApp.with(this)
                .asBitmap()
                .load(track.getAlbumArtUrl())
                .centerCrop()
                .listener(requestListener)
                .into(mAlbumArt);


        //Toast.makeText(this, "Track: " + track.getTrackName(), Toast.LENGTH_SHORT).show();

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.d(TAG, "onPrepared: Max Duration: " + mediaPlayer.getDuration());
                togglePlayPause();
                mSeekBar.setProgress(0);
                mSeekBar.setMax(mMediaPlayer.getDuration());

            }
        });


        mPlayerPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mMediaPlayer.isPlaying()) {
                    togglePlayPause();
                    mMediaPlayer.stop();
                    mMediaPlayer.reset();

                } else {
                    togglePlayPause();
                    try {
                        mMediaPlayer.setDataSource(track.getPreviewUrl());
                        mMediaPlayer.prepareAsync();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

        });


    }



    /*public class SeekThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                Log.d("SeekThread", "run: " + 1);
                mSeekBar.setProgress(mMediaPlayer.getDuration());
                Log.d("SeekThread", "run: " + mMediaPlayer.getCurrentPosition());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/


    private void togglePlayPause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            //mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
            mPlayerPlay.setImageResource(R.drawable.ic_action_play);
        } else {
            mMediaPlayer.start();

            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
                }
            }, 0, 100);

            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {

                    if (input) {
                        mMediaPlayer.seekTo(progress);
                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            /*new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
                    Log.d(TAG, "run: " + mMediaPlayer.getCurrentPosition());
                }
            }, 0, 100);

            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                    if (input) {
                        mMediaPlayer.seekTo(progress);
                    }
                    mMediaPlayer.seekTo(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });*/
            mPlayerPlay.setImageResource(R.drawable.ic_pause);

        }
    }


    private RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
            setBlurredImage(resource);
            return false;
        }
    };


    private void setBlurredImage(Bitmap source) {
        Bitmap blurredBitmap = NativeStackBlur.process(source, 50);

        ImageView screenBg = (ImageView) findViewById(R.id.screen_bg);

        screenBg.setImageBitmap(blurredBitmap);
    }


}
