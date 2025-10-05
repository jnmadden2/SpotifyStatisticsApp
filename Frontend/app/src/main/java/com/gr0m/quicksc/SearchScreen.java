package com.gr0m.quicksc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import okhttp3.*;
import java.io.IOException;
import java.util.List;

public class SearchScreen extends AppCompatActivity {

    private LinearLayout searchResultsLayout;
    private EditText searchEditText;
    private Button searchButton;
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);
        if (Util.service == null) {
            Util.service = new SpotifyServer(8080); // or some default initialization
        }
        if (Util.current_user == null) {
            finish(); // Close the activity if there's no current user
            return;
        }

        Button ButtonNavigation_Main = findViewById(R.id.Navigation_Main);
        ButtonNavigation_Main.setOnClickListener(view -> {
            Intent intent = new Intent(SearchScreen.this, MainScreen.class);
            startActivity(intent);
        });

        searchResultsLayout = findViewById(R.id.searchResultsLayout);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(view -> {
            String query = searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                performSearch(query);
            }
        });
    }

    private void performSearch(String query) {
        String url = "https://api.spotify.com/v1/search?type=track&q=" + query;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + Util.spotify_access_token.access_token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                String responseData = response.body().string();
                API.SearchSong searchSong = gson.fromJson(responseData, API.SearchSong.class);

                // Post search results to backend
                API.PostUserSearchedSongs postUserSearchedSongs = new API.PostUserSearchedSongs();
                postUserSearchedSongs.id = Util.GenerateRandomInt();
                postUserSearchedSongs.spotify = Util.current_user.spotify;
                postUserSearchedSongs.items = Util.json_converter.toJson(searchSong.tracks.items);

                Util.MakePostRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/update/" + Util.current_user.spotify + "/searchedsongs", postUserSearchedSongs);

                // Retrieve posted search results
                API.GetUserSearchedSongs getUserSearchedSongs = Util.MakeGetRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/" + Util.current_user.spotify + "/searchedsongs", API.GetUserSearchedSongs.class);

                runOnUiThread(() -> {
                    if (getUserSearchedSongs != null && getUserSearchedSongs.items != null) {
                        List<API.SearchSong.Track> tracks = gson.fromJson(getUserSearchedSongs.items, new TypeToken<List<API.SearchSong.Track>>() {}.getType());
                        parseAndDisplayResults(tracks);
                    }
                });
            }
        });
    }


    private void parseAndDisplayResults(List<API.SearchSong.Track> tracks) {
        searchResultsLayout.removeAllViews();

        for (API.SearchSong.Track track : tracks) {
            String trackName = track.name;
            String imageUrl = track.album != null && !track.album.images.isEmpty() ? track.album.images.get(0).url : "";
            String artistName = track.artists != null && !track.artists.isEmpty() ? track.artists.get(0).name : "Unknown Artist";

            addResultToLayout(trackName, imageUrl, artistName);
        }
    }

    private void addResultToLayout(String trackName, String imageUrl, String artistName) {
        View trackView = getLayoutInflater().inflate(R.layout.search_result_item, searchResultsLayout, false);
        ImageView trackImageView = trackView.findViewById(R.id.coverImageView);
        TextView trackNameTextView = trackView.findViewById(R.id.nameTextView);
        TextView trackDetailTextView = trackView.findViewById(R.id.detailTextView);

        Picasso.get().load(imageUrl).into(trackImageView);
        trackNameTextView.setText(trackName);
        trackDetailTextView.setText(artistName);

        searchResultsLayout.addView(trackView);
    }
}
