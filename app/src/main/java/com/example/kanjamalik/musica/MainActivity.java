package com.example.kanjamalik.musica;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        iTuneResponseFetcher fetcher = new iTuneResponseFetcher();
        fetcher.execute();

    }

    private class iTuneResponseFetcher extends AsyncTask<Void, Void, ArrayList<Track>> {

        @Override
        protected ArrayList<Track> doInBackground(Void... params) {
            String requestUrl = "https://itunes.apple.com/search?term=ali+zafar";
            try {
                URL url = new URL(requestUrl);
                InputStream inputStream = url.openStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[2048];
                int bytesRead = 0;
                while ((bytesRead = inputStream.read(buffer)) > 0) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                byteArrayOutputStream.close();
                String response = new String(byteArrayOutputStream.toByteArray());
                ArrayList<Track> tracks = parseJSON(response);
                return tracks;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
        protected void onPostExecute(ArrayList<Track> tracks) {
            super.onPostExecute(tracks);

            TracksAdapter adapter = new TracksAdapter(MainActivity.this, tracks);

            recyclerView.setAdapter(adapter);
        }
    }

    private ArrayList<Track> parseJSON(String response) {
        ArrayList<Track> tracks = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(response);
            JSONArray results = rootObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject trackObject = (JSONObject) results.get(i);
                String trackName = trackObject.getString("trackName");
                String artistName = trackObject.getString("artistName");
                String previewUrl = trackObject.getString("previewUrl");
                String artworkUrl = trackObject.getString("artworkUrl100");

                Track track = new Track();
                track.setvalues(trackName, artistName, previewUrl);
                track.setAlbumArtUrl(artworkUrl);

                tracks.add(track);
                Log.d("Done", "Track Name: " +trackName+ "Artist Name :" +artistName+ "Preview URL :"
                        +previewUrl);

                Log.d("Done","All Result :"+results);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tracks;
    }
}
