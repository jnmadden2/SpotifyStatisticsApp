package com.gr0m.quicksc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TopTrackScreen extends AppCompatActivity {

    private GridLayout trackGrid;
    private String accessToken; // Store the access token here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_tracks_screen);

        if (Util.service == null) {
            Util.service = new SpotifyServer(8080); // or some default initialization
        }
        Button ButtonNavigation = (Button) findViewById(R.id.Navigation);
        ButtonNavigation.setOnClickListener(view -> {
            Intent intent = new Intent(TopTrackScreen.this, MainScreen.class);
            startActivity(intent);
        });


        if (Util.current_user == null) {
            // Handle the case where the user is not set. For example:
            finish(); // Close the activity
            return;
        }
        // Initialize UI elements
        //trackListLinearLayout = findViewById(R.id.trackListLinearLayout);
        trackGrid = findViewById(R.id.trackGrid);
        // Replace this with the actual access token obtained during authentication
        accessToken = Util.spotify_access_token.access_token;



        API.UserTopTracks api_user_top_tracks = Util.service.SpotifyAPICall("https://api.spotify.com/v1/me/top/tracks", API.UserTopTracks.class);
        API.PostBackendUserTopTracks database_user_top_tracks = new API.PostBackendUserTopTracks();

        database_user_top_tracks.id = Util.GenerateRandomInt();
        database_user_top_tracks.spotify = Util.current_user.spotify;
        database_user_top_tracks.items = Util.json_converter.toJson(api_user_top_tracks.items);



        Util.MakePostRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/update/" + Util.current_user.spotify + "/topsongs", database_user_top_tracks);

        API.GetBackendUserTopTracks get_user_top_tracks = Util.MakeGetRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/" + Util.current_user.spotify + "/topsongs", API.GetBackendUserTopTracks.class);


        List<API.UserTopTracks.Track> track_object_array = Util.json_converter.fromJson(get_user_top_tracks.items, new TypeToken<List<API.UserTopTracks.Track>>() {}.getType());

        for (API.UserTopTracks.Track track : track_object_array)
        {
            String track_name = track.name;
            String album_picture_url = track.album.images.get(0).url;
            String artist_name = track.artists.get(0).name;

            View gridItem = getLayoutInflater().inflate(R.layout.grid_item, null);
            ImageView albumCoverImageView = gridItem.findViewById(R.id.albumCoverImageView);
            TextView trackNameTextView = gridItem.findViewById(R.id.trackNameTextView);
            TextView artistNameTextView = gridItem.findViewById(R.id.artistNameTextView);


            Picasso.get()
                    .load(album_picture_url)
                    .resize(300, 300) // adjust this value if necessary
                    .into(albumCoverImageView);

            trackNameTextView.setText(track_name);
            artistNameTextView.setText(artist_name);

            trackGrid.addView(gridItem);

        }



    }


    }

