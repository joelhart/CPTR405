package edu.wallawalla.cs.hartjo.joelapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Change the theme if preference is true
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkTheme = sharedPrefs.getBoolean("theme", false);
        if (darkTheme) {
            setTheme(R.style.DarkTheme);
            setContentView(R.layout.settings_activity);
        }

        setContentView(R.layout.activity_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            fragment = new ListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }
    }
}