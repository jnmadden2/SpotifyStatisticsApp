package com.gr0m.quicksc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class TopArtistScreen extends AppCompatActivity
{
    private GridLayout trackGrid;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_artist_screen);

        if (Util.service == null) {
            Util.service = new SpotifyServer(8080); // or some default initialization
        }


        Button ButtonNavigation = (Button) findViewById(R.id.Navigation);
        Button ButtonNavigation_FollowedArtists = (Button) findViewById(R.id.Navigation_FollowedArtists);
        Button ButtonNavigation_ChatScreen = (Button) findViewById(R.id.Navigation_ChatScreen);
        Button ButtonNavigation_TopTracks = (Button) findViewById(R.id.Navigation_TopTracks);
        Button ButtonNavigation_RecentlyPlayed = (Button) findViewById(R.id.Navigation_RecentlyPlayed);
        Button ButtonNavigation_MainScreen = (Button) findViewById(R.id.Navigation_MainScreen);


        ButtonNavigation_FollowedArtists.setEnabled(false);
        ButtonNavigation_FollowedArtists.setVisibility(View.INVISIBLE);
        ButtonNavigation_ChatScreen.setEnabled(false);
        ButtonNavigation_ChatScreen.setVisibility(View.INVISIBLE);
        ButtonNavigation_TopTracks.setEnabled(false);
        ButtonNavigation_TopTracks.setVisibility(View.INVISIBLE);
        ButtonNavigation_RecentlyPlayed.setEnabled(false);
        ButtonNavigation_RecentlyPlayed.setVisibility(View.INVISIBLE);
        ButtonNavigation_MainScreen.setEnabled(false);
        ButtonNavigation_MainScreen.setVisibility(View.INVISIBLE);


        ButtonNavigation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!Util.menu_open)
                {
                    ButtonNavigation_FollowedArtists.setEnabled(true);
                    ButtonNavigation_FollowedArtists.setVisibility(View.VISIBLE);

                    ButtonNavigation_TopTracks.setEnabled(true);
                    ButtonNavigation_TopTracks.setVisibility(View.VISIBLE);

                    ButtonNavigation_RecentlyPlayed.setEnabled(true);
                    ButtonNavigation_RecentlyPlayed.setVisibility(View.VISIBLE);

                    ButtonNavigation_ChatScreen.setEnabled(true);
                    ButtonNavigation_ChatScreen.setVisibility(View.VISIBLE);

                    ButtonNavigation_MainScreen.setEnabled(true);
                    ButtonNavigation_MainScreen.setVisibility(View.VISIBLE);
                    Util.menu_open = true;
                    return;

                }
                if (Util.menu_open)
                {
                    ButtonNavigation_FollowedArtists.setEnabled(false);
                    ButtonNavigation_FollowedArtists.setVisibility(View.INVISIBLE);

                    ButtonNavigation_TopTracks.setEnabled(false);
                    ButtonNavigation_TopTracks.setVisibility(View.INVISIBLE);

                    ButtonNavigation_RecentlyPlayed.setEnabled(false);
                    ButtonNavigation_RecentlyPlayed.setVisibility(View.INVISIBLE);

                    ButtonNavigation_ChatScreen.setEnabled(false);
                    ButtonNavigation_ChatScreen.setVisibility(View.INVISIBLE);

                    ButtonNavigation_MainScreen.setEnabled(false);
                    ButtonNavigation_MainScreen.setVisibility(View.INVISIBLE);
                    Util.menu_open = false;
                    return;

                }

            }

        });


        ButtonNavigation_FollowedArtists.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(TopArtistScreen.this, FollowedArtistScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_TopTracks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(TopArtistScreen.this, TopTrackScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_RecentlyPlayed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(TopArtistScreen.this, RecentlyPlayedScreen.class);
                startActivity(intent);

            }

        });

        ButtonNavigation_ChatScreen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(TopArtistScreen.this, ChatScreen.class);
                startActivity(intent);

            }

        });

        ButtonNavigation_MainScreen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(TopArtistScreen.this, MainScreen.class);
                startActivity(intent);

            }

        });


        if (Util.current_user == null) {
            // Handle the case where the user is not set. For example:
            finish(); // Close the activity
            return;
        }

        API.UserTopArtists api_user_top_artists = Util.service.SpotifyAPICall("https://api.spotify.com/v1/me/top/artists", API.UserTopArtists.class);
        API.PostBackendUserTopArtists database_user_top_artists = new API.PostBackendUserTopArtists();
        database_user_top_artists.id = Util.GenerateRandomInt();
        database_user_top_artists.spotify = Util.current_user.spotify;
        database_user_top_artists.items = Util.json_converter.toJson(api_user_top_artists.items);

        Util.MakePostRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/update/" + Util.current_user.spotify + "/topartists", database_user_top_artists);
        API.GetBackendUserTopArtists get_user_top_artists = Util.MakeGetRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/" + Util.current_user.spotify + "/topartists", API.GetBackendUserTopArtists.class);

        // convert items into enumerable object array
        List<API.UserTopArtists.ArtistObject> artist_object_array = Util.json_converter.fromJson(get_user_top_artists.items, new TypeToken<List<API.UserTopArtists.ArtistObject>>() {}.getType());



        trackGrid = findViewById(R.id.trackGrid);


        for (API.UserTopArtists.ArtistObject artist : artist_object_array)
        {

            String artist_name = artist.name;
            String album_picture_url = artist.images.get(0).url;

            View gridItem = getLayoutInflater().inflate(R.layout.grid_item, null);
            ImageView artistImageView = gridItem.findViewById(R.id.albumCoverImageView);
            TextView trackNameTextView = gridItem.findViewById(R.id.trackNameTextView);
            TextView artistNameTextView = gridItem.findViewById(R.id.artistNameTextView);


            Picasso.get()
                    .load(album_picture_url)
                    .resize(300, 300) // adjust this value if necessary
                    .into(artistImageView);

            artistNameTextView.setText(artist_name);
            trackNameTextView.setText(null);

            trackGrid.addView(gridItem);

        }

    }

}
