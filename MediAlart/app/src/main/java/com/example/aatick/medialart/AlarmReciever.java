package com.example.aatick.medialart;

import android.app.AlarmManager;
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
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.telephony.gsm.SmsManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class AlarmReciever extends BroadcastReceiver {
    //    final Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), notification);
//    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    MediaPlayer player;
    Context context = this.context;
    private SQLiteDatabase newDB;
    private DataBaseHelper helper;
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
    String time_interval_alarm = null;
    String med_id = null;
    int time_interval_medi;
    String dosage_get;
    int update_dosage;
    String stock_get;
    int update_stock;
    int net_updated_stock;
    int i;
    Dialog alarmDialog;
    boolean isplaying = false;

    @Override
    public void onReceive(final Context context, Intent intent) {
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
                Cursor c = newDB.rawQuery("SELECT id,medicine_name, date,time_interval,time,dosage,stock FROM media_store where id >0", null);

                if (c != null) {
                    while (c.moveToNext()) {
                        med_id = c.getString(c.getColumnIndex("id"));
                        String medicineName = c.getString(c.getColumnIndex("medicine_name"));
                        date_alarm = c.getString(c.getColumnIndex("date"));
                        time_alarm = c.getString(c.getColumnIndex("time"));
                        dosage_get = c.getString(c.getColumnIndex("dosage"));
                        String dose = c.getString(c.getColumnIndex("dosage"));
                        stock_get = c.getString(c.getColumnIndex("stock"));
                        time_interval_alarm = c.getString(c.getColumnIndex("time_interval"));
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
                        Uri notification  = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                        database_time = dateString2 + " " + dateString;
                       final Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), notification);

                        if (curentDateTime.equals(database_time)) {
                            isplaying = true;
                            alarmDialog = new Dialog(context);
                            alarmDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                            alarmDialog.setContentView(R.layout.alarm_layout);
                            alarmDialog.setTitle("Alarm is ringing");
                            TextView tvName = (TextView) alarmDialog.findViewById(R.id.tv_medicineName);
                            tvName.setText("Please take " + dose + " " + medicineName);
                            final Button btnOk = (Button) alarmDialog.findViewById(R.id.btn_ok);
                            Button btnCancel = (Button) alarmDialog.findViewById(R.id.btn_cancel);
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
                                    Intent intent = new Intent(context, this.getClass());
                                    PendingIntent pendingIntent =
                                            PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                                    long currentTimeMillis = System.currentTimeMillis();
                                    long nextUpdateTimeMillis = currentTimeMillis + 1 * DateUtils.MINUTE_IN_MILLIS;
                                    Time nextUpdateTime = new Time(nextUpdateTimeMillis);
                                    nextUpdateTime.setTime(nextUpdateTimeMillis);
                                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                                    alarmManager.set(AlarmManager.RTC, nextUpdateTimeMillis, pendingIntent);
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
                            Date date6 = null;
                            try {
                                date6 = new SimpleDateFormat("hh:mm a").parse(time_alarm);
                                update_stock = Integer.parseInt(stock_get);
                                update_dosage = Integer.parseInt(dosage_get);
                                net_updated_stock = (update_stock - update_dosage);
                                time_interval_medi = Integer.parseInt(time_interval_alarm);
                                System.out.println("time_interval" + time_interval_medi);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date6);
                                calendar.add(Calendar.HOUR, time_interval_medi);
                                String dateString2r = new SimpleDateFormat("hh:mm a").format(calendar.getTime());
                                System.out.println("Time here " + dateString2r);
                                System.out.println("Valueid" + med_id);
                                ContentValues cv = new ContentValues();
                                cv.put("time", dateString2r); //These Fields should be your String values of actual column names
                                cv.put("stock", net_updated_stock + "");
                                newDB.update("media_store", cv, "id " + "=" + med_id, null);
                                r.play();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            results.add("Name: " + medicineName);

                        }
                        i++;
                    }

                }

            } catch (SQLiteException se) {
                Log.e(getClass().getSimpleName(), "Could not create or Open the database");
            }


}
}
