package com.muhaammaad.metarpolite.ui.main.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.IdlingResource;

import com.muhaammaad.metarpolite.R;
import com.muhaammaad.metarpolite.global.IdlingResource.SimpleIdlingResource;

/**
 * Launcher Activity responsible to show fragment with list of metars
 */
public class MainActivity extends AppCompatActivity implements SimpleIdlingResource.IdlingDelegate {

    // The Idling Resource which will be null in production.
    @Nullable
    public SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MetarListFragment.newInstance())
                    .commitNow();
        }
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    /**
     * Invoked from activity's further components.
     */
    @Override
    public void setIdleState(boolean isIdleNow) {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(isIdleNow);
        }
    }
}
