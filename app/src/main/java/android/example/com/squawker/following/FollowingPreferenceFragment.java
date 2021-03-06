/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package android.example.com.squawker.following;

import android.content.SharedPreferences;
import android.example.com.squawker.R;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;


/**
 * Shows the list of instructors you can follow
 */
// TODO (1) Implement onSharedPreferenceChangeListener
public class FollowingPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    private final static String TAG = FollowingPreferenceFragment.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Add visualizer preferences, defined in the XML file in res->xml->preferences_squawker
        addPreferencesFromResource(R.xml.following_squawker);

        Preference asserPreference = findPreference(getString(R.string.follow_key_switch_asser));
        Preference cezannePreference = findPreference(getString(R.string.follow_key_switch_cezanne));
        Preference jlinPreference = findPreference(getString(R.string.follow_key_switch_jlin));
        Preference lylaPreference = findPreference(getString(R.string.follow_key_switch_lyla));
        Preference nikitaPreference = findPreference(getString(R.string.follow_key_switch_nikita));

        asserPreference.setOnPreferenceChangeListener(this);
        cezannePreference.setOnPreferenceChangeListener(this);
        jlinPreference.setOnPreferenceChangeListener(this);
        lylaPreference.setOnPreferenceChangeListener(this);
        nikitaPreference.setOnPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "onSharedPreferenceChanged: " + key);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.d(TAG, "onPreferenceChange: " + preference.getKey());
        Log.d(TAG, "onPreferenceChange: " + newValue);

        if ((Boolean) newValue) {
            FirebaseMessaging.getInstance().subscribeToTopic(preference.getKey());
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(preference.getKey());
        }

        return true;
    }

// TODO (2) When a SharedPreference changes, check which preference it is and subscribe or
    // un-subscribe to the correct topics.

    // Ex. FirebaseMessaging.getInstance().subscribeToTopic("key_lyla");
    // subscribes to Lyla's squawks.

    // HINT: Checkout res->xml->following_squawker.xml. Note how the keys for each of the
    // preferences matches the topic to subscribe to for each instructor.

    // TODO (3) Make sure to register and unregister this as a Shared Preference Change listener, in
    // onCreate and onDestroy.


    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
