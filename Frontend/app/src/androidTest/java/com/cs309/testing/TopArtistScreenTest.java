package com.cs309.testing;

import com.gr0m.quicksc.*;
import com.gr0m.quicksc.R;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;

@RunWith(AndroidJUnit4.class)
public class TopArtistScreenTest {

    @Rule
    public ActivityScenarioRule<TopArtistScreen> activityScenarioRule = new ActivityScenarioRule<>(TopArtistScreen.class);

    @Before
    public void setUp() {
        Util.service = new StubSpotifyServer();
        Util.current_user = createFakeUserProfile();
        activityScenarioRule = new ActivityScenarioRule<>(TopArtistScreen.class);
    }

    @Test
    public void testArtistGridLayoutPopulation() {
        onView(withId(R.id.trackGrid)).check(matches(hasMinimumChildCount(1)));
    }
    @Test
    public void testNavigationButtonVisibility() {
        onView(withId(R.id.Navigation_TopArtists)).check(matches(isDisplayed()));
    }



    private API.GetBackendUserProfile createFakeUserProfile() {
        API.GetBackendUserProfile fakeUser = new API.GetBackendUserProfile();
        fakeUser.displayName = "Fake User";
        fakeUser.email = "fakeuser@example.com";
        fakeUser.spotify = "fakeSpotifyId";
        fakeUser.pictureURL = "http://fakeimage.com/fake.jpg";
        return fakeUser;
    }

    private static class StubSpotifyServer extends SpotifyServer {
        public StubSpotifyServer() {
            super(8080);
        }

        @Override
        public <T> T SpotifyAPICall(String url, Class<T> type) {
            if (type.equals(API.UserTopArtists.class)) {
                return (T) TestData.getMockUserTopArtists();
            }
            return null;
        }
    }
}