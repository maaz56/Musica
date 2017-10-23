package com.example.kanjamalik.musica;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Kanja Malik on 10/16/2017.
 */

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TrackHolder> {


    private Context mContext;
    private ArrayList<Track> mTracks;


    public TracksAdapter(Context mContext, ArrayList<Track> mTracks) {
        this.mTracks = mTracks;
        this.mContext = mContext;
    }

    @Override
    public TrackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.track_list_item, parent, false);

        TrackHolder trackHolder = new TrackHolder(view);
        return trackHolder;
    }

    @Override
    public void onBindViewHolder(TrackHolder holder, int position) {

        Track trackAtPosition = mTracks.get(position);

        holder.artistName.setText(trackAtPosition.getArtistName());
        holder.trackName.setText(trackAtPosition.getTrackName());

        GlideApp.with(mContext).load(trackAtPosition.getAlbumArtUrl()).into(holder.albumArt);
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }

    public class TrackHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView albumArt;

        public TextView trackName, artistName;

        public TrackHolder(View itemView) {
            super(itemView);

            albumArt = (ImageView) itemView.findViewById(R.id.album_art);
            trackName = (TextView) itemView.findViewById(R.id.track_name_label);
            artistName = (TextView) itemView.findViewById(R.id.artist_name_label);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Track clickedTrack = mTracks.get(getAdapterPosition());

            Intent detail = new Intent(mContext, TrackDetailActivity.class);

            detail.putExtra(TrackDetailActivity.KEY_TRACK, clickedTrack);

            mContext.startActivity(detail);
        }
    }
}
