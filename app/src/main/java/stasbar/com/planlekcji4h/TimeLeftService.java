package stasbar.com.planlekcji4h;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.text.format.Time;

/**
 * Created by Stanisław on 2014-10-22.
 */
public class TimeLeftService extends IntentService {
    private static final int MY_NOTIFICATION_ID=8;
    NotificationManager notificationManager;
    Notification myNotification;
    boolean checkbox;
    String lession, time;
    Time today = new Time(Time.getCurrentTimezone());
    TimeLeft Pozostaly;

    public TimeLeftService() {
        super("stasbar.com.planlekcji4h.TimeLeftService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        checkbox = sharedPreferences.getBoolean("notification", true);
        Pozostaly = new TimeLeft();

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while(true) {


            if(checkbox){
                Intent resultIntent = new Intent(this, MyActivity.class);
                PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(
                                this,
                                0,
                                resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setAutoCancel(true);
                today.setToNow();
                lession = Pozostaly.getLesson(today,Pozostaly.tablicaPoczatek(),Pozostaly.tablicaKoniec(), Pozostaly.tablicaLekcje());
                time = Pozostaly.getTime(today, Pozostaly.tablicaPoczatek(), Pozostaly.tablicaKoniec());
                builder.setContentTitle(lession);
                builder.setContentText(time);
                builder.setSmallIcon(R.drawable.ic_launcher);
                builder.setContentIntent(resultPendingIntent);
                myNotification = builder.build();
                notificationManager.notify(8, myNotification);
            }
            try {
                Thread.sleep(30000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
