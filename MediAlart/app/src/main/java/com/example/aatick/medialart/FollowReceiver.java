package com.example.aatick.medialart;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
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
import android.os.CountDownTimer;
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

public class FollowReceiver extends BroadcastReceiver {
    public FollowReceiver() {
    }

    MediaPlayer player;


    private SQLiteDatabase newDB;

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
    Dialog alarmDialog;
    String doctorName = null;
    String location = null;
    Ringtone r;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        //query for date and time matching to show setting alarm data

        Date date1 = null;
        try {
//            Calendar c = Calendar.getInstance();
//      System.out.println("Current time => " + c.getTime());
            date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSSSSSSS").parse(String.valueOf(timestamp));
//            date1 = new SimpleDateFormat("E yyyy.MM.dd  hh:mm:sss").parse(String.valueOf(c));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        currentDateTime1 = new SimpleDateFormat("hh:mm").format(date1);
        currentDateTime2 = new SimpleDateFormat("yyyy-MM-dd").format(date1);
//        System.out.println("mm" + currentDateTime1 + " " + currentDateTime2);
        curentDateTime = currentDateTime2 + " " + currentDateTime1;
        System.out.println("getready"+curentDateTime);
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(context.getApplicationContext());
            newDB = dbHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT doctor_name, follow_date,follow_time ,doctor_location, disease_name FROM media_follower where id >0", null);

            if (c != null) {
                while (c.moveToNext()) {
                    doctorName = c.getString(c.getColumnIndex("doctor_name"));
                    date_alarm = c.getString(c.getColumnIndex("follow_date"));
                    time_alarm = c.getString(c.getColumnIndex("follow_time"));
                    location = c.getString(c.getColumnIndex("doctor_location"));

                    String time_interval_alarm = c.getString(c.getColumnIndex("disease_name"));

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
                    database_time = dateString2 + " " + dateString;

                    System.out.println("getready_for"+database_time);
                    System.out.println("loney"+curentDateTime.equals(database_time));
                    if (curentDateTime.equals(database_time)) {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                            r = RingtoneManager.getRingtone(context.getApplicationContext(), notification);
                            r.play();
                        alarmDialog = new Dialog(context);
                        alarmDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        alarmDialog.setContentView(R.layout.alarm_layout);
                        alarmDialog.setTitle("Alarm is ringing");
                        TextView tvName =(TextView)alarmDialog.findViewById(R.id.tv_medicineName);
                        tvName.setText("Please visit "+doctorName+"in "+time_alarm);
                        final Button btnOk =(Button)alarmDialog.findViewById(R.id.btn_ok);
                        Button btnCancel = (Button)alarmDialog.findViewById(R.id.btn_cancel);

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                r.stop();
                                alarmDialog.dismiss();
                            }
                        });
                        alarmDialog.show();

                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                r.stop();
                                alarmDialog.dismiss();

                                CountDownTimer timer = new CountDownTimer(40000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        r.play();
                                        alarmDialog.show();
                                    }
                                }.start();
                            }
                        });
                        results.add("Name: " + doctorName);
                        Log.d("radhason_power", database_time);
                    }
                }

            }

        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

    }
}
