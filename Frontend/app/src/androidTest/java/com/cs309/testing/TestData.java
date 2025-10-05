package com.cs309.testing;
import com.gr0m.quicksc.*;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

public class TestData {
    public static API.UserTopTracks getMockUserTopTracks() {
        API.UserTopTracks mockTracks = new API.UserTopTracks();
        mockTracks.href = "https://api.spotify.com/v1/me/top/tracks";
        mockTracks.limit = 10;
        mockTracks.next = "https://api.spotify.com/v1/me/top/tracks?offset=10";
        mockTracks.offset = 0;
        mockTracks.previous = null;
        mockTracks.total = 20;

        List<API.UserTopTracks.Track> trackList = new ArrayList<>();
        API.UserTopTracks.Track track = new API.UserTopTracks().new Track();

        track.album = new API.UserTopTracks().new Album();
        track.album.album_type = "album";
        track.album.name = "Sample Album";
        track.album.release_date = "2023-01-01";

        API.UserTopTracks.Artist artist = new API.UserTopTracks().new Artist();
        artist.name = "Sample Artist";
        track.artists = new ArrayList<>();
        track.artists.add(artist);

        track.name = "Sample Track";
        track.duration_ms = 210000;
        track.popularity = 75;
        track.id = "3n3Ppam7vgaVa1iaRUc9Lp"; // Example of a Spotify-like track ID

        API.UserTopTracks.Image image = new API.UserTopTracks().new Image();
        image.url = "https://example.com/image.jpg";
        image.height = 640;
        image.width = 640;
        track.album.images = new ArrayList<>();
        track.album.images.add(image);

        trackList.add(track);
        mockTracks.items = trackList;

        return mockTracks;

    }
    public static API.UserRecentlyPlayed getMockUserRecentlyPlayed() {
        API.UserRecentlyPlayed mockRecentlyPlayed = new API.UserRecentlyPlayed();
        mockRecentlyPlayed.href = "https://api.spotify.com/v1/me/player/recently-played";
        mockRecentlyPlayed.limit = 10;
        mockRecentlyPlayed.next = "https://api.spotify.com/v1/me/player/recently-played?offset=10";
        mockRecentlyPlayed.cursors = new API.UserRecentlyPlayed.Cursors();
        mockRecentlyPlayed.cursors.after = "cursorAfter";
        mockRecentlyPlayed.cursors.before = "cursorBefore";
        mockRecentlyPlayed.total = 20;

        List<API.UserRecentlyPlayed.PlayHistoryObject> playHistoryList = new ArrayList<>();
        API.UserRecentlyPlayed.PlayHistoryObject playHistory = new API.UserRecentlyPlayed().new PlayHistoryObject();

        playHistory.track = createSampleTrack();
        playHistory.played_at = "2023-04-01T12:00:00Z"; // ISO 8601 format
        playHistory.context = new API.UserRecentlyPlayed().new Context();
        playHistory.context.href = "https://api.spotify.com/v1/albums/albumId";
        playHistory.context.type = "album";
        playHistory.context.uri = "spotify:album:albumId";

        playHistoryList.add(playHistory);
        mockRecentlyPlayed.items = playHistoryList;

        return mockRecentlyPlayed;
    }

    private static API.UserRecentlyPlayed.Track createSampleTrack() {
        API.UserRecentlyPlayed.Track track = new API.UserRecentlyPlayed().new Track();

        track.album = new API.UserRecentlyPlayed().new Album();
        track.album.album_type = "album";
        track.album.name = "Sample Album";
        track.album.release_date = "2023-01-01";

        API.UserRecentlyPlayed.Artist artist = new API.UserRecentlyPlayed().new Artist();
        artist.name = "Sample Artist";
        track.artists = new ArrayList<>();
        track.artists.add(artist);

        track.name = "Sample Track";
        track.duration_ms = 210000;
        track.popularity = 75;
        track.id = "3n3Ppam7vgaVa1iaRUc9Lp"; // Example of a Spotify-like track ID

        API.UserRecentlyPlayed.Image image = new API.UserRecentlyPlayed().new Image();
        image.url = "https://example.com/image.jpg";
        image.height = 640;
        image.width = 640;
        track.album.images = new ArrayList<>();
        track.album.images.add(image);

        return track;
    }
    public static API.SearchSong getMockSearchSong() {
        API.SearchSong mockSearchSong = new API.SearchSong();
        mockSearchSong.tracks = new API.SearchSong.Tracks();

        List<API.SearchSong.Track> trackList = new ArrayList<>();
        API.SearchSong.Track track = new API.SearchSong().new Track();

        track.name = "Sample Track";
        track.duration_ms = 210000;
        track.explicit = false;

        track.external_urls = new API.SearchSong().new ExternalUrls();
        track.external_urls.spotify = "https://open.spotify.com/track/sampleTrackId";

        API.SearchSong.Artist artist = new API.SearchSong().new Artist();
        artist.name = "Sample Artist";
        track.artists = new ArrayList<>();
        track.artists.add(artist);

        track.album = new API.SearchSong().new Album();
        track.album.name = "Sample Album";

        API.SearchSong.Image image = new API.SearchSong().new Image();
        image.url = "https://example.com/album-image.jpg";
        image.height = 640;
        image.width = 640;
        track.album.images = new ArrayList<>();
        track.album.images.add(image);

        trackList.add(track);
        mockSearchSong.tracks.items = trackList;

        return mockSearchSong;
    }

    public static API.UserFollowedArtists getMockUserFollowedArtists() {
        API.UserFollowedArtists mockFollowedArtists = new API.UserFollowedArtists();
        mockFollowedArtists.artists = mockFollowedArtists.new Artists();

        mockFollowedArtists.artists.href = "https://api.spotify.com/v1/me/following?type=artist";
        mockFollowedArtists.artists.limit = 10;
        mockFollowedArtists.artists.next = "https://api.spotify.com/v1/me/following?type=artist&after=artistId";
        mockFollowedArtists.artists.cursors = mockFollowedArtists.new Cursors();
        mockFollowedArtists.artists.cursors.after = "artistId";
        mockFollowedArtists.artists.cursors.before = null;
        mockFollowedArtists.artists.total = 20;

        List<API.UserFollowedArtists.ArtistObject> artistList = new ArrayList<>();
        API.UserFollowedArtists.ArtistObject artist = createSampleFollowedArtist();

        artistList.add(createSampleFollowedArtist());
        mockFollowedArtists.artists.items = artistList;

        return mockFollowedArtists;
    }

    private static API.UserFollowedArtists.ArtistObject createSampleFollowedArtist() {
        API.UserFollowedArtists.ArtistObject artist = new API.UserFollowedArtists().new ArtistObject();
        artist.spotify = new API.UserFollowedArtists().new ExternalUrls();
        artist.spotify.spotify = "https://open.spotify.com/artist/artistId";
        artist.followers = new API.UserFollowedArtists().new Followers();
        artist.followers.href = null;
        artist.followers.total = 100000;
        artist.genres = Arrays.asList("rock", "pop");
        artist.href = "https://api.spotify.com/v1/artists/artistId";
        artist.id = "artistId";
        artist.images = new ArrayList<>();
        API.UserFollowedArtists.Image image = new API.UserFollowedArtists().new Image();
        image.url = "https://example.com/artist-image.jpg";
        image.height = 640;
        image.width = 640;
        artist.images.add(image);
        artist.name = "Sample Artist";
        artist.popularity = 80;
        artist.type = "artist";
        artist.uri = "spotify🧑‍🎨artistId";
        artist.minutes_listened = 12345;

        return artist;
    }
    public static API.UserTopArtists getMockUserTopArtists() {
        API.UserTopArtists mockArtists = new API.UserTopArtists();
        mockArtists.href = "https://api.spotify.com/v1/me/top/artists";
        mockArtists.limit = 10;
        mockArtists.next = "https://api.spotify.com/v1/me/top/artists?offset=10";
        mockArtists.offset = 0;
        mockArtists.previous = null;
        mockArtists.total = 20;

        List<API.UserTopArtists.ArtistObject> artistList = new ArrayList<>();
        for (int i = 0; i < mockArtists.limit; i++) {
            artistList.add(createSampleArtist());
        }
        mockArtists.items = artistList;

        return mockArtists;
    }

    private static API.UserTopArtists.ArtistObject createSampleArtist() {
        API.UserTopArtists.ArtistObject artist = new API.UserTopArtists().new ArtistObject();
        artist.name = "Sample Artist";
        artist.id = "artistId" + System.currentTimeMillis(); // Unique ID
        artist.popularity = 80;
        artist.href = "https://api.spotify.com/v1/artists/artistId";

        // Images
        artist.images = new ArrayList<>();
        API.UserTopArtists.Image image = new API.UserTopArtists().new Image();
        image.url = "https://example.com/artist-image.jpg";
        image.height = 640;
        image.width = 640;
        artist.images.add(image);

        // Followers
        artist.followers = new API.UserTopArtists().new Followers();
        artist.followers.total = 100000;

        // Genres
        artist.genres = new ArrayList<>();
        artist.genres.add("rock");
        artist.genres.add("pop");

        // Additional fields based on your API requirements
        artist.type = "artist";
        artist.uri = "spotify:artist:artistId";

        // Fields such as biography, active years, etc., can be added based on your requirements
        // For example:


        return artist;
    }

    public static API.SearchAlbum getMockSearchAlbum() {
        API.SearchAlbum mockSearchAlbum = new API.SearchAlbum();
        mockSearchAlbum.albums = new API.SearchAlbum.Albums();
//        mockSearchAlbum.albums.href = "https://api.spotify.com/v1/search?type=album&q=sampleQuery";
        mockSearchAlbum.albums.items = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            mockSearchAlbum.albums.items.add(createSampleAlbum());
        }

        return mockSearchAlbum;
    }

    private static API.SearchAlbum.Album createSampleAlbum() {
        API.SearchAlbum.Album album = new API.SearchAlbum().new Album();
        album.name = "Sample Album " + System.currentTimeMillis(); // Unique name
        album.release_date = "2023-01-01";

        API.SearchAlbum.Image image = new API.SearchAlbum().new Image();
        image.url = "https://example.com/album-image.jpg";
        image.height = 640;
        image.width = 640;
        album.images = new ArrayList<>();
        album.images.add(image);



        // Add mock artist data if required
        API.SearchAlbum.Artist artist = new API.SearchAlbum().new Artist();
        artist.name = "Sample Artist";

        album.artists = new ArrayList<>();
        album.artists.add(artist);

        return album;
    }
}
