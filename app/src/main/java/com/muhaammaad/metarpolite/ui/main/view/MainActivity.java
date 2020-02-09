package com.muhaammaad.metarpolite.ui.main.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.muhaammaad.metarpolite.R;
import com.muhaammaad.metarpolite.global.appContext;

/**
 * Launcher Activity responsible to show fragment with list of metars
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        appContext.context = getApplicationContext();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}
