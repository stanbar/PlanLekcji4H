package stasbar.com.planlekcji4h;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Menu extends Fragment implements View.OnClickListener{
    View v;
    Wpis wpis;
    Zastepstwo zastepstwo;
    DialogKlasa dialogKlasa;
    DialogTerminarz dialogTerminarz;
    Communicator comm;
    FragmentManager manager;
    FragmentTransaction transaction;
    SharedPreferences sharedPreferences;
    Document doc = null;
    boolean klasaBool;
    String klasaString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_menu, container, false);
        ImageButton btn1 = (ImageButton) v.findViewById(R.id.imageButton1);
        ImageButton btn2 = (ImageButton) v.findViewById(R.id.imageButton2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        return v;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm= (Communicator) getActivity();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        klasaBool = sharedPreferences.getBoolean("filtr", false);
        klasaString = sharedPreferences.getString("klasa", "4H");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton1:
                    dialogTerminarz = new DialogTerminarz();
                    manager = getFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_in_left, R.animator.slide_out_right);
                    transaction.replace(R.id.glownylayout, dialogTerminarz, "terminarzLista");
                    transaction.addToBackStack(null);
                    transaction.commit();

                    new MyTask().execute();

                break;
            case R.id.imageButton2:

                    dialogKlasa = new DialogKlasa();
                    manager = getFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_in_left, R.animator.slide_out_right);
                    transaction.replace(R.id.glownylayout, dialogKlasa, "zastepstwoLista");
                    transaction.addToBackStack("zastepstwoLista");
                    transaction.commit();

                    new ZastepstwaAsync().execute();

                break;
        }



    }







    class MyTask extends AsyncTask<Void, Wpis, Void>{
        String url = "http://terminarz.zsl.gda.pl/index.php?klasa=28";

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                doc = Jsoup.connect(url).get();
                // Get the html document title

                Elements dni = doc.select("td.td");
                Elements wpisy = null;
                Elements numer = null;
                Element tytuly = null;
                Element tresc = null;
                String tytulString;
                String trescString;
                String numerString;
                Time today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                for (Element div : dni) {
                    wpisy = div.select("div.wpis.PPK, div.wpis.KARTK, div.wpis.INNE, div.wpis.POW");
                    numer = div.select("div.day");
                    int numerDnia = Integer.valueOf(numer.text());
                    Log.d("dzien", numer.text() + "");
                    if(numerDnia>=today.monthDay){
                for (Element element : wpisy) {

                    tytuly = element.select("b").first();
                    tresc = element.select("p").first();

                        tytulString = tytuly.text().toString();
                        trescString = tresc.text().toString();
                        numerString = numer.text().toString();
                    if(tytuly !=null) {
                        Log.d("tytulString",tytulString);
                        Log.d("trescString",trescString);
                        Log.d("numerString",numerString);
                        int numerInt = Integer.valueOf(numerString);

                        wpis = new Wpis(numerInt, tytulString, trescString);
                        publishProgress(wpis);

                    }
                    Log.d("tresc", tresc.text() + "");
                }}}



            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onProgressUpdate(Wpis... values) {
            comm.przeslijTerminarz(values[0]);
        }

    }

    class ZastepstwaAsync extends AsyncTask<Void, Zastepstwo, Void> {
        String url2="http://terminarz.zsl.gda.pl/zastepstwa.php";


        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                doc = Jsoup.connect(url2).get();

                Element table = doc.select("#content").first();
                Elements rows = table.getElementsByTag("tr");
                Element nauczyciel=null;
                Element godzina=null;
                Element klasa=null;
                Element lekcjaPlanowa=null;
                Element nauczycielZastepujacy=null;
                Element lekcjaZamieniona=null;
                for (Element row : rows) {
                    if (!row.select("td").hasAttr("colspan")) { // sprawdzenie czy rzad nie ma colspana
                        if (row != rows.get(0) && !row.select("td").first().hasAttr("style")) { ///ominiecie rzedow z nazwami kolumn

                            if (row.select("td").first().hasAttr("rowspan")) {
                                if (klasaBool) {
                                    String temp = row.select("td").get(2).text();
                                    Log.d("Stachuj1", temp + "");
                                    if (temp.equalsIgnoreCase(klasaString)) {

                                        String nauczycielString = row.select("td").get(0).text();
                                        Log.d("nauczycielString", nauczycielString + "");

                                        godzina = row.select("td").get(1);
                                        Log.d("godzina", godzina.text() + "");
                                        klasa = row.select("td").get(2);
                                        Log.d("klasa", klasa.text() + "");

                                        lekcjaPlanowa = row.select("td").get(3);
                                        Log.d("lekcjaPlanowa", lekcjaPlanowa.text() + "");

                                        nauczycielZastepujacy = row.select("td").get(4);
                                        Log.d("nauczycielZastepujacy", nauczycielZastepujacy.text() + "");

                                        lekcjaZamieniona = row.select("td").get(5);
                                        Log.d("lekcjaZamieniona", lekcjaZamieniona.text() + "");

                                        zastepstwo = new Zastepstwo(nauczycielString, godzina.text(), klasa.text(), lekcjaPlanowa.text(), nauczycielZastepujacy.text(), lekcjaZamieniona.text());
                                        publishProgress(zastepstwo);
                                    }
                                } else {
                                    String nauczycielString = row.select("td").get(0).text();
                                    Log.d("nauczycielString", nauczycielString + "");

                                    godzina = row.select("td").get(1);
                                    Log.d("godzina", godzina.text() + "");
                                    klasa = row.select("td").get(2);
                                    Log.d("klasa", klasa.text() + "");

                                    lekcjaPlanowa = row.select("td").get(3);
                                    Log.d("lekcjaPlanowa", lekcjaPlanowa.text() + "");

                                    nauczycielZastepujacy = row.select("td").get(4);
                                    Log.d("nauczycielZastepujacy", nauczycielZastepujacy.text() + "");

                                    lekcjaZamieniona = row.select("td").get(5);
                                    Log.d("lekcjaZamieniona", lekcjaZamieniona.text() + "");

                                    zastepstwo = new Zastepstwo(nauczycielString, godzina.text(), klasa.text(), lekcjaPlanowa.text(), nauczycielZastepujacy.text(), lekcjaZamieniona.text());
                                    publishProgress(zastepstwo);
                                }
                            }else{
                                if (klasaBool) {
                                    String temp = row.select("td").get(1).text();
                                    Log.d("Stachuj2", temp + "");
                                    if (temp.equalsIgnoreCase(klasaString)) {

                                        godzina = row.select("td").get(0);
                                        Log.d("godzina", godzina.text() + "");
                                        klasa = row.select("td").get(1);
                                        Log.d("klasa", klasa.text() + "");

                                        lekcjaPlanowa = row.select("td").get(2);
                                        Log.d("lekcjaPlanowa", lekcjaPlanowa.text() + "");

                                        nauczycielZastepujacy = row.select("td").get(3);
                                        Log.d("nauczycielZastepujacy", nauczycielZastepujacy.text() + "");

                                        lekcjaZamieniona = row.select("td").get(4);
                                        Log.d("lekcjaZamieniona", lekcjaZamieniona.text() + "");
                                        zastepstwo = new Zastepstwo(godzina.text(), klasa.text(), lekcjaPlanowa.text(), nauczycielZastepujacy.text(), lekcjaZamieniona.text());
                                        publishProgress(zastepstwo);
                                    }
                                } else {
                                    godzina = row.select("td").get(0);
                                    Log.d("godzina", godzina.text() + "");
                                    klasa = row.select("td").get(1);
                                    Log.d("klasa", klasa.text() + "");

                                    lekcjaPlanowa = row.select("td").get(2);
                                    Log.d("lekcjaPlanowa", lekcjaPlanowa.text() + "");

                                    nauczycielZastepujacy = row.select("td").get(3);
                                    Log.d("nauczycielZastepujacy", nauczycielZastepujacy.text() + "");

                                    lekcjaZamieniona = row.select("td").get(4);
                                    Log.d("lekcjaZamieniona", lekcjaZamieniona.text() + "");
                                    zastepstwo = new Zastepstwo(godzina.text(), klasa.text(), lekcjaPlanowa.text(), nauczycielZastepujacy.text(), lekcjaZamieniona.text());
                                    publishProgress(zastepstwo);
                                }

                            }

                        }


                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }
        protected void onProgressUpdate(Zastepstwo... values) {
            comm.przeslijZastepstwo(values[0]);
        }

    }


}

