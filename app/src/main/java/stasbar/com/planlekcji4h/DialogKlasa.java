package stasbar.com.planlekcji4h;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Stanisław on 2014-10-18.
 */
public class DialogKlasa extends ListFragment  {
    ZastepstwaAdapter zastepstwaAdapter;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setAdapter(new ZastepstwaAdapter(getActivity(),new ArrayList<Zastepstwo>()));
        zastepstwaAdapter = (ZastepstwaAdapter) getListView().getAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_klasa, container,false);


        return view;
    }

    public void updateArrayList(Zastepstwo zastepstwo){

        zastepstwaAdapter.add(zastepstwo);
    }
}
