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

/**
 * Represents the screen displaying followed artists.
 */
public class FollowedArtistScreen extends AppCompatActivity {

    /**
     * The GridLayout to display followed artists.
     */
    private GridLayout trackGrid;

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
        setContentView(R.layout.followed_artist_screen);

        // Find buttons in the layout
        Button ButtonNavigation = findViewById(R.id.Navigation);
        Button ButtonNavigation_TopArtists = findViewById(R.id.Navigation_TopArtists);
        Button ButtonNavigation_ChatScreen = findViewById(R.id.Navigation_ChatScreen);
        Button ButtonNavigation_TopTracks = findViewById(R.id.Navigation_TopTracks);
        Button ButtonNavigation_RecentlyPlayed = findViewById(R.id.Navigation_RecentlyPlayed);
        Button ButtonNavigation_MainScreen = findViewById(R.id.Navigation_MainScreen);

        // Set initial visibility and enable states for navigation buttons
        setNavigationButtonStates(ButtonNavigation_TopArtists, ButtonNavigation_ChatScreen, ButtonNavigation_TopTracks,
                ButtonNavigation_RecentlyPlayed, ButtonNavigation_MainScreen, false);

        // Set click listener for the main navigation button
        ButtonNavigation.setOnClickListener(this::onNavigationButtonClick);

        // Set click listeners for navigation menu buttons
        ButtonNavigation_TopArtists.setOnClickListener(this::onTopArtistsButtonClick);
        ButtonNavigation_TopTracks.setOnClickListener(this::onTopTracksButtonClick);
        ButtonNavigation_RecentlyPlayed.setOnClickListener(this::onRecentlyPlayedButtonClick);
        ButtonNavigation_ChatScreen.setOnClickListener(this::onChatScreenButtonClick);
        ButtonNavigation_MainScreen.setOnClickListener(this::onMainScreenButtonClick);

        // Call Spotify API to get followed artists
        API.UserFollowedArtists api_user_followed_artists = Util.service.SpotifyAPICall("https://api.spotify.com/v1/me/following?type=artist", API.UserFollowedArtists.class);

        // Prepare data for database update
        API.PostUserFollowedArtists database_user_followed_artists = new API.PostUserFollowedArtists();
        database_user_followed_artists.id = Util.GenerateRandomInt();
        database_user_followed_artists.spotify = Util.current_user.spotify;
        database_user_followed_artists.items = Util.json_converter.toJson(api_user_followed_artists.artists.items);

        // Make a POST request to update user's followed artists in the database
        Util.MakePostRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/update/" + Util.current_user.spotify + "/followedartists", database_user_followed_artists);

        // Make a GET request to retrieve user's followed artists from the database
        API.GetUserFollowedArtists get_user_top_artists = Util.MakeGetRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/" + Util.current_user.spotify + "/followedartists", API.GetUserFollowedArtists.class);

        // Convert items into an enumerable object array
        List<API.UserFollowedArtists.ArtistObject> artist_object_array = Util.json_converter.fromJson(get_user_top_artists.items, new TypeToken<List<API.UserFollowedArtists.ArtistObject>>() {}.getType());

        // Initialize GridLayout for displaying followed artists
        trackGrid = findViewById(R.id.trackGrid);

        // Iterate over followed artists and populate the GridLayout
        for (API.UserFollowedArtists.ArtistObject artist : artist_object_array) {
            String artist_name = artist.name;
            String album_picture_url = artist.images.get(0).url;

            // Inflate grid item layout
            View gridItem = getLayoutInflater().inflate(R.layout.grid_item, null);
            ImageView artistImageView = gridItem.findViewById(R.id.albumCoverImageView);
            TextView trackNameTextView = gridItem.findViewById(R.id.trackNameTextView);
            TextView artistNameTextView = gridItem.findViewById(R.id.artistNameTextView);

            // Load artist image using Picasso library
            Picasso.get()
                    .load(album_picture_url)
                    .resize(300, 300) // adjust this value if necessary
                    .into(artistImageView);

            // Set artist name and empty track name
            artistNameTextView.setText(artist_name);
            trackNameTextView.setText(null);

            // Add the grid item to the GridLayout
            trackGrid.addView(gridItem);
        }
    }

    /**
     * Sets the initial visibility and enable states for navigation buttons.
     *
     * @param buttons Buttons to be configured.
     * @param isVisible Visibility state.
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
            setNavigationButtonStates(ButtonNavigation_TopArtists, ButtonNavigation_TopTracks,
                    ButtonNavigation_RecentlyPlayed, ButtonNavigation_ChatScreen, ButtonNavigation_MainScreen, true);
            Util.menu_open = true;
            return;
        }
        if (Util.menu_open) {
            setNavigationButtonStates(ButtonNavigation_TopArtists, ButtonNavigation_TopTracks,
                    ButtonNavigation_RecentlyPlayed, ButtonNavigation_ChatScreen, ButtonNavigation_MainScreen, false);
            Util.menu_open = false;
        }
    }

    /**
     * Handles the click event for the "Top Artists" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onTopArtistsButtonClick(View view) {
        startActivity(new Intent(FollowedArtistScreen.this, TopArtistScreen.class));
    }

    /**
     * Handles the click event for the "Top Tracks" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onTopTracksButtonClick(View view) {
        startActivity(new Intent(FollowedArtistScreen.this, TopTrackScreen.class));
    }

    /**
     * Handles the click event for the "Recently Played" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onRecentlyPlayedButtonClick(View view) {
        startActivity(new Intent(FollowedArtistScreen.this, RecentlyPlayedScreen.class));
    }

    /**
     * Handles the click event for the "Chat Screen" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onChatScreenButtonClick(View view) {
        startActivity(new Intent(FollowedArtistScreen.this, ChatScreen.class));
    }

    /**
     * Handles the click event for the "Main Screen" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onMainScreenButtonClick(View view) {
        startActivity(new
