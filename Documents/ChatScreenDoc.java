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

/**
 * Represents the chat screen where users can send and receive messages via WebSocket.
 */
public class ChatScreen extends AppCompatActivity implements WebSocketListener {

    /**
     * The base URL for WebSocket connection.
     */
    private String BASE_URL = "ws://coms-309-057.class.las.iastate.edu:8080/chat/";

    /**
     * Button for sending messages.
     */
    private Button sendBtn;

    /**
     * EditText for entering messages.
     */
    private EditText msgEtx;

    /**
     * TextView for displaying chat messages.
     */
    private TextView msgTv, usernameEtx;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                            shut down, this Bundle contains the data it most recently supplied
     *                            in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_screen);

        // Initialize UI elements
        sendBtn = findViewById(R.id.bt2);
        msgEtx = findViewById(R.id.et2);
        msgTv = findViewById(R.id.tx1);

        Button ButtonNavigation = findViewById(R.id.Navigation);
        Button ButtonNavigation_TopArtists = findViewById(R.id.Navigation_TopArtists);
        Button ButtonNavigation_Main = findViewById(R.id.Navigation_Main);
        Button ButtonNavigation_RecentlyPlayed = findViewById(R.id.Navigation_RecentlyPlayed);
        Button ButtonNavigation_FollowedArtists = findViewById(R.id.Navigation_FollowedArtists);

        // Set initial visibility and enable states for navigation buttons
        setNavigationButtonStates(ButtonNavigation_TopArtists, ButtonNavigation_Main, ButtonNavigation_RecentlyPlayed, ButtonNavigation_FollowedArtists);

        // Set click listener for the main navigation button
        ButtonNavigation.setOnClickListener(this::onNavigationButtonClick);

        // Set click listeners for navigation menu buttons
        ButtonNavigation_TopArtists.setOnClickListener(this::onTopArtistsButtonClick);
        ButtonNavigation_Main.setOnClickListener(this::onMainButtonClick);
        ButtonNavigation_RecentlyPlayed.setOnClickListener(this::onRecentlyPlayedButtonClick);
        ButtonNavigation_FollowedArtists.setOnClickListener(this::onFollowedArtistsButtonClick);

        // Connect to WebSocket
        String serverUrl = BASE_URL + Util.current_user.displayName;
        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(ChatScreen.this);

        // Send button click listener
        sendBtn.setOnClickListener(v -> {
            try {
                // Send message
                WebSocketManager.getInstance().sendMessage(msgEtx.getText().toString());
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage());
            }
        });
    }

    /**
     * Sets the initial visibility and enable states for navigation buttons.
     *
     * @param buttons Buttons to be configured.
     */
    private void setNavigationButtonStates(Button... buttons) {
        for (Button button : buttons) {
            button.setEnabled(false);
            button.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Handles the click event for the navigation button.
     *
     * @param view The view that was clicked.
     */
    private void onNavigationButtonClick(View view) {
        if (!Util.menu_open) {
            setNavigationButtonStates(ButtonNavigation_TopArtists, ButtonNavigation_Main, ButtonNavigation_RecentlyPlayed, ButtonNavigation_FollowedArtists);
            Util.menu_open = true;
            return;
        }
        if (Util.menu_open) {
            setNavigationButtonStates(ButtonNavigation_TopArtists, ButtonNavigation_Main, ButtonNavigation_RecentlyPlayed, ButtonNavigation_FollowedArtists);
            Util.menu_open = false;
        }
    }

    /**
     * Handles the click event for the "Top Artists" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onTopArtistsButtonClick(View view) {
        startActivity(new Intent(ChatScreen.this, TopArtistScreen.class));
    }

    /**
     * Handles the click event for the "Main" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onMainButtonClick(View view) {
        startActivity(new Intent(ChatScreen.this, MainScreen.class));
    }

    /**
     * Handles the click event for the "Recently Played" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onRecentlyPlayedButtonClick(View view) {
        startActivity(new Intent(ChatScreen.this, RecentlyPlayedScreen.class));
    }

    /**
     * Handles the click event for the "Followed Artists" button in the navigation menu.
     *
     * @param view The view that was clicked.
     */
    private void onFollowedArtistsButtonClick(View view) {
        startActivity(new Intent(ChatScreen.this, FollowedArtistScreen.class));
    }

    /**
     * Called when a message is received through WebSocket.
     *
     * @param message The received message.
     */
    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "\n" + message);
        });
    }

    /**
     * Called when the WebSocket connection is closed.
     *
     * @param code   The status code of the closure.
     * @param reason The reason for the closure.
     * @param remote True if the connection was closed by the server, false if closed locally.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    /**
     * Called when the WebSocket connection is established.
     *
     * @param handshakedata The handshake data.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    /**
     * Called when an error occurs with the WebSocket connection.
     *
     * @param ex The exception representing the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {}
}
