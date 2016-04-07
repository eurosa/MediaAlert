package com.example.aatick.medialart;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {
    MediaPlayer player;


    private SQLiteDatabase newDB;
    private  DataBaseHelper helper;
    private PendingIntent alarmIntent;
    String dateString1 = null;
    private IntentFilter matcher;
    String dateString2 = null;
    String currentDateTime;
    String dateString = null;
    String database_time = null;
    String currentDateTime1 = null;
    String time_alarm = null;
    String curentDateTime = null;
    String currentDateTime2 = null;
    private ArrayList<String> results = new ArrayList<String>();
    String date_alarm = null;
    String time_interval_alarm=null;
    String med_id=null;
    int time_interval_medi;
    String description;
    int update_dosage;
    String sms_mobile;
    int update_stock;
    int net_updated_stock;
    int i;
    Dialog alarmDialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        //query for date and time matching to show setting alarm data

        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSSSSSSS").parse(String.valueOf(timestamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        currentDateTime1 = new SimpleDateFormat("hh:mm").format(date1);
        currentDateTime2 = new SimpleDateFormat("yyyy-MM-dd").format(date1);
        curentDateTime = currentDateTime2 + " " + currentDateTime1;
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(context.getApplicationContext());
            newDB = dbHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT id,sms_medicine,sms_date,sms_time,sms_description,sms_mobile FROM sms_table where id >0", null);
            if (c != null) {
                while (c.moveToNext()) {
                    med_id=c.getString(c.getColumnIndex("id"));
                    String medicineName = c.getString(c.getColumnIndex("sms_medicine"));
                    date_alarm = c.getString(c.getColumnIndex("sms_date"));
                    time_alarm = c.getString(c.getColumnIndex("sms_time"));
                    description =c.getString(c.getColumnIndex("sms_description"));
                    sms_mobile =c.getString(c.getColumnIndex("sms_mobile"));
                    dateString1 = date_alarm;
                    Date timeFormatter = null;
                    Date date2 = null;
                    try {

                        date2 = new SimpleDateFormat("yyyy-mm-dd").parse(dateString1);
                        timeFormatter = new SimpleDateFormat("h:mm a").parse(time_alarm);
                        dateString2 = new SimpleDateFormat("yyyy-mm-dd").format(date2);
                        dateString = new SimpleDateFormat("hh:mm").format(timeFormatter);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.d("phoned",description);
                    System.out.println("time_interval" +curentDateTime.equals(database_time));
                    database_time = dateString2 + " " + dateString;
                    if (curentDateTime.equals(database_time)) {

                        String phoneNumberReciver=sms_mobile;
                        // phone number to which SMS to be send
                        String message=description;// message to send
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(phoneNumberReciver, null, message, null, null);
                        // Show the toast  like in above screen shot
                        Toast.makeText(context, "Alarm Triggered and SMS Sent", Toast.LENGTH_LONG).show();
                    }
                    i++;
                }
            }
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }
}

