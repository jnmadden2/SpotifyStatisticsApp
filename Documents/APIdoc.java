package com.gr0m.quicksc;

import java.util.List;

/**
 * This class provides API functionalities for managing user profiles,
 * their top artists, top tracks, recently played tracks, and followed artists.
 */
public class API
{
    // MANAGING USER(S)
    /**
     * Represents an access token with its details.
     */
    public static class AccessToken
    {
        public String access_token = "";
        public String token_type = "";
        public String scope = "";
        public int expires_in = 0;
        public String refresh_token = "";

    }
    /**
     * Represents the data structure for posting user profile information to the backend.
     */
    public static class PostBackendUserProfile
    {
        public int id;
        public String spotify;
        public String displayName;
        public String pictureURL;
        public String email;
        public String accessToken;

    }
    /**
     * Represents the data structure for getting user profile information from the backend.
     */
    public static class GetBackendUserProfile
    {
        public int id;
        public String spotify;
        public String displayName;
        public String pictureURL;
        public String email;
        public String accessToken;

    }
    /**
     * Represents a user's profile
     */
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
        /**
         * Represents a user's Explicit Content
         */
        public class ExplicitContent
        {
            public boolean filter_enabled;
            public boolean filter_locked;

        }
        /**
         * Represents a user's url
         */
        public class ExternalUrls
        {
            public String spotify;

        }
        /**
         * Represents a user's followers
         */
        public class Followers
        {
            public String href;
            public int total;

        }
        /**
         * Represents a user's profile image
         */
        public class Image
        {
            public String url;
            public int height;
            public int width;

        }



    }
    /**
     * Represents the data required to post a user's top artists to the backend.
     * This includes an identifier, artist information, and associated Spotify data.
     */
    public static class PostBackendUserTopArtists
    {
        public int id;
        public String items;
        public String spotify;

    }
    /**
     * Represents the data structure for retrieving a user's top artists from the backend.
     * This class is used to fetch data like artist details associated with a specific user.
     */
    public static class GetBackendUserTopArtists
    {
        public int id;
        public String items;
        public String spotify;

    }
    /**
     * Encapsulates information about a user's top artists.
     * This includes details like URLs, limits, and a list of artist objects.
     */
    //MANAGING USER(S) TOP ARTIST
    public class UserTopArtists
    {
        public String href;
        public int limit;
        public String next;
        public int offset;
        public String previous;
        public int total;
        public List<ArtistObject> items;
        /**
         * Represents external URL(s) for an artist, typically on Spotify.
         */
        public class ExternalUrls
        {
            public String spotify;

        }
        /**
         * Represents the followers of an artist.
         */
        public class Followers
        {
            public String href;
            public int total;

        }
        /**
         * Represents an image, typically of an artist or album.
         */
        public class Image
        {
            public String url;
            public int height;
            public int width;

        }
        /**
         * Represents detailed information about an artist.
         */
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
    /**
     * Represents the data structure for posting user's top tracks information to the backend.
     * This class is primarily used for updating the top tracks related data in the backend system.
     */
    public static class PostBackendUserTopTracks
    {
        public int id;
        public String items;
        public String spotify;

    }
    /**
     * Represents the data structure for retrieving a user's top tracks from the backend.
     * This class is used to fetch top tracks data associated with a specific user.
     */
    public static class GetBackendUserTopTracks
    {
        public int id;
        public String items;
        public String spotify;

    }
    /**
     * Encapsulates information about a user's top tracks.
     * This includes track details like URLs, limits, and a list of track objects.
     */
    public class UserTopTracks
    {
        public String href;
        public int limit;
        public String next;
        public int offset;
        public String previous;
        public int total;
        public List<Track> items;
        /**
         * Represents external URL(s) for an entity, typically a track or album on Spotify.
         */
        public class ExternalUrls
        {
            public String spotify;

        }
        /**
         * Represents an image, typically of an album or artist.
         */
        public class Image
        {
            public String url;
            public int height;
            public int width;

        }
        /**
         * Represents an artist involved in a track or album.
         */
        public class Artist
        {
            public ExternalUrls external_urls;
            public String href;
            public String id;
            public String name;
            public String type;
            public String uri;

        }
        /**
         * Represents an album which includes one or more tracks.
         */
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
        /**
         * Represents a track with its details.
         */
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
    /**
     * Represents the data structure for posting a user's recently played tracks to the backend.
     * This class is typically used for updating the recently played tracks data in the backend system.
     */
    public static class PostBackendUserRecentlyPlayed
    {
        public int id;
        public String items;
        public String spotify;
    }
    /**
     * Represents the data structure for retrieving a user's recently played tracks from the backend.
     * This class is used to fetch recently played tracks data associated with a specific user.
     */
    public static class GetBackendUserRecentlyPlayed
    {
        public int id;
        public String items;
        public String spotify;

    }
    /**
     * Encapsulates information about a user's recently played tracks.
     * This includes details like URLs, limits, cursors for pagination, and a list of play history objects.
     */

    public static class UserRecentlyPlayed
    {
        public String href;
        public int limit;
        public String next;
        public Cursors cursors;
        public int total;
        public List<PlayHistoryObject> items;
        /**
         * Represents pagination cursors for traversing through a collection.
         */
        public class Cursors
        {
            public String after;
            public String before;

        }
        /**
         * Represents external URL(s) for a Spotify entity.
         */
        public class ExternalUrls
        {
            public String spotify;

        }
        /**
         * Represents an image, typically of an album or artist.
         */
        public class Image
        {
            public String url;
            public int height;
            public int width;

        }

        /**
         * Represents an artist involved in a track or album.
         */
        public class Artist
        {
            public ExternalUrls external_urls;
            public String href;
            public String id;
            public String name;
            public String type;
            public String uri;

        }
        /**
         * Represents an album.
         */
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
        /**
         * Represents a track with its details.
         */
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
        /**
         * Represents a single track played by a user along with its context.
         */
        public class PlayHistoryObject
        {
            public Track track;
            public String played_at; // [date-time]
            public Context context;

        }

    }

    //MANAGING USER(S) RECENTLY LISTENED
    /**
     * Represents the data structure for retrieving a user's followed artists from the backend.
     * This class is used to fetch the list of artists followed by a specific user.
     */
    public static class GetUserFollowedArtists
    {
        public int id;
        public String items;
        public String spotify;

    }
    /**
     * Represents the data structure for posting a user's followed artists to the backend.
     * This class is primarily used for updating the list of artists followed by the user in the backend system.
     */
    public static class PostUserFollowedArtists
    {
        public int id;
        public String items;
        public String spotify;

    }
    /**
     * Encapsulates information about the artists followed by a user.
     * This includes a list of artist objects along with pagination details.
     */
    public static class UserFollowedArtists
    {
        Artists artists;
        /**
         * Represents an artist involved in a track or album.
         */
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
        /**
         * Represents external URL(s) for an entity, typically a track or album on Spotify.
         */
        public class ExternalUrls
        {
            public String spotify;

        }
        /**
         * Represents the followers of an artist.
         */
        public class Followers
        {
            public String href;
            public int total;

        }
        /**
         * Represents an image, typically of an artist or album.
         */
        public class Image
        {
            public String url;
            public int height;
            public int width;

        }
        /**
         * Represents a collection of artists followed by the user.
         */
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

}
