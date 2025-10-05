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

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    public static int SpotifyUnit = 1000;

    // main service to access SpotifyServer methods and members
    public static SpotifyServer service;
    /*
     * 0 = needs to start
     * 1 = started
     * 2 = successful
     */
    public static int initialization_status = 0;

    public static boolean menu_open = false;

    public static SpotifyServer InitSpotifyServer(int port)
    {
        return new SpotifyServer(port);

    }
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
    public static int GenerateRandomInt()
    {
        return ThreadLocalRandom.current().nextInt(100_000_000, 1_000_000_000);

    }
    public static int CalculateMinutesFromHours(int i)
    {
        Random rand = new Random();

        return rand.nextInt(i);

    }
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
