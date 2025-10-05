package com.cs309.testing;

import com.gr0m.quicksc.*;
import com.gr0m.quicksc.R;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;

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
public class SearchSongScreenTest {

    @Rule
    public ActivityScenarioRule<SearchScreen> activityScenarioRule = new ActivityScenarioRule<>(SearchScreen.class);

    @Before
    public void setUp() {
        Util.service = new StubSpotifyServer();
        Util.current_user = createFakeUserProfile();
    }

    @Test
    public void testNavigationButtonVisibility() {
        // Check if the main navigation button is visible
        onView(withId(R.id.Navigation_Main)).check(matches(isDisplayed()));
    }
    @Test
    public void testPerformSearchOperation2() {
        // Simulate typing a search query
        onView(withId(R.id.searchEditText)).perform(typeText("Sample"), closeSoftKeyboard());

        // Simulate clicking the search button
        onView(withId(R.id.searchButton)).perform(click());

        // Check if the search results are populated with at least one item
        onView(withId(R.id.searchResultsLayout)).check(matches(hasMinimumChildCount(1)));
    }
    @Test
    public void testPerformSearchOperation() {
        // Simulate typing a search query
        onView(withId(R.id.searchEditText)).perform(typeText("Sample"), closeSoftKeyboard());

        // Simulate clicking the search button
        onView(withId(R.id.searchButton)).perform(click());

    }

    @Test
    public void testSearchResultsPopulation() {
        onView(withId(R.id.searchEditText)).perform(typeText("Sample"), closeSoftKeyboard());

        // Simulate clicking the search button
        onView(withId(R.id.searchButton)).perform(click());

        // Wait for a few seconds to allow the search results to load (adjust the timing as needed)
        try {
            Thread.sleep(3000); // Wait for 3 seconds (you can adjust this time)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the search results are populated with at least one item
        onView(withId(R.id.searchResultsLayout)).check(matches(hasMinimumChildCount(1)));
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
            if (type.equals(API.SearchSong.class)) {
                // Immediately return mock data instead of doing a real network call
                return (T) TestData.getMockSearchSong();
            }
            return null;
        }
    }
}
