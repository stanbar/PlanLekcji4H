package stasbar.com.planlekcji4h;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

/**
 * Created by Stanisław on 2014-09-27.
 */
public class PrefsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.prefs);

        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        ListPreference listPreferencePref = (ListPreference) findPreference("klasa");
        listPreferencePref
                .setSummary(sp.getString("klasa", "Wybierz klase"));

    }
}
