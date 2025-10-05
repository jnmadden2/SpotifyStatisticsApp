package com.gr0m.quicksc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import okhttp3.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class SearchArtists extends AppCompatActivity {

    private LinearLayout searchResultsLayout;
    private EditText searchEditText;
    private Button searchButton;
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen_artists);

        Button ButtonNavigation_Main = findViewById(R.id.Navigation_Main);
        ButtonNavigation_Main.setOnClickListener(view -> {
            Intent intent = new Intent(SearchArtists.this, MainScreen.class);
            startActivity(intent);
        });


        searchResultsLayout = findViewById(R.id.searchResultsLayout);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchEditText.getText().toString().trim();
                if (!query.isEmpty()) {
                    try {
                        performSearch(query);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void performSearch(String query) throws UnsupportedEncodingException {
        String encodedQuery = java.net.URLEncoder.encode(query, "UTF-8");
        String url = "https://api.spotify.com/v1/search?type=artist&q=" + encodedQuery;

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
                API.SearchArtist searchArtist = gson.fromJson(responseData, API.SearchArtist.class);

                API.PostUserSearchedArtists postUserSearchedArtists = new API.PostUserSearchedArtists();
                postUserSearchedArtists.id = Util.GenerateRandomInt();
                postUserSearchedArtists.spotify = Util.current_user.spotify;
                postUserSearchedArtists.items = Util.json_converter.toJson(searchArtist.artists.items);

                Util.MakePostRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/update/" + Util.current_user.spotify + "/searchedartists", postUserSearchedArtists);

                // Retrieve posted search results
                API.GetUserSearchedArtists getUserSearchedArtists = Util.MakeGetRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/" + Util.current_user.spotify + "/searchedartists", API.GetUserSearchedArtists.class);

                runOnUiThread(() -> {
                    if (getUserSearchedArtists != null && getUserSearchedArtists.items != null) {
                        List<API.SearchArtist.Artist> artists = gson.fromJson(getUserSearchedArtists.items, new TypeToken<List<API.SearchArtist.Artist>>() {}.getType());
                        parseAndDisplayResults(artists);
                    }
                });


            }
        });
    }

    private void parseAndDisplayResults(List<API.SearchArtist.Artist> artists) {
        searchResultsLayout.removeAllViews();

        for (API.SearchArtist.Artist artist : artists) {
            String artistName = artist.name;
            String imageUrl = artist.images != null && !artist.images.isEmpty() ? artist.images.get(0).url : "";

            addResultToLayout(artistName, imageUrl);
        }
    }

    private void addResultToLayout(String artistName, String imageUrl) {
        View artistView = getLayoutInflater().inflate(R.layout.search_screen_artist_item, searchResultsLayout, false);
        ImageView artistImageView = artistView.findViewById(R.id.artistImageView);
        TextView artistNameTextView = artistView.findViewById(R.id.artistNameTextView);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(artistImageView);
        } else {

        }
        artistNameTextView.setText(artistName);

        searchResultsLayout.addView(artistView);
    }
}
