package stasbar.com.planlekcji4h;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


/**
 * Created by Stanisław on 2014-10-08.
 */
public class Browser extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.browser,container,false);
        TextView tv = (TextView) v.findViewById(R.id.textView);

        return v;
    }
}

