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

/**
 * Represents the screen displaying the user's top artists.
 */
public class TopArtistScreen extends AppCompatActivity {

    private GridLayout trackGrid;

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
        setContentView(R.layout.top_artist_screen);

        // Initialize UI elements
        Button ButtonNavigation = findViewById(R.id.Navigation);
        Button ButtonNavigation_FollowedArtists = findViewById(R.id.Navigation_FollowedArtists);
        Button ButtonNavigation_ChatScreen = findViewById(R.id.Navigation_ChatScreen);
        Button ButtonNavigation_TopTracks = findViewById(R.id.Navigation_TopTracks);
        Button ButtonNavigation_RecentlyPlayed = findViewById(R.id.Navigation_RecentlyPlayed);
        Button ButtonNavigation_MainScreen = findViewById(R.id.Navigation_MainScreen);

        // Set initial visibility and enable states for navigation buttons
        setNavigationButtonStates(ButtonNavigation_FollowedArtists, ButtonNavigation_ChatScreen, ButtonNavigation_TopTracks,
                ButtonNavigation_RecentlyPlayed, ButtonNavigation_MainScreen);

        // Set click listener for the main navigation button
        ButtonNavigation.setOnClickListener(this::onNavigationButtonClick);

        // Set click listeners for navigation menu buttons
        ButtonNavigation_FollowedArtists.setOnClickListener(this::onFollowedArtistsButtonClick);
        ButtonNavigation_TopTracks.setOnClickListener(this::onTopTracksButtonClick);
        ButtonNavigation_RecentlyPlayed.setOnClickListener(this::onRecentlyPlayedButtonClick);
        ButtonNavigation_ChatScreen.setOnClickListener(this::onChatScreenButtonClick);
        ButtonNavigation_MainScreen.setOnClickListener(this::onMainScreenButtonClick);

        // Make Spotify API call, add to the database, and use the database for all user operations afterward
        API.UserTopArtists api_user_top_artists = Util.service.SpotifyAPICall("https://api.spotify.com/v1/me/top/artists", API.UserTopArtists.class);
        API.PostBackendUserTopArtists database_user_top_artists = new API.PostBackendUserTopArtists();
        database_user_top_artists.id = Util.GenerateRandomInt();
        database_user_top_artists.spotify = Util.current_user.spotify;
        database_user_top_artists.items = Util.json_converter.toJson(api_user_top_artists.items);

        Util.MakePostRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/update/" + Util.current_user.spotify + "/topartists", database_user_top_artists);
        API.GetBackendUserTopArtists get_user_top_artists = Util.MakeGetRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/" + Util.current_user.spotify + "/topartists", API.GetBackendUserTopArtists.class);

        // Convert items into an enumerable object array
        List<API.UserTopArtists.ArtistObject> artist_object_array = Util.json_converter.fromJson(get_user_top_artists.items, new TypeToken<List<API.UserTopArtists.ArtistObject>>() {}.getType());

        // Initialize and populate the track grid
        trackGrid = findViewById(R.id.trackGrid);
        populateTrackGrid(artist_object_array);
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
            setNavigationButtonStates(ButtonNavigation_FollowedArtists, ButtonNavigation_ChatScreen, ButtonNavigation_TopTracks,
                    ButtonNavigation_RecentlyPlayed, ButtonNavigation_MainScreen);
            Util.menu_open = true;
            return;
        }
        if (Util.menu_open) {
            setNavigationButtonStates(ButtonNavigation_FollowedArtists, ButtonNavigation_ChatScreen, ButtonNavigation_TopTracks,
                    ButtonNavigation_RecentlyPlayed, ButtonNavigation_MainScreen);
            Util.menu_open = false;
        }
    }

    /**
     * Handles the click event for the "Followed Artists" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onFollowedArtistsButtonClick(View view) {
        startActivity(new Intent(TopArtistScreen.this, FollowedArtistScreen.class));
    }

    /**
     * Handles the click event for the "Top Tracks" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onTopTracksButtonClick(View view) {
        startActivity(new Intent(TopArtistScreen.this, TopTrackScreen.class));
    }

    /**
     * Handles the click event for the "Recently Played" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onRecentlyPlayedButtonClick(View view) {
        startActivity(new Intent(TopArtistScreen.this, RecentlyPlayedScreen.class));
    }

    /**
     * Handles the click event for the "Chat Screen" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onChatScreenButtonClick(View view) {
        startActivity(new Intent(TopArtistScreen.this, ChatScreen.class));
    }

    /**
     * Handles the click event for the "Main Screen" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onMainScreenButtonClick(View view) {
        startActivity(new Intent(TopArtistScreen.this, MainScreen.class));
    }

    /**
     * Populates the track grid with the provided list of artist objects.
     *
     * @param artistObjectArray List of artist objects.
     */
    private void populateTrackGrid(List<API.UserTopArtists.ArtistObject> artistObjectArray) {
        for (API.UserTopArtists.ArtistObject artist : artistObjectArray) {
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

