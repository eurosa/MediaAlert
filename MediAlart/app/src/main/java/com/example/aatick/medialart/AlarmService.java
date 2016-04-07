package com.example.aatick.medialart;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.GregorianCalendar;

public class AlarmService extends Service {
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
    public AlarmService() {
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
     Toast.makeText(this, "gama", Toast.LENGTH_LONG).show();
            Long time = new GregorianCalendar().getTimeInMillis();
            System.out.println("Gregorian" + time);

            Intent intentAlarm = new Intent(this, AlarmReciever.class);

            // create the object
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            // I have changed the context to this

            alarmIntent = PendingIntent.getBroadcast(this, 0, intentAlarm, 0);

//              schedule for every 60 seconds

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(),60 * 1000, alarmIntent);
        return super.onStartCommand(intent, flags, startId);
    }
}
