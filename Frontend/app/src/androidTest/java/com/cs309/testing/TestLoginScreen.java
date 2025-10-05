package com.cs309.testing;
import  com.gr0m.quicksc.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class TestLoginScreen {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule // needed to launch the activity
    public ActivityScenarioRule<LoginScreen> activityScenarioRule = new ActivityScenarioRule<>(LoginScreen.class);

    @Test
    public void testSpotifyAuthorizationButtonVisibility() {
        // Check if the Spotify Authorization Button is visible when the access token is empty
        if (Util.spotify_access_token.access_token.trim().isEmpty()) {
            // Put thread to sleep to allow volley to handle the request
            try {
                Thread.sleep(SIMULATED_DELAY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            onView(withId(R.id.LoginScreen_SpotifyAuthorizationButton)).check(matches(isDisplayed()));
        }
    }
}
