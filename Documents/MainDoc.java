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

/**
 * Represents the main screen of the application where users are welcomed and can navigate to other features.
 */
public class MainScreen extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                            shut down, this Bundle contains the data it most recently supplied
     *                            in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main_screen);

        // Initialize UI elements
        Button ButtonNavigation = findViewById(R.id.Navigation);
        Button ButtonNavigation_TopArtists = findViewById(R.id.Navigation_TopArtists);
        Button ButtonNavigation_ChatScreen = findViewById(R.id.Navigation_ChatScreen);
        Button ButtonNavigation_TopTracks = findViewById(R.id.Navigation_TopTracks);
        Button ButtonNavigation_RecentlyPlayed = findViewById(R.id.Navigation_RecentlyPlayed);
        Button ButtonNavigation_FollowedArtists = findViewById(R.id.Navigation_FollowedArtists);

        // Set initial visibility and enable states for navigation buttons
        setNavigationButtonStates(ButtonNavigation_TopArtists, ButtonNavigation_ChatScreen, ButtonNavigation_TopTracks,
                ButtonNavigation_RecentlyPlayed, ButtonNavigation_FollowedArtists);

        // Make Spotify API call, add to the database, and use the database for all user operations afterward
        API.UserProfile api_user = Util.service.SpotifyAPICall("https://api.spotify.com/v1/me", API.UserProfile.class);
        API.PostBackendUserProfile database_user = new API.PostBackendUserProfile();
        database_user.id = Util.GenerateRandomInt();
        database_user.spotify = api_user.external_urls.spotify;
        database_user.displayName = api_user.display_name;
        database_user.pictureURL = api_user.images.get(0).url;
        database_user.email = api_user.email;
        database_user.accessToken = Util.spotify_access_token.access_token;

        String[] splitURL = database_user.spotify.split("/");
        String spotify = splitURL[splitURL.length - 1];
        database_user.spotify = spotify;

        Util.MakePostRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/update/" + database_user.spotify, database_user);
        Util.current_user = Util.MakeGetRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/" + database_user.spotify, API.GetBackendUserProfile.class);

        // Welcome label
        String welcome_message;
        if (Util.current_user == null) welcome_message = "[-] ERROR DETECTED! RESTART APPLICATION!";
        else welcome_message = "Welcome " + Util.current_user.displayName;
        TextView MainScreen_Welcome = findViewById(R.id.MainScreen_Welcome);
        MainScreen_Welcome.setText(welcome_message);

        // Load user avatar picture if available
        if (!Util.current_user.pictureURL.trim().isEmpty()) {
            ImageView MainScreen_Avatar = findViewById(R.id.MainScreen_Avatar);
            Picasso.get()
                    .load(Util.current_user.pictureURL)
                    .resize(300, 300)
                    .into(MainScreen_Avatar);
        }

        // Set click listener for the main navigation button
        ButtonNavigation.setOnClickListener(this::onNavigationButtonClick);

        // Set click listeners for navigation menu buttons
        ButtonNavigation_TopArtists.setOnClickListener(this::onTopArtistsButtonClick);
        ButtonNavigation_TopTracks.setOnClickListener(this::onTopTracksButtonClick);
        ButtonNavigation_RecentlyPlayed.setOnClickListener(this::onRecentlyPlayedButtonClick);
        ButtonNavigation_ChatScreen.setOnClickListener(this::onChatScreenButtonClick);
        ButtonNavigation_FollowedArtists.setOnClickListener(this::onFollowedArtistsButtonClick);
    }

    /**
     * Sets the initial visibility and enable states for navigation buttons.
     *
     * @param buttons Buttons to be configured.
     */
    private void setNavigationButtonStates(Button... buttons) {
        for (Button button : buttons) {
            button.setEnabled(false);
            button.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Handles the click event for the navigation button.
     *
     * @param view The view that was clicked.
     */
    private void onNavigationButtonClick(View view) {
        if (!Util.menu_open) {
            setNavigationButtonStates(ButtonNavigation_TopArtists, ButtonNavigation_ChatScreen, ButtonNavigation_TopTracks,
                    ButtonNavigation_RecentlyPlayed, ButtonNavigation_FollowedArtists);
            Util.menu_open = true;
            return;
        }
        if (Util.menu_open) {
            setNavigationButtonStates(ButtonNavigation_TopArtists, ButtonNavigation_ChatScreen, ButtonNavigation_TopTracks,
                    ButtonNavigation_RecentlyPlayed, ButtonNavigation_FollowedArtists);
            Util.menu_open = false;
        }
    }

    /**
     * Handles the click event for the "Top Artists" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onTopArtistsButtonClick(View view) {
        startActivity(new Intent(MainScreen.this, TopArtistScreen.class));
    }

    /**
     * Handles the click event for the "Top Tracks" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onTopTracksButtonClick(View view) {
        startActivity(new Intent(MainScreen.this, TopTrackScreen.class));
    }

    /**
     * Handles the click event for the "Recently Played" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onRecentlyPlayedButtonClick(View view) {
        startActivity(new Intent(MainScreen.this, RecentlyPlayedScreen.class));
    }

    /**
     * Handles the click event for the "Chat Screen" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onChatScreenButtonClick(View view) {
        startActivity(new Intent(MainScreen.this, ChatScreen.class));
    }

    /**
     * Handles the click event for the "Followed Artists" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onFollowedArtistsButtonClick(View view) {
        startActivity(new Intent(MainScreen.this, FollowedArtistScreen.class));
    }

    /**
     * Called when the activity is about to be destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Called when the activity will start interacting with the user.
     */
    @Override
    public void onResume() {
        super.onResume();
    }
}
