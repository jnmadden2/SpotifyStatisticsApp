package com.gr0m.quicksc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.SecureRandom;

import java.util.concurrent.ThreadLocalRandom;

import okhttp3.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * Provides utility functions for interacting with the Spotify API and handling HTTP requests.
 * Includes methods for starting and stopping a server, generating random strings and numbers,
 * and making HTTP POST and GET requests.
 */
public class Util
{
    // used to retrieve, access, and call Spotify API
    public static final String CLIENT_ID = "8fff1542f7984c2a9e1b14d35b06f27a";
    public static final String CLIENT_SECRET = "baa0d020ded943e4adfc18a87b9b0356";
    public static final String REDIRECT_URI = "http://localhost:8080/callback";
    public static final String RESPONSE_TYPE = "code";
    public static final String SCOPE = "user-read-private user-read-email user-top-read user-library-read user-read-recently-played user-follow-read";
    public static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    public static API.AccessToken spotify_access_token = new API.AccessToken();
    // used to convert JSON strings to API objects
    public static Gson json_converter = new Gson();
    public static API.GetBackendUserProfile current_user;

    // main service to access SpotifyServer methods and members
    public static SpotifyServer service;
    /*
     * 0 = needs to start
     * 1 = started
     * 2 = successful
     */
    public static int initialization_status = 0;

    public static boolean menu_open = false;
    /**
     * Initializes a new SpotifyServer instance on the specified port.
     *
     * @param port The port number on which the server will listen.
     * @return A new instance of SpotifyServer.
     */
    public static SpotifyServer InitSpotifyServer(int port)
    {
        return new SpotifyServer(port);

    }
    /**
     * Starts the specified SpotifyServer.
     *
     * @param service The SpotifyServer instance to be started.
     */
    public static void StartSpotifyServer(SpotifyServer service)
    {
        try
        {
            service.start();

        }
        catch (IOException ex)
        {
            ex.printStackTrace();

        }

    }
    /**
     * Stops the specified SpotifyServer.
     *
     * @param service The SpotifyServer instance to be stopped.
     */
    public static void CloseSpotifyServer(SpotifyServer service)
    {
        try
        {
            service.stop();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();

        }

    }
    /**
     * Generates a random alphanumeric string of the specified length.
     *
     * @param length The desired length of the random string.
     * @return A randomly generated string.
     */
    public static String GenerateRandomString(int length)
    {
        SecureRandom secure_random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);
        String allowed_characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < length; i++)
        {
            int randomIndex = secure_random.nextInt(allowed_characters.length());
            builder.append(allowed_characters.charAt(randomIndex));

        }
        return builder.toString();

    }
    /**
     * Generates a random integer within a specified range.
     *
     * @return A randomly generated integer.
     */
    public static int GenerateRandomInt()
    {
        return ThreadLocalRandom.current().nextInt(100_000_000, 1_000_000_000);

    }
    /**
     * Constructs the URL for Spotify authentication.
     *
     * @return A URL string for Spotify authentication.
     */
    public static String GetSpotifyAuthUrl()
    {
        String auth_url = "https://accounts.spotify.com/authorize?" +
                "response_type=" + Util.RESPONSE_TYPE +
                "&client_id=" + Util.CLIENT_ID +
                "&scope=" + Util.SCOPE +
                "&redirect_uri=" + Util.REDIRECT_URI +
                "&state=" + Util.GenerateRandomString(16);

        return auth_url;

    }
    /**
     * Wraps the MakePostRequest method to run asynchronously and handle exceptions.
     *
     * @param url    The URL to which the POST request will be made.
     * @param object The object to be posted.
     * @param <T>    The type of the object.
     */
    public static <T> void MakePostRequestWrapper(String url, T object)
    {
        ExecutorService executor_service = Executors.newSingleThreadExecutor();
        Future<String> future = executor_service.submit(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                try
                {
                    String response = Util.MakePostRequest(url, object);
                    return response;

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();

                }
                return "[+] success!";

            }

        });
        executor_service.shutdown();

        try
        {
            String status = future.get();
            System.out.println(status);

        }
        catch (InterruptedException | ExecutionException ex)
        {
            ex.printStackTrace();

        }

    }
    /**
     * Makes a POST request to the specified URL with the given object.
     *
     * @param url    The URL to which the POST request will be made.
     * @param object The object to be posted.
     * @param <T>    The type of the object.
     * @return The response from the server.
     * @throws IOException If an I/O error occurs.
     */
    public static <T> String MakePostRequest(String url, T object) throws IOException
    {
        Gson converter = new Gson();
        String converted_object = converter.toJson(object);

        RequestBody body = RequestBody.create(converted_object, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json") // Ensure header is set
                .build();

        OkHttpClient client = new OkHttpClient();
        try (okhttp3.Response response = client.newCall(request).execute())
        {
            if (response.isSuccessful()) return ("[+] server responded with: " + response.body().string());
            else return ("[-] failed to get a valid response: " + response.code() + ", " + response.message());

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            throw ex;

        }

    }
    /**
     * Wraps the MakeGetRequest method to run asynchronously and handle exceptions.
     *
     * @param url  The URL from which the GET request will be made.
     * @param type The class type of the expected response.
     * @param <T>  The type of the expected response.
     * @return An object of the specified type containing the response data.
     */
    public static <T> T MakeGetRequestWrapper(String url, Class<T> type)
    {
        ExecutorService executor_service = Executors.newSingleThreadExecutor();
        Future<String> future = executor_service.submit(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                try
                {
                    String response = Util.MakeGetRequest(url, type);
                    if(response == null) return null;
                    return response;

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();

                }
                return "[+] success!";

            }

        });
        executor_service.shutdown();

        try
        {
            String json_get = future.get();
            if(json_get != null) return Util.json_converter.fromJson(json_get, type);
            else return null;

        }
        catch (InterruptedException | ExecutionException ex)
        {
            ex.printStackTrace();

        }
        return null;

    }
    /**
     * Makes a GET request to the specified URL.
     *
     * @param url  The URL from which the GET request will be made.
     * @param type The class type of the expected response.
     * @param <T>  The type of the expected response.
     * @return The response from the server as a string.
     * @throws IOException If an I/O error occurs.
     */
    public static <T> String MakeGetRequest(String url, Class<T> type) throws IOException
    {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (okhttp3.Response response = new OkHttpClient().newCall(request).execute())
        {
            if (response.isSuccessful()) return response.body().string();
            else return null;

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            throw ex;

        }

    }

}
