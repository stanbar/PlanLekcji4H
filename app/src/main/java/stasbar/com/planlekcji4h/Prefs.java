package stasbar.com.planlekcji4h;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Stanisław on 2014-09-27.
 */
public class Prefs extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();
    }
}
