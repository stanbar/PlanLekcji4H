package stasbar.com.planlekcji4h;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Stanisław on 2014-09-25.
 */
public class About extends DialogFragment implements View.OnClickListener {
    Button b1,b2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.aboutus,null);
        b1=(Button)view.findViewById(R.id.button2);
        b2= (Button) view.findViewById(R.id.button3);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        getDialog().setTitle("Jak golf to tylko w LPG");
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button2){
            Toast.makeText(getActivity(), "Lecimy kurwa tutaj", Toast.LENGTH_SHORT).show();
            dismiss();
        }
        if (v.getId()==R.id.button3){

            dismiss();
        }
    }

}
