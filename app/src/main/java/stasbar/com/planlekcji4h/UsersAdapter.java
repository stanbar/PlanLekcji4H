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
public class UsersAdapter extends ArrayAdapter<Wpis> {
    public UsersAdapter(Context context, ArrayList<Wpis> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Wpis user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.wpis_terminarza, parent, false);
        }
        // Lookup view for data population
        TextView tvDay = (TextView) convertView.findViewById(R.id.DzienWpisu);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        // Populate the data into the template view using the data object

        tvName.setText(user.nazwaLekcji);
        tvHome.setText(user.opis);
        tvDay.setText(user.getDzien());
        // Return the completed view to render on screen
        return convertView;
    }
}
