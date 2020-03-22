package com.muhaammaad.metarpolite;

import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.muhaammaad.metarpolite.ui.main.adapter.MetarDataAdapter;
import com.muhaammaad.metarpolite.ui.main.view.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.internal.util.Checks.checkArgument;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;

/**
 * Instrumented test, which will execute on an Android device.
 * <p>
 * Test class showcasing some {@link RecyclerViewActions} from Espresso.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MetarListDetailTest {

    private static final String RANDOM_STATION_ID = "ETIH";
    private static final int RANDOM_POSITION = 1;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

    private IdlingResource mIdlingResource;

    /**
     * Use {@link ActivityScenario to launch and get access to the activity.
     * {@link ActivityScenario#onActivity(ActivityScenario.ActivityAction)} provides a thread-safe
     * mechanism to access the activity.
     */
    @Before
    public void registerIdlingResource() {
        ActivityScenario activityScenario = activityScenarioRule.getScenario();
        activityScenario.onActivity((ActivityScenario.ActivityAction<MainActivity>) activity -> {
            mIdlingResource = activity.getIdlingResource();
            // To prove that the test fails, omit this call:
            IdlingRegistry.getInstance().register(mIdlingResource);
        });
    }

    @Test
    public void performActionOnGivenPosition() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.rvMetars))
                .perform(RecyclerViewActions.actionOnItemAtPosition(RANDOM_POSITION, click()));
    }

    @Test
    public void scrollToPositionAndClick() {
        // First, scroll to the view holder using the isInTheMiddle matcher.
        onView(withId(R.id.rvMetars))
                .perform(scrollToHolder(new CustomViewHolderMatcher()).atPosition(RANDOM_POSITION), click());
    }

    @Test
    public void findTextAndscrollToPositionAndClick() {
        onView(withId(R.id.rvMetars)).perform(scrollToHolder(withHolder(RANDOM_STATION_ID)));
        onView(withItemText(RANDOM_STATION_ID)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    private static class CustomViewHolderMatcher extends TypeSafeMatcher<RecyclerView.ViewHolder> {
        private Matcher<View> itemMatcher = any(View.class);

        CustomViewHolderMatcher() {
        }

        public CustomViewHolderMatcher(Matcher<View> itemMatcher) {
            this.itemMatcher = itemMatcher;
        }

        @Override
        public boolean matchesSafely(RecyclerView.ViewHolder viewHolder) {

            return MetarDataAdapter.MetarViewHolder.class.isAssignableFrom(viewHolder.getClass())
                    && itemMatcher.matches(viewHolder.itemView);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("is assignable from CustomViewHolder");
        }
    }

    /**
     * Matches a {@link android.widget.TextView} text with a rvLayoutId as parent.
     */
    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        final boolean[] found = {false};
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                if (found[0])
                    return false;
                found[0] = allOf(isDescendantOfA(withId(R.id.rvMetars)), withText(itemText)).matches(item);
                return found[0];
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA RV with text " + itemText);
            }
        };
    }

    /**
     * Matches a binded {@link android.widget.TextView} text.
     */
    private Matcher<MetarDataAdapter.MetarViewHolder> withHolder(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        final boolean[] founds = {false};
        return new TypeSafeMatcher<MetarDataAdapter.MetarViewHolder>() {
            @Override
            public boolean matchesSafely(MetarDataAdapter.MetarViewHolder item) {
                item.metarListItemBinding.executePendingBindings();
                if (founds[0])
                    return false;
                founds[0] = item.metarListItemBinding.tvStationId.getText().equals(itemText);
                return founds[0];
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA RV with text " + itemText);
            }
        };
    }
}

