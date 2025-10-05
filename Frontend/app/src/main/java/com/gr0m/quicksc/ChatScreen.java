package com.gr0m.quicksc;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.handshake.ServerHandshake;
import org.w3c.dom.Text;


    public class ChatScreen extends AppCompatActivity implements WebSocketListener{

        private String BASE_URL = "ws://coms-309-057.class.las.iastate.edu:8080/chat/";

        private Button sendBtn;
        private EditText msgEtx;
        private TextView msgTv, usernameEtx;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.chat_screen);

            /* initialize UI elements */

            sendBtn = (Button) findViewById(R.id.bt2);
            msgEtx = (EditText) findViewById(R.id.et2);
            msgTv = (TextView) findViewById(R.id.tx1);

            Button ButtonNavigation = (Button) findViewById(R.id.Navigation);
            Button ButtonNavigation_TopArtists = (Button) findViewById(R.id.Navigation_TopArtists);

            Button ButtonNavigation_Main = (Button) findViewById(R.id.Navigation_Main);
            Button ButtonNavigation_RecentlyPlayed = (Button) findViewById(R.id.Navigation_RecentlyPlayed);
            Button ButtonNavigation_FollowedArtists = (Button) findViewById(R.id.Navigation_FollowedArtists);

            ButtonNavigation_TopArtists.setEnabled(false);
            ButtonNavigation_TopArtists.setVisibility(View.INVISIBLE);
            ButtonNavigation_Main.setEnabled(false);
            ButtonNavigation_Main.setVisibility(View.INVISIBLE);
            ButtonNavigation_RecentlyPlayed.setEnabled(false);
            ButtonNavigation_RecentlyPlayed.setVisibility(View.INVISIBLE);
            ButtonNavigation_FollowedArtists.setEnabled(false);
            ButtonNavigation_FollowedArtists.setVisibility(View.INVISIBLE);

            ButtonNavigation.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(!Util.menu_open)
                    {
                        ButtonNavigation_TopArtists.setEnabled(true);
                        ButtonNavigation_TopArtists.setVisibility(View.VISIBLE);

                        ButtonNavigation_RecentlyPlayed.setEnabled(true);
                        ButtonNavigation_RecentlyPlayed.setVisibility(View.VISIBLE);

                        ButtonNavigation_Main.setEnabled(true);
                        ButtonNavigation_Main.setVisibility(View.VISIBLE);

                        ButtonNavigation_FollowedArtists.setEnabled(true);
                        ButtonNavigation_FollowedArtists.setVisibility(View.VISIBLE);
                        Util.menu_open = true;
                        return;

                    }
                    if (Util.menu_open)
                    {
                        ButtonNavigation_TopArtists.setEnabled(false);
                        ButtonNavigation_TopArtists.setVisibility(View.INVISIBLE);



                        ButtonNavigation_RecentlyPlayed.setEnabled(false);
                        ButtonNavigation_RecentlyPlayed.setVisibility(View.INVISIBLE);

                        ButtonNavigation_Main.setEnabled(false);
                        ButtonNavigation_Main.setVisibility(View.INVISIBLE);

                        ButtonNavigation_FollowedArtists.setEnabled(true);
                        ButtonNavigation_FollowedArtists.setVisibility(View.VISIBLE);
                        Util.menu_open = false;
                        return;

                    }

                }
            });
            ButtonNavigation_TopArtists.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(ChatScreen.this, TopArtistScreen.class);
                    startActivity(intent);

                }

            });
            ButtonNavigation_Main.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(ChatScreen.this, MainScreen.class);
                    startActivity(intent);

                }

            });
            ButtonNavigation_RecentlyPlayed.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(ChatScreen.this, RecentlyPlayedScreen.class);
                    startActivity(intent);

                }

            });


            ButtonNavigation_TopArtists.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(ChatScreen.this, FollowedArtistScreen.class);
                    startActivity(intent);

                }

            });
            /* connect button listener */
            String serverUrl = BASE_URL + Util.current_user.displayName; //usernameEtx.getText().toString();

            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(ChatScreen.this);


            /* send button listener */
            sendBtn.setOnClickListener(v -> {
                try {

                    // send message
                    WebSocketManager.getInstance().sendMessage(msgEtx.getText().toString());
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            });
        }


        @Override
        public void onWebSocketMessage(String message) {
            /**
             * In Android, all UI-related operations must be performed on the main UI thread
             * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
             * is used to post a runnable to the UI thread's message queue, allowing UI updates
             * to occur safely from a background or non-UI thread.
             */
            runOnUiThread(() -> {
                String s = msgTv.getText().toString();
                msgTv.setText(s + "\n"+message);
            });
        }

        @Override
        public void onWebSocketClose(int code, String reason, boolean remote) {
            String closedBy = remote ? "server" : "local";
            runOnUiThread(() -> {
                String s = msgTv.getText().toString();
                msgTv.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
            });
        }

        @Override
        public void onWebSocketOpen(ServerHandshake handshakedata) {}

        @Override
        public void onWebSocketError(Exception ex) {}
    }


