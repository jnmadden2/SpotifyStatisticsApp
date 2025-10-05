package com.gr0m.quicksc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MinutesListenedScreen extends AppCompatActivity {

    private GridLayout trackGrid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minutes_listened_screen);


        Button ButtonNavigation = (Button) findViewById(R.id.Navigation);
        Button ButtonNavigation_TopArtists = (Button) findViewById(R.id.Navigation_TopArtists);
        Button ButtonNavigation_ChatScreen = (Button) findViewById(R.id.Navigation_ChatScreen);
        Button ButtonNavigation_TopTracks = (Button) findViewById(R.id.Navigation_TopTracks);
        Button ButtonNavigation_RecentlyPlayed = (Button) findViewById(R.id.Navigation_RecentlyPlayed);
        Button ButtonNavigation_MainScreen = (Button) findViewById(R.id.Navigation_MainScreen);


        ButtonNavigation_TopArtists.setEnabled(false);
        ButtonNavigation_TopArtists.setVisibility(View.INVISIBLE);
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
                    ButtonNavigation_TopArtists.setEnabled(true);
                    ButtonNavigation_TopArtists.setVisibility(View.VISIBLE);

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
                    ButtonNavigation_TopArtists.setEnabled(false);
                    ButtonNavigation_TopArtists.setVisibility(View.INVISIBLE);

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


        ButtonNavigation_TopArtists.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MinutesListenedScreen.this, TopArtistScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_TopTracks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MinutesListenedScreen.this, TopTrackScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_RecentlyPlayed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MinutesListenedScreen.this, RecentlyPlayedScreen.class);
                startActivity(intent);

            }

        });

        ButtonNavigation_ChatScreen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MinutesListenedScreen.this, ChatScreen.class);
                startActivity(intent);

            }

        });

        ButtonNavigation_MainScreen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MinutesListenedScreen.this, MainScreen.class);
                startActivity(intent);

            }

        });




        API.UserFollowedArtists api_user_followed_artists = Util.service.SpotifyAPICall("https://api.spotify.com/v1/me/following?type=artist", API.UserFollowedArtists.class);
        for(int i = 0; i < api_user_followed_artists.artists.items.size(); i++)
        {
            api_user_followed_artists.artists.items.get(i).minutes_listened = Util.CalculateMinutesFromHours(Util.SpotifyUnit);

        }
        API.PostUserFollowedArtists database_user_followed_artists = new API.PostUserFollowedArtists();
        database_user_followed_artists.id = Util.GenerateRandomInt();
        database_user_followed_artists.spotify = Util.current_user.spotify;
        database_user_followed_artists.items = Util.json_converter.toJson(api_user_followed_artists.artists.items);

        Util.MakePostRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/update/" + Util.current_user.spotify + "/minutes", database_user_followed_artists);
        API.GetUserFollowedArtists get_user_top_artists = Util.MakeGetRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/" + Util.current_user.spotify + "/minutes", API.GetUserFollowedArtists.class);

        // convert items into enumerable object array
        List<API.UserFollowedArtists.ArtistObject> artist_object_array = Util.json_converter.fromJson(get_user_top_artists.items, new TypeToken<List<API.UserFollowedArtists.ArtistObject>>() {}.getType());


        trackGrid = findViewById(R.id.trackGrid);


        for (API.UserFollowedArtists.ArtistObject artist : artist_object_array)
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
           trackNameTextView.setText(artist.minutes_listened + " minutes");

            trackGrid.addView(gridItem);

        }

    }
}