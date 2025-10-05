package com.cs309.testing;

import com.gr0m.quicksc.*;
import com.gr0m.quicksc.R;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static org.hamcrest.Matchers.greaterThan;


@RunWith(AndroidJUnit4.class)
public class TopTrackScreenTest {

    @Rule
    public ActivityScenarioRule<TopTrackScreen> activityScenarioRule = new ActivityScenarioRule<>(TopTrackScreen.class);
    @Before
    public void setUp() {
        // Use a stubbed SpotifyServer instead of the real one
        Util.service = new StubSpotifyServer();
        Util.current_user = createFakeUserProfile();
    }

    @Test
    public void testNavigationButtonVisibility() {
        // Check if a specific navigation button is visible
        onView(withId(R.id.Navigation)).check(matches(isDisplayed()));
    }
    @Test
    public void testNavigationButtonVisibility2() {
        // Check if a specific navigation button is visible
        onView(withId(R.id.Navigation)).check(matches(isDisplayed()));
    }

    @Test
    public void testTrackGridLayoutPopulation() {
        // Check if the GridLayout for tracks is populated with at least one item
        onView(withId(R.id.trackGrid)).check(matches(hasMinimumChildCount(1)));

    }
    private API.GetBackendUserProfile createFakeUserProfile() {
        // Create and return a fake user profile
        API.GetBackendUserProfile fakeUser = new API.GetBackendUserProfile();
        fakeUser.displayName = "Fake User";
        fakeUser.email = "fakeuser@example.com";
        fakeUser.spotify = "fakeSpotifyId";
        fakeUser.pictureURL = "http://fakeimage.com/fake.jpg";
        // ... set other necessary fields

        return fakeUser;
    }
    private static class StubSpotifyServer extends SpotifyServer {
        public StubSpotifyServer() {
            super(8080); // Use a suitable port
        }

        @Override
        public <T> T SpotifyAPICall(String url, Class<T> type) {
            // Return a mock object depending on the type
            if (type.equals(API.UserTopTracks.class)) {
                return (T) TestData.getMockUserTopTracks(); // Ensure this returns a valid mock object
            }
            // Handle other types if needed
            return null;
        }
    }

}
