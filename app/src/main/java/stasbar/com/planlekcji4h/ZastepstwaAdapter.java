package stasbar.com.planlekcji4h;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Stanisław on 2014-10-17.
 */
public class ZastepstwaAdapter extends ArrayAdapter<Zastepstwo> {
    public ZastepstwaAdapter(Context context, ArrayList<Zastepstwo> zastepstwa) {
        super(context, 0, zastepstwa);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Zastepstwo zastepstwo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.zastepstwo, parent, false);
        }
        // Lookup view for data population
        TextView tvNauczycielNieobecny = (TextView) convertView.findViewById(R.id.tvNauczycielNieobecny);
        TextView tvGodzinaLekcyjna = (TextView) convertView.findViewById(R.id.tvGodzinaLekcyjna);
        TextView tvKlasa = (TextView) convertView.findViewById(R.id.tvKlasa);
        TextView tvLekcjaPlanowana = (TextView) convertView.findViewById(R.id.tvLekcjaPlanowana);
        TextView tvNauczycielZastepujacy = (TextView) convertView.findViewById(R.id.tvNauczycielZastepujacy);
        TextView tvLekcjaZamieniona = (TextView) convertView.findViewById(R.id.tvLekcjaZamieniona);
        // Populate the data into the template view using the data object

        tvNauczycielNieobecny.setText(zastepstwo.nauczycielNieobecny);
        tvGodzinaLekcyjna.setText(zastepstwo.godzina);
        tvKlasa.setText(zastepstwo.klasa);
        tvLekcjaPlanowana.setText(zastepstwo.lekcjaPlanowana);
        tvNauczycielZastepujacy.setText(zastepstwo.nauczycielZastepujacy);
        tvLekcjaZamieniona.setText(zastepstwo.lekcjaZamieniona);
        // Return the completed view to render on screen
        return convertView;
    }
}
