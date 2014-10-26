package stasbar.com.planlekcji4h;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Stanisław on 2014-10-19.
 */
public class DialogTerminarz extends ListFragment{
    UsersAdapter terminarzAdapter;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setAdapter(new UsersAdapter(getActivity(),new ArrayList<Wpis>()));
        terminarzAdapter = (UsersAdapter) getListView().getAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_terminarz, container,false);


        return view;
    }

    public void updateArrayList(Wpis wpis){

        terminarzAdapter.add(wpis);
    }
}
