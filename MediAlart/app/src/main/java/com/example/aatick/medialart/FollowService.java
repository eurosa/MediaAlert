package com.example.aatick.medialart;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class FollowService extends Service {
    public static final String CREATE = "CREATE";
    public static final String CANCEL = "CANCEL";
    private SQLiteDatabase newDB;
    private ArrayList<String> results=new ArrayList<String>();
    private PendingIntent alarmIntent;
    private PendingIntent alarmIntent_follow;
    String dateString1;
    private IntentFilter matcher;
    String dateString2;
    String currentDateTime;
    String dateString;
    String database_time;
    public FollowService() {
        super();
        matcher = new IntentFilter();
        matcher.addAction(CREATE);
        matcher.addAction(CANCEL);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Long time = new GregorianCalendar().getTimeInMillis();
        //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class

        Intent intentAlarm_follow = new Intent(this, FollowReceiver.class);

        // create the object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, AlarmReceiver.class);
        // I have changed the context to this

        alarmIntent_follow = PendingIntent.getBroadcast(this, 0, intentAlarm_follow, 0);
//          schedule for every 10 seconds
        Toast.makeText(FollowService.this, "dgdgdgd", Toast.LENGTH_SHORT).show();

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, 60 * 1000, alarmIntent_follow);

        return super.onStartCommand(intent, flags, startId);
    }

}
