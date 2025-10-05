package com.gr0m.quicksc;

import java.io.IOException;
import android.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import fi.iki.elonen.NanoHTTPD;
import okhttp3.*;

public class SpotifyServer extends NanoHTTPD
{
    // code returned by spotify to exchange for access token object
    private String auth_code;
    // OkHttpClient object to make get / post / delete requests
    private OkHttpClient client = new OkHttpClient();
    // String object to hold access token (JSON)
    private String access_token_json;

    public SpotifyServer(int port)
    {
        super(port);

    }
    public String ExchangeCodeForAccessToken(String code)
    {
        String header_credentials = Util.CLIENT_ID + ":" + Util.CLIENT_SECRET;
        String encoded_header_credentials = Base64.encodeToString(header_credentials.getBytes(), Base64.NO_WRAP);

        RequestBody request_body = new FormBody.Builder()
                .add("code", code)
                .add("redirect_uri", Util.REDIRECT_URI)
                .add("grant_type", "authorization_code")
                .build();

        Request request = new Request.Builder()
                .url(Util.TOKEN_URL)
                .post(request_body)
                .header("Authorization", "Basic " + encoded_header_credentials)
                .build();

        try(okhttp3.Response response = this.client.newCall(request).execute())
        {
            if (response.isSuccessful()) return response.body().string();
            else return null;

        } catch (IOException e)
        {
            throw new RuntimeException(e);

        }

    }
    @Override
    public Response serve(IHTTPSession session)
    {
        if ("/callback".equalsIgnoreCase(session.getUri()))
        {
            // Handle the callback logic here.
            Map<String, List<String>> parameters = session.getParameters();
            if (parameters.containsKey("code"))
            {
                this.auth_code = parameters.get("code").get(0);
                if(this.auth_code != null && !this.auth_code.trim().isEmpty())
                {
                    this.access_token_json = this.ExchangeCodeForAccessToken(this.auth_code);
                    if(this.access_token_json != null) Util.spotify_access_token = Util.json_converter.fromJson(this.access_token_json, API.AccessToken.class);
                    else return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_HTML, "[-] access token was null!  RESTART APPLICATION!");

                }
                else return newFixedLengthResponse(Response.Status.EXPECTATION_FAILED, MIME_HTML, "[-] returned auth code is null or empty! RESTART APPLICATION!");

            }
            if(this.access_token_json != null && !this.access_token_json.trim().isEmpty()) return newFixedLengthResponse(Response.Status.OK, MIME_HTML, "[+] callback received!\n [+] access token collected!\n [+] please resume the application!\n");
            else return newFixedLengthResponse(Response.Status.EXPECTATION_FAILED, MIME_HTML, "[-] failed to exchange code for access token! RESTART APPLICATION!");

        }
        return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_HTML, "[-] not found!  RESTART APPLICATION!");

    }

    // run all API calls in background thread
    public <T> T SpotifyAPICall(String url, Class<T> type)
    {
        ExecutorService executor_service = Executors.newSingleThreadExecutor();
        Future<String> future = executor_service.submit(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", "Bearer " + Util.spotify_access_token.access_token)
                        .build();

                try (okhttp3.Response response = new OkHttpClient().newCall(request).execute())
                {
                    if (response.isSuccessful()) return response.body().string();
                    else return null;

                }
                catch (IOException e)
                {
                    return null;

                }

            }

        });
        executor_service.shutdown();

        try
        {
            String api_json = future.get();
            if(api_json != null) return Util.json_converter.fromJson(api_json, type);
            else return null;

        }
        catch (InterruptedException | ExecutionException ex)
        {
            ex.printStackTrace();

        }
        return null;

    }

}
