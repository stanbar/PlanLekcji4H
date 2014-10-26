package stasbar.com.planlekcji4h;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;


public class MyActivity extends Activity implements ActionBar.TabListener, Communicator {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    MediaPlayer mp;
    SharedPreferences sharedPreferences;
    Random rand;
    //time left
    TextView tvNazwaLekcji, tvPozostalyCzas;
    Time today = new Time(Time.getCurrentTimezone());
    TimeLeft Pozostaly = new TimeLeft();

    //notyfikacja
    NotificationCompat.Builder builder;
    Notification notification;
    NotificationManager notificationManager;

    Handler handler;

    //SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    //boolean music = getPrefs.getBoolean("checkbox", true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        rand = new Random();
        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        Intent mServiceIntent = new Intent(this, TimeLeftService.class);
        this.startService(mServiceIntent);
        //timer lekcji
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflator.inflate(R.layout.pozostaly_czas, null);

        tvNazwaLekcji = (TextView) v.findViewById(R.id.nazwa_lekcji);
        tvPozostalyCzas = (TextView)v.findViewById(R.id.pozostaly_czas);

        actionBar.setCustomView(v);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Set up MyTask


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

 /*       new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                setLessonString();
                                setTimeString();
                              updateNotification();
                                Log.d("StachujThread", "Zaktualizowano");
                            }
                        });
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

            }
        };
        */
    }

    @Override
    protected void onResume() {
        super.onResume();
        today.setToNow();
        int startposition = today.weekDay;

        int dzienTygodnia = today.weekDay;
        Log.d("today.hour", today.hour + "");
        Log.d("dzienTygodnia", dzienTygodnia+"");
        int[] tablica = Pozostaly.tablicaKoniec();
        int ostatniaLekcja = Pozostaly.getOstatniaLekcja(dzienTygodnia);
        Log.d("ostatniaLekcja", ostatniaLekcja+"");
        int ostatniaGodzina =tablica[ostatniaLekcja];
        Log.d("ostatniaGodzina", ostatniaGodzina+"");
        if ((today.hour*100) > ostatniaGodzina){

            startposition = today.weekDay+1;
            Log.d("if", "wykonany");
        }

        mViewPager.setCurrentItem(startposition, true);
        Log.d("setCurrentItem", "wykonano: setCurrentItem"+ startposition);
        setLessonString();
        setTimeString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }
    public void showDialog(){
        FragmentManager fm=getFragmentManager();
        About about = new About();
        about.show(fm,"about");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            showDialog();
        }
        if (id== R.id.action_frekwencja){
            Uri uri = Uri.parse("https://uonet.edu.gdansk.pl/moduluczen/FrekwencjaZbiorczo.aspx");
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
            Toast.makeText(this,"Frekwencja", Toast.LENGTH_SHORT).show();


        }
        if (id== R.id.action_oceny){
            Uri uri = Uri.parse("https://uonet.edu.gdansk.pl/moduluczen/OcenyZbiorczo.aspx");
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
            Toast.makeText(this,"Oceny", Toast.LENGTH_SHORT).show();
        }
        if (id== R.id.action_uliczna_rada){

            graj(rand.nextInt(5));

        }
        if (id== R.id.ustawienia){
            Intent intentSetPref = new Intent(this, Prefs.class);
            startActivity(intentSetPref);


        }
        if (id== R.id.action_terminarz){
            Uri uri = Uri.parse("http://terminarz.zsl.gda.pl/index.php?klasa=28");
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
            Toast.makeText(this,"Terminarz", Toast.LENGTH_SHORT).show();
        }
        if (id== R.id.action_zastepstwa){
            Uri uri = Uri.parse("http://terminarz.zsl.gda.pl/zastepstwa.php");
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
            Toast.makeText(this,"Zastępstwa", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void graj(int nazwa) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean checkbox = sharedPreferences.getBoolean("checkbox", true);
        if (checkbox == true) {
                switch (nazwa) {
                    case 0:
                        mp = MediaPlayer.create(this, R.raw.nie_mow_nic_nikomu);
                        break;
                    case 1:
                        mp = MediaPlayer.create(this, R.raw.przyklad);
                        break;
                    case 2:
                        mp = MediaPlayer.create(this, R.raw.paranoje);
                        break;
                    case 3:
                        mp = MediaPlayer.create(this, R.raw.lufki);
                        break;
                    case 4:
                        mp = MediaPlayer.create(this, R.raw.turysta);
                        break;
                    case 5:
                        mp = MediaPlayer.create(this, R.raw.nie_paplaj);
                        break;

                }
            mp.start();
        }
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.

        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void przeslijZastepstwo(Zastepstwo wartosc) {
        FragmentManager manager = getFragmentManager();
        DialogKlasa zastepstwaList = (DialogKlasa) manager.findFragmentByTag("zastepstwoLista");
        zastepstwaList.updateArrayList(wartosc);
    }

    @Override
    public void przeslijTerminarz(Wpis wartosc) {
        FragmentManager manager = getFragmentManager();
        DialogTerminarz terminarzLista = (DialogTerminarz) manager.findFragmentByTag("terminarzLista");
        terminarzLista.updateArrayList(wartosc);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        Fragment fragment = null;
            if(position==0){
                fragment=new stasbar.com.planlekcji4h.Menu();
            }
            if(position==1){
                fragment=new FragmentPon();
            }
            if(position==2){
                fragment=new FragmentWt();
            }
            if(position==3){
                fragment=new FragmentSr();
            }
            if(position==4){
                fragment=new FragmentCzw();
            }
            if(position==5){
                fragment=new FragmentPt();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section6).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 4:
                    return getString(R.string.title_section4).toUpperCase(l);
                case 5:
                    return getString(R.string.title_section5).toUpperCase(l);

            }
            return null;
        }
    }
    public String getLessonString() {
        today.setToNow();
        return Pozostaly.getLesson(today,Pozostaly.tablicaPoczatek(),Pozostaly.tablicaKoniec(), Pozostaly.tablicaLekcje());

    }
    public String getTimeString() {
        today.setToNow();
        return Pozostaly.getTime(today, Pozostaly.tablicaPoczatek(), Pozostaly.tablicaKoniec());

    }
    private void setLessonString(){
        today.setToNow();
        tvNazwaLekcji.setText(getLessonString());
    }
    private void setTimeString(){
        today.setToNow();
        tvPozostalyCzas.setText(getTimeString());
    }
/*
    public void createNotification() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean checkbox = sharedPreferences.getBoolean("notification", true);
        if (checkbox == true) {

            //intent
            Intent resultIntent = new Intent(this, MyActivity.class);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            builder = new NotificationCompat.Builder(this);
            builder.setAutoCancel(true);
            builder.setContentTitle(getLessonString());
            builder.setContentText(getTimeString());
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setContentIntent(resultPendingIntent);
            notification = builder.build();
            notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(8, notification);
        }

    }
    public void updateNotification(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean checkbox = sharedPreferences.getBoolean("notification", true);
        if (checkbox == true) {
            builder.setContentTitle(getLessonString());
            builder.setContentText(getTimeString());
            notificationManager.notify(8, builder.build());
        }
    }

*/



}
