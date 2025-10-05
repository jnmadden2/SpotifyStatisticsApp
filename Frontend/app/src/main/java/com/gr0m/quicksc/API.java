package com.gr0m.quicksc;

import java.util.List;

public class API
{
    // MANAGING USER(S)
    public static class AccessToken
    {
        public String access_token = "";
        public String token_type = "";
        public String scope = "";
        public int expires_in = 0;
        public String refresh_token = "";

    }
    public static class PostBackendUserProfile
    {
        public int id;
        public String spotify;
        public String displayName;
        public String pictureURL;
        public String email;
        public String accessToken;

    }
    public static class GetBackendUserProfile
    {
        public int id;
        public String spotify;
        public String displayName;
        public String pictureURL;
        public String email;
        public String accessToken;

    }
    public class UserProfile
    {
        public String display_name;
        public String email;
        public ExplicitContent explicit_content;
        public ExternalUrls external_urls;
        public Followers followers;
        public String id;
        public List<Image> images;
        public String product;
        public String type;
        public String uri;

        public class ExplicitContent
        {
            public boolean filter_enabled;
            public boolean filter_locked;

        }
        public class ExternalUrls
        {
            public String spotify;

        }
        public class Followers
        {
            public String href;
            public int total;

        }
        public class Image
        {
            public String url;
            public int height;
            public int width;

        }



    }

    public static class PostBackendUserTopArtists
    {
        public int id;
        public String items;
        public String spotify;

    }

    public static class GetBackendUserTopArtists
    {
        public int id;
        public String items;
        public String spotify;

    }

    //MANAGING USER(S) TOP ARTIST
    public static class UserTopArtists
    {
        public String href;
        public int limit;
        public String next;
        public int offset;
        public String previous;
        public int total;
        public List<ArtistObject> items;

        public class ExternalUrls
        {
            public String spotify;

        }
        public class Followers
        {
            public String href;
            public int total;

        }
        public class Image
        {
            public String url;
            public int height;
            public int width;

        }
        public class ArtistObject
        {
            public ExternalUrls spotify;
            public Followers followers;
            public List<String> genres;
            public String href;
            public String id;
            public List<Image> images;

            public String name;
            public int popularity;
            public String type;
            public String uri;

        }

    }

    //MANAGING USER(S) TOP TRACKS
    public static class PostBackendUserTopTracks
    {
        public int id;
        public String items;
        public String spotify;

    }

    public static class GetBackendUserTopTracks
    {
        public int id;
        public String items;
        public String spotify;

    }

    public static class UserTopTracks
    {
        public String href;
        public int limit;
        public String next;
        public int offset;
        public String previous;
        public int total;
        public List<Track> items;

        public class ExternalUrls
        {
            public String spotify;

        }

        public class Image
        {
            public String url;
            public int height;
            public int width;

        }

        public class Artist
        {
            public ExternalUrls external_urls;
            public String href;
            public String id;
            public String name;
            public String type;
            public String uri;

        }

        public class Album
        {
            public String album_type;
            public List<Artist> artists;
            public List<String> available_markets;
            public ExternalUrls external_urls;
            public String href;
            public String id;
            public List<Image> images;
            public String name;
            public String release_date;
            public String release_date_precision;
            public int total_tracks;
            public String type;
            public String uri;

        }

        public class Track
        {
            public Album album;
            public List<Artist> artists;
            public List<String> available_markets;
            public int disc_number;
            public int track_number;
            public int duration_ms;
            public boolean explicit;
            public ExternalUrls external_urls;
            public String href;
            public String id;
            public boolean is_playable;
            public String name;
            public int popularity;

            public String type;
            public String uri;

        }

    }

    //MANAGING USER(S) RECENTLY LISTENED
    public static class PostBackendUserRecentlyPlayed
    {
        public int id;
        public String items;
        public String spotify;
    }

    public static class GetBackendUserRecentlyPlayed
    {
        public int id;
        public String items;
        public String spotify;

    }

    public static class UserRecentlyPlayed
    {
        public String href;
        public int limit;
        public String next;
        public Cursors cursors;
        public int total;
        public List<PlayHistoryObject> items;

        public static class Cursors
        {
            public String after;
            public String before;

        }

        public class ExternalUrls
        {
            public String spotify;

        }

        public class Image
        {
            public String url;
            public int height;
            public int width;

        }

        public class Artist
        {
            public ExternalUrls external_urls;
            public String href;
            public String id;
            public String name;
            public String type;
            public String uri;

        }

        public class Album
        {
            public String album_type;
            public List<Artist> artists;
            public List<String> available_markets;
            public ExternalUrls external_urls;
            public String href;
            public String id;
            public List<Image> images;
            public String name;
            public String release_date;
            public String release_date_precision;
            public int total_tracks;
            public String type;
            public String uri;

        }

        public class Track
        {
            public UserRecentlyPlayed.Album album;
            public List<Artist> artists;
            public List<String> available_markets;
            public int disc_number;
            public int track_number;
            public int duration_ms;
            public boolean explicit;
            public ExternalUrls external_urls;
            public String href;
            public String id;
            public boolean is_playable;
            public String name;
            public int popularity;

            public String type;
            public String uri;

        }

        public class Context
        {
            public String type;
            public String href;
            public ExternalUrls external_urls;
            public String uri;

        }

        public class PlayHistoryObject
        {
            public Track track;
            public String played_at; // [date-time]
            public Context context;

        }

    }

    //MANAGING USER(S) RECENTLY LISTENED
    public static class GetUserFollowedArtists
    {
        public int id;
        public String items;
        public String spotify;

    }
    public static class PostUserFollowedArtists
    {
        public int id;
        public String items;
        public String spotify;

    }
    public static class UserFollowedArtists
    {
        public Artists artists;

        public class Artists
        {
            public String href;
            public int limit;
            public String next;
            public Cursors cursors;
            public int total;
            public List<ArtistObject> items;

        }

        public class Cursors
        {
            public String after;
            public String before;

        }

        public class ExternalUrls
        {
            public String spotify;

        }
        public class Followers
        {
            public String href;
            public int total;

        }
        public class Image
        {
            public String url;
            public int height;
            public int width;

        }
        public class ArtistObject
        {
            public ExternalUrls spotify;
            public Followers followers;
            public List<String> genres;
            public String href;
            public String id;
            public List<Image> images;

            public String name;
            public int popularity;
            public String type;
            public String uri;

            public int minutes_listened;

        }

    }
    public class FollowedArtistsMinutes
    {

        API.UserFollowedArtists Artists;
        int MinutesListened;

    }
    public static class GetUserSearchedSongs
    {
        public int id;
        public String items;
        public String spotify;

    }
    public static class PostUserSearchedSongs
    {
        public int id;
        public String items;
        public String spotify;

    }
    public static class SearchSong {
        public Tracks tracks;

        public static class Tracks {
            public List<Track> items;
        }

        public class Track {
            public String name;
            public List<Artist> artists;
            public Album album;
            public int duration_ms;
            public boolean explicit;
            public ExternalUrls external_urls;
            // ... other relevant fields ...
        }

        public class Artist {
            public String name;
            // ... other artist-specific fields ...
        }

        public class Album {
            public String name;
            public List<Image> images;
            // ... other album-specific fields ...
        }

        public class Image {
            public String url;
            public int height;
            public int width;
        }

        public class ExternalUrls {
            public String spotify;
        }
    }
    public static class PostUserSearchedArtists
    {
        public int id;
        public String items;
        public String spotify;

    }

    public static class GetUserSearchedArtists
    {
        public int id;
        public String items;
        public String spotify;

    }
    public static class SearchArtist {
        public Artists artists;

        public static class Artists {
            public List<Artist> items;
        }

        public static class Artist {
            public String name;
            public List<Image> images;
            // ... other artist-specific fields ...
        }

        public static class Image {
            public String url;
            public int height;
            public int width;
        }

        public static class ExternalUrls {
            public String spotify;
        }

    }
    public static class GetUserSearchedAlbums
    {
        public int id;
        public String items;
        public String spotify;

    }
    public static class PostUserSearchedAlbums
    {
        public int id;
        public String items;
        public String spotify;

    }
    public static class SearchAlbum {
        public Albums albums;

        public static class Albums {
            public List<Album> items;
        }

        public class Album {
            public String name;
            public List<Image> images;
            public List<Artist> artists;
            public String release_date;
            public String album_type;
            public ExternalUrls external_urls;

        }

        public class Artist {
            public String name;
            public ExternalUrls external_urls;
            // ... other artist-specific fields ...
        }

        public class Image {
            public String url;
            public int height;
            public int width;
        }

        public static class ExternalUrls {
            public String spotify;
        }


    }


}
