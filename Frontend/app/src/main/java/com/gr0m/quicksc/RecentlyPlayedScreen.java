package com.gr0m.quicksc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecentlyPlayedScreen extends AppCompatActivity
{
    private GridLayout recentlyPlayedGrid;
    private String accessToken; // Store the access token here

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recently_played_screen);

        Button ButtonNavigation = (Button) findViewById(R.id.Navigation);
        Button ButtonNavigation_TopArtists = (Button) findViewById(R.id.Navigation_TopArtists);
        Button ButtonNavigation_ChatScreen = (Button) findViewById(R.id.Navigation_ChatScreen);
        Button ButtonNavigation_Main = (Button) findViewById(R.id.Navigation_Main);
        Button ButtonNavigation_TopTracks = (Button) findViewById(R.id.Navigation_Main);

        ButtonNavigation_TopTracks.setEnabled(false);
        ButtonNavigation_TopTracks.setVisibility(View.INVISIBLE);
        ButtonNavigation_TopArtists.setEnabled(false);
        ButtonNavigation_TopArtists.setVisibility(View.INVISIBLE);
        ButtonNavigation_ChatScreen.setEnabled(false);
        ButtonNavigation_ChatScreen.setVisibility(View.INVISIBLE);
        ButtonNavigation_Main.setEnabled(false);
        ButtonNavigation_Main.setVisibility(View.INVISIBLE);

        ButtonNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (!Util.menu_open) {
                    ButtonNavigation_TopArtists.setEnabled(true);
                    ButtonNavigation_TopArtists.setVisibility(View.VISIBLE);

                    ButtonNavigation_TopTracks.setEnabled(true);
                    ButtonNavigation_TopTracks.setVisibility(View.VISIBLE);

                    ButtonNavigation_Main.setEnabled(true);
                    ButtonNavigation_Main.setVisibility(View.VISIBLE);

                    ButtonNavigation_ChatScreen.setEnabled(true);
                    ButtonNavigation_ChatScreen.setVisibility(View.VISIBLE);
                    Util.menu_open = true;
                    return;

                }
                if (Util.menu_open) {
                    ButtonNavigation_TopArtists.setEnabled(false);
                    ButtonNavigation_TopArtists.setVisibility(View.INVISIBLE);

                    ButtonNavigation_TopTracks.setEnabled(false);
                    ButtonNavigation_TopTracks.setVisibility(View.INVISIBLE);

                    ButtonNavigation_Main.setEnabled(false);
                    ButtonNavigation_Main.setVisibility(View.INVISIBLE);

                    ButtonNavigation_ChatScreen.setEnabled(false);
                    ButtonNavigation_ChatScreen.setVisibility(View.INVISIBLE);
                    Util.menu_open = false;
                    return;

                }


            }

        });
        ButtonNavigation_TopArtists.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RecentlyPlayedScreen.this, TopArtistScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_TopTracks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RecentlyPlayedScreen.this, TopTrackScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_Main.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RecentlyPlayedScreen.this, MainScreen.class);
                startActivity(intent);

            }

        });

        ButtonNavigation_ChatScreen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecentlyPlayedScreen.this, ChatScreen.class);
                startActivity(intent);

            }

        });

        if (Util.service == null) {
            Util.service = new SpotifyServer(8080); // or some default initialization
        }
        if (Util.current_user == null) {
            finish(); // Close the activity if there's no current user
            return;
        }
        // Initialize UI elements
        //trackListLinearLayout = findViewById(R.id.trackListLinearLayout);
        recentlyPlayedGrid = findViewById(R.id.recentlyPlayedGrid);
        // Replace this with the actual access token obtained during authentication
        accessToken = Util.spotify_access_token.access_token;

        API.UserRecentlyPlayed api_user_recently_played = Util.service.SpotifyAPICall("https://api.spotify.com/v1/me/player/recently-played", API.UserRecentlyPlayed.class);

        // Prepare the object to post to the backend database
        API.PostBackendUserRecentlyPlayed database_user_recently_played = new API.PostBackendUserRecentlyPlayed();
        database_user_recently_played.id = Util.GenerateRandomInt();
        database_user_recently_played.spotify = Util.current_user.spotify;
        database_user_recently_played.items = Util.json_converter.toJson(api_user_recently_played.items);

        // Post the recently played tracks to your backend
        Util.MakePostRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/update/" + Util.current_user.spotify + "/recentlyplayed", database_user_recently_played);

        // Retrieve the recently played tracks from your backend
        API.GetBackendUserRecentlyPlayed get_user_recently_played = Util.MakeGetRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/" + Util.current_user.spotify + "/recentlyplayed", API.GetBackendUserRecentlyPlayed.class);

        // Convert the JSON string back to a list of tracks
        List<API.UserRecentlyPlayed.PlayHistoryObject> play_history_objects = Util.json_converter.fromJson(get_user_recently_played.items, new TypeToken<List<API.UserRecentlyPlayed.PlayHistoryObject>>() {}.getType());

        // Populate the grid with the recently played tracks
        for (API.UserRecentlyPlayed.PlayHistoryObject play_history : play_history_objects)
        {
            String track_name = play_history.track.name;
            String album_picture_url = play_history.track.album.images.get(0).url;
            String artist_name = play_history.track.artists.get(0).name;

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

            recentlyPlayedGrid.addView(gridItem);

        }

    }

}


