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

public class SearchAlbums extends AppCompatActivity {

    private LinearLayout searchResultsLayout;
    private EditText searchEditText;
    private Button searchButton;
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen_albums);

        Button ButtonNavigation_Main = findViewById(R.id.Navigation_Main);
        ButtonNavigation_Main.setOnClickListener(view -> {
            Intent intent = new Intent(SearchAlbums.this, MainScreen.class);
            startActivity(intent);
        });

        searchResultsLayout = findViewById(R.id.searchResultsLayout);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(view -> {
            String query = searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                try {
                    performSearch(query);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void performSearch(String query) throws UnsupportedEncodingException {
        String url = "https://api.spotify.com/v1/search?type=album&q=" + query;

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
                API.SearchAlbum searchAlbum = gson.fromJson(responseData, API.SearchAlbum.class);

                API.PostUserSearchedAlbums postUserSearchedAlbums = new API.PostUserSearchedAlbums();
                postUserSearchedAlbums.id = Util.GenerateRandomInt();
                postUserSearchedAlbums.spotify = Util.current_user.spotify;
                postUserSearchedAlbums.items = Util.json_converter.toJson(searchAlbum.albums.items);

                Util.MakePostRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/update/" + Util.current_user.spotify + "/searchedalbums", postUserSearchedAlbums);

                // Retrieve posted search results
                API.GetUserSearchedAlbums getUserSearchedAlbums = Util.MakeGetRequestWrapper("http://coms-309-057.class.las.iastate.edu:8080/users/" + Util.current_user.spotify + "/searchedalbums", API.GetUserSearchedAlbums.class);

                runOnUiThread(() -> {
                    if (getUserSearchedAlbums != null && getUserSearchedAlbums.items != null) {
                        List<API.SearchAlbum.Album> albums = gson.fromJson(getUserSearchedAlbums.items, new TypeToken<List<API.SearchAlbum.Album>>() {}.getType());
                        parseAndDisplayResults(albums);
                    }
                });




            }
        });
    }

    private void parseAndDisplayResults(List<API.SearchAlbum.Album> albums) {
        searchResultsLayout.removeAllViews();

        for (API.SearchAlbum.Album album : albums) {
            String albumName = album.name;
            String imageUrl = album.images != null && !album.images.isEmpty() ? album.images.get(0).url : "";

            addResultToLayout(albumName, imageUrl);
        }
    }

    private void addResultToLayout(String albumName, String imageUrl) {
        View albumView = getLayoutInflater().inflate(R.layout.search_screen_album_item, searchResultsLayout, false);
        ImageView albumImageView = albumView.findViewById(R.id.albumImageView);
        TextView albumNameTextView = albumView.findViewById(R.id.albumNameTextView);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(albumImageView);
        }
        albumNameTextView.setText(albumName);

        searchResultsLayout.addView(albumView);
    }
}
