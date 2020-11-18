package com.example.shopstock;

import android.app.FragmentManager;
import android.os.Bundle
import android.preference.PreferenceFragment
import androidx.appcompat.app.AppCompatActivity;

class SettingsActivity extends AppCompatActivity {

        @Override
        <savedInstanceState>
        fun onCreate(savedInstanceState, Bundle?) {
            Bundle savedInstanceState = null;
            super.onCreate(savedInstanceState);

            FragmentManager fragmentManager = null;
            if (fragmentManager.findFragmentById(android.R.id.content) == null) {
        fragmentManager.beginTransaction()
        .add(android.R.id.content, new SettingsFragment()).commit()
        }
        }


class SettingsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
        }
        }
        }
