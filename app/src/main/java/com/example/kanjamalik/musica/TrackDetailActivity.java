package com.example.kanjamalik.musica;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.commit451.nativestackblur.NativeStackBlur;
import com.cuboid.cuboidcirclebutton.CuboidButton;

import java.io.IOException;

/**
 * Created by Kanja Malik on 10/17/2017.
 */

public class TrackDetailActivity extends AppCompatActivity {

    private TextView mArtistName, mTrackName;
    private ImageView mAlbumArt;

    private MediaPlayer mMediaPlayer;
    private ImageView mPlayerPlay, mStopPlayer;


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
        Intent intent = getIntent();


        final Track track = (Track) intent.getSerializableExtra(TrackDetailActivity.KEY_TRACK);

        String trackName = track.getTrackName();
        mTrackName.setText(trackName);
        mArtistName.setText(track.getArtistName());

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
                togglePlayPause();
            }
        });




        mPlayerPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaPlayer.isPlaying()) {
                    togglePlayPause();

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


       /* mPlayerPlay.setOnClickListener(new View.OnClickListener() {
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

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }


            }
        });
    }*/



  /*  private void togglePlayPause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mPlayerPlay.setImageResource(R.drawable.ic_action_play);
        } else {
            mMediaPlayer.start();
            mPlayerPlay.setImageResource(R.drawable.ic_pause);
        }*/


    }

    private void toggleStopStart(){
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
            mMediaPlayer.reset();
        }
    }

    private void togglePlayPause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mPlayerPlay.setImageResource(R.drawable.ic_action_play);
        } else {
            mMediaPlayer.start();
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
