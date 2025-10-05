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

/**
 * TopTrackScreen is an Android activity that showcases the top tracks of a user.
 * It extends AppCompatActivity and is part of the com.gr0m.quicksc package.
 *
 * The activity displays the top tracks in a GridLayout, with each track having its name,
 * album cover, and artist name. The screen includes navigation buttons to other parts of the app.
 */
public class TopTrackScreen extends AppCompatActivity {

    /**
     * GridLayout to display the top tracks in a grid format.
     */
    private GridLayout trackGrid;

    /**
     * String to store the Spotify access token.
     */
    private String accessToken;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_tracks_screen);

        // Initialize UI elements and set initial visibility and enable states for navigation buttons

        Button ButtonNavigation = (Button) findViewById(R.id.Navigation);
        Button ButtonNavigation_TopArtists = (Button) findViewById(R.id.Navigation_TopArtists);
        Button ButtonNavigation_ChatScreen = (Button) findViewById(R.id.Navigation_ChatScreen);
        Button ButtonNavigation_Main = (Button) findViewById(R.id.Navigation_Main);
        Button ButtonNavigation_RecentlyPlayed = (Button) findViewById(R.id.Navigation_RecentlyPlayed);
        Button ButtonNavigation_FollowedArtists = (Button) findViewById(R.id.Navigation_FollowedArtists);


        ButtonNavigation_TopArtists.setEnabled(false);
        ButtonNavigation_TopArtists.setVisibility(View.INVISIBLE);
        ButtonNavigation_ChatScreen.setEnabled(false);
        ButtonNavigation_ChatScreen.setVisibility(View.INVISIBLE);
        ButtonNavigation_Main.setEnabled(false);
        ButtonNavigation_Main.setVisibility(View.INVISIBLE);
        ButtonNavigation_RecentlyPlayed.setEnabled(false);
        ButtonNavigation_RecentlyPlayed.setVisibility(View.INVISIBLE);
        ButtonNavigation_FollowedArtists.setEnabled(false);
        ButtonNavigation_FollowedArtists.setVisibility(View.INVISIBLE);

        // Set click listener for the main navigation button

        ButtonNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Util.menu_open) {
                    ButtonNavigation_TopArtists.setEnabled(true);
                    ButtonNavigation_TopArtists.setVisibility(View.VISIBLE);

                    ButtonNavigation_Main.setEnabled(true);
                    ButtonNavigation_Main.setVisibility(View.VISIBLE);

                    ButtonNavigation_RecentlyPlayed.setEnabled(true);
                    ButtonNavigation_RecentlyPlayed.setVisibility(View.VISIBLE);

                    ButtonNavigation_FollowedArtists.setEnabled(true);
                    ButtonNavigation_FollowedArtists.setVisibility(View.VISIBLE);

                    ButtonNavigation_ChatScreen.setEnabled(true);
                    ButtonNavigation_ChatScreen.setVisibility(View.VISIBLE);
                    Util.menu_open = true;
                    return;

                }
                if (Util.menu_open) {
                    ButtonNavigation_TopArtists.setEnabled(false);
                    ButtonNavigation_TopArtists.setVisibility(View.INVISIBLE);

                    ButtonNavigation_RecentlyPlayed.setEnabled(false);
                    ButtonNavigation_RecentlyPlayed.setVisibility(View.INVISIBLE);

                    ButtonNavigation_Main.setEnabled(false);
                    ButtonNavigation_Main.setVisibility(View.INVISIBLE);

                    ButtonNavigation_FollowedArtists.setEnabled(true);
                    ButtonNavigation_FollowedArtists.setVisibility(View.VISIBLE);

                    ButtonNavigation_ChatScreen.setEnabled(false);
                    ButtonNavigation_ChatScreen.setVisibility(View.INVISIBLE);
                    Util.menu_open = false;
                    return;

                }


            }

        });
        ButtonNavigation_TopArtists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopTrackScreen.this, TopArtistScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_FollowedArtists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopTrackScreen.this, FollowedArtistScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_RecentlyPlayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopTrackScreen.this, RecentlyPlayedScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopTrackScreen.this, MainScreen.class);
                startActivity(intent);

            }

        });

        ButtonNavigation_ChatScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopTrackScreen.this, ChatScreen.class);
                startActivity(intent);

            }

        });
        // Initialize UI elements
        //trackListLinearLayout = findViewById(R.id.trackListLinearLayout);
        trackGrid = findViewById(R.id.trackGrid);
        // Replace this with the actual access token obtained during authentication
        accessToken = Util.spotify_access_token.access_token;


        // Make Spotify API call, add to the database, and use the database for all user operations afterward

        API.UserTopTracks api_user_top_tracks = Util.service.SpotifyAPICall("https://api.spotify.com/v1/me/top/tracks", API.UserTopTracks.class);
        API.PostBackendUserTopTracks database_user_top_tracks = new API.PostBackendUserTopTracks();

        database_user_top_tracks.id = Util.GenerateRandomInt();
        database_user_top_tracks.spotify = Util.current_user.spotify;
        database_user_top_tracks.items = Util.json_converter.toJson(api_user_top_tracks.items);



        Util.MakePostRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/update/" + Util.current_user.spotify + "/topsongs", database_user_top_tracks);

        API.GetBackendUserTopTracks get_user_top_tracks = Util.MakeGetRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/" + Util.current_user.spotify + "/topsongs", API.GetBackendUserTopTracks.class);

        // Convert items into an enumerable object array

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

            // Populate the track grid

            trackGrid.addView(gridItem);

        }



    }


}

