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
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;

@RunWith(AndroidJUnit4.class)
public class SearchAlbumsScreenTest {

    @Rule
    public ActivityScenarioRule<SearchAlbums> activityScenarioRule = new ActivityScenarioRule<>(SearchAlbums.class);

    @Before
    public void setUp() {
        Util.service = new StubSpotifyServer();
        Util.current_user = createFakeUserProfile();
    }

    @Test
    public void testNavigationButtonVisibility() {
        onView(withId(R.id.Navigation_Main)).check(matches(isDisplayed()));
    }

    @Test
    public void testPerformSearchOperation() {
        onView(withId(R.id.searchEditText)).perform(typeText("Sample Album"), closeSoftKeyboard());
        onView(withId(R.id.searchButton)).perform(click());

        // Simulate a delay for loading search results
        try {
            Thread.sleep(3000); // Adjust delay as needed
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the search results layout is populated with at least one item
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
            if (type.equals(API.SearchAlbum.class)) {
                return (T) TestData.getMockSearchAlbum();
            }
            return null;
        }
    }

    // Additional methods or test cases can be added here
}

