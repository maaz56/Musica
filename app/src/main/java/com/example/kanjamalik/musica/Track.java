package com.example.kanjamalik.musica;

import java.io.Serializable;

/**
 * Created by Kanja Malik on 10/16/2017.
 */

public class Track implements Serializable {


    private String trackName;
    private String artistName;
    private String previewUrl;

    private String albumArtUrl;
    private String duration;

    public void setvalues(String t_Name, String ta_name, String tp_url, String t_duration) {
        trackName = t_Name;
        artistName = ta_name;
        previewUrl = tp_url;
        duration = t_duration;
    }

    public String getDuration() {
        return duration;
    }


    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getAlbumArtUrl() {
        return albumArtUrl;
    }

   /* public String getStreamURL() {
        return stremUrl;
    }*/

    public void setAlbumArtUrl(String albumArtUrl) {
        this.albumArtUrl = albumArtUrl;
    }
}
