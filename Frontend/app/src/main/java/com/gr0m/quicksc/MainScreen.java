package com.gr0m.quicksc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.squareup.picasso.Picasso;

public class MainScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main_screen);

        Button ButtonNavigation = (Button) findViewById(R.id.Navigation);
        Button ButtonNavigation_TopArtists = (Button) findViewById(R.id.Navigation_TopArtists);
        Button ButtonNavigation_ChatScreen = (Button) findViewById(R.id.Navigation_ChatScreen);
        Button ButtonNavigation_TopTracks = (Button) findViewById(R.id.Navigation_TopTracks);
        Button ButtonNavigation_RecentlyPlayed = (Button) findViewById(R.id.Navigation_RecentlyPlayed);
        Button ButtonNavigation_FollowedArtists = (Button) findViewById(R.id.Navigation_FollowedArtists);
        Button ButtonNavigation_MinutesListened = (Button) findViewById(R.id.Navigation_MinutesListened);
        Button ButtonNavigation_SearchTracks = (Button) findViewById(R.id.Navigation_SearchScreen);
        Button ButtonNavigation_SearchArtists = (Button) findViewById(R.id.Navigation_SearchArtists);
        Button ButtonNavigation_SearchAlbums = (Button) findViewById(R.id.Navigation_SearchAlbums);



        ButtonNavigation_TopArtists.setEnabled(false);
        ButtonNavigation_TopArtists.setVisibility(View.INVISIBLE);
        ButtonNavigation_ChatScreen.setEnabled(false);
        ButtonNavigation_ChatScreen.setVisibility(View.INVISIBLE);
        ButtonNavigation_TopTracks.setEnabled(false);
        ButtonNavigation_TopTracks.setVisibility(View.INVISIBLE);
        ButtonNavigation_RecentlyPlayed.setEnabled(false);
        ButtonNavigation_RecentlyPlayed.setVisibility(View.INVISIBLE);
        ButtonNavigation_FollowedArtists.setEnabled(false);
        ButtonNavigation_FollowedArtists.setVisibility(View.INVISIBLE);
        ButtonNavigation_MinutesListened.setEnabled(false);
        ButtonNavigation_MinutesListened.setVisibility(View.INVISIBLE);
        ButtonNavigation_SearchTracks.setEnabled(false);
        ButtonNavigation_SearchTracks.setVisibility(View.INVISIBLE);
        ButtonNavigation_SearchArtists.setEnabled(false);
        ButtonNavigation_SearchArtists.setVisibility(View.INVISIBLE);
        ButtonNavigation_SearchAlbums.setEnabled(false);
        ButtonNavigation_SearchAlbums.setVisibility(View.INVISIBLE);


        // make spotify api call, add to database, use database for all user operations afterwards
        API.UserProfile api_user = Util.service.SpotifyAPICall("https://api.spotify.com/v1/me", API.UserProfile.class);
        API.PostBackendUserProfile database_user = new API.PostBackendUserProfile();
        database_user.id = Util.GenerateRandomInt();
        database_user.spotify = api_user.external_urls.spotify;
        database_user.displayName = api_user.display_name;
        database_user.pictureURL = api_user.images.get(0).url;
        database_user.email = api_user.email;
        database_user.accessToken = Util.spotify_access_token.access_token;

        String[] splitURL = database_user.spotify.split("/");
        String spotify = splitURL[splitURL.length-1];
        database_user.spotify = spotify;

        Util.MakePostRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/update/" + database_user.spotify, database_user);
        Util.current_user = Util.MakeGetRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/" + database_user.spotify, API.GetBackendUserProfile.class);

        // welcome label
        String welcome_message;
        if (Util.current_user == null) welcome_message = "[-] ERROR DETECTED! RESTART APPLICATION!";
        else welcome_message = "Welcome " + Util.current_user.displayName;
        TextView MainScreen_Welcome = (TextView)findViewById(R.id.MainScreen_Welcome);
        MainScreen_Welcome.setText(welcome_message);

        if (!Util.current_user.pictureURL.trim().isEmpty())
        {
            //user avatar picture
            ImageView MainScreen_Avatar = (ImageView) findViewById(R.id.MainScreen_Avatar);
            Picasso.get()
                    .load(Util.current_user.pictureURL)
                    .resize(300, 300)
                    .into(MainScreen_Avatar);

        }
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

                    ButtonNavigation_FollowedArtists.setEnabled(true);
                    ButtonNavigation_FollowedArtists.setVisibility(View.VISIBLE);

                    ButtonNavigation_MinutesListened.setEnabled(true);
                    ButtonNavigation_MinutesListened.setVisibility(View.VISIBLE);

                    ButtonNavigation_SearchTracks.setEnabled(true);
                    ButtonNavigation_SearchTracks.setVisibility(View.VISIBLE);

                    ButtonNavigation_SearchArtists.setEnabled(true);
                    ButtonNavigation_SearchArtists.setVisibility(View.VISIBLE);

                    ButtonNavigation_SearchAlbums.setEnabled(true);
                    ButtonNavigation_SearchAlbums.setVisibility(View.VISIBLE);

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

                    ButtonNavigation_FollowedArtists.setEnabled(false);
                    ButtonNavigation_FollowedArtists.setVisibility(View.INVISIBLE);

                    ButtonNavigation_MinutesListened.setEnabled(false);
                    ButtonNavigation_MinutesListened.setVisibility(View.INVISIBLE);

                    ButtonNavigation_SearchTracks.setEnabled(false);
                    ButtonNavigation_SearchTracks.setVisibility(View.INVISIBLE);

                    ButtonNavigation_SearchArtists.setEnabled(false);
                    ButtonNavigation_SearchArtists.setVisibility(View.INVISIBLE);

                    ButtonNavigation_SearchAlbums.setEnabled(false);
                    ButtonNavigation_SearchAlbums.setVisibility(View.INVISIBLE);

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
                Intent intent = new Intent(MainScreen.this, TopArtistScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_TopTracks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, TopTrackScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_RecentlyPlayed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, RecentlyPlayedScreen.class);
                startActivity(intent);

            }

        });

        ButtonNavigation_ChatScreen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, ChatScreen.class);
                startActivity(intent);

            }

        });

        ButtonNavigation_FollowedArtists.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, FollowedArtistScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_MinutesListened.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, MinutesListenedScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_SearchTracks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, SearchScreen.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_SearchArtists.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, SearchArtists.class);
                startActivity(intent);

            }

        });
        ButtonNavigation_SearchAlbums.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainScreen.this, SearchAlbums.class);
                startActivity(intent);

            }

        });

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }
    @Override
    public void onResume()
    {
        super.onResume();

    }

}
