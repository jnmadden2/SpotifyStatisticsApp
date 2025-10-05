package com.gr0m.quicksc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginScreen extends AppCompatActivity
{
    private void ReloadScreen()
    {
        if(Util.initialization_status == 1)
        {
            Intent intent = getIntent();
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            Util.initialization_status = 2;

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_screen);

        Button LoginScreen_SpotifyAuthorizationButton = (Button)findViewById(R.id.LoginScreen_SpotifyAuthorizationButton);
        LoginScreen_SpotifyAuthorizationButton.setVisibility(View.GONE);

        if (Util.spotify_access_token.access_token.trim().isEmpty())
        {
            // set-up localhost:8080 to listen for spotify redirect
            Util.service = Util.InitSpotifyServer(8080);
            Util.StartSpotifyServer(Util.service);

            LoginScreen_SpotifyAuthorizationButton.setVisibility(View.VISIBLE);
            LoginScreen_SpotifyAuthorizationButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Util.initialization_status = 1;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Util.GetSpotifyAuthUrl()));
                    startActivity(intent);

                }

            });

        }
        else
        {
            if (!Util.spotify_access_token.access_token.trim().isEmpty())
            {
                finish();
                Intent intent = new Intent(LoginScreen.this, MainScreen.class);
                startActivity(intent);

            }

        }

    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.ReloadScreen();

    }

}