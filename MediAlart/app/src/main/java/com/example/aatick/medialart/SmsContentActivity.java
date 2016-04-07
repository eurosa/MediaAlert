package com.example.aatick.medialart;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.IntentService;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class SmsContentActivity extends ListActivity {
    SQLiteDatabase newDB;
    EditText edit_name,edit_mobile,edit_description;
    private ArrayList<String> results=new ArrayList<String>();
    private   String  smsMedicine, smsDate,smsTime,moblileNumber,sms_id,smsDescription;

    TextView edit_date,edit_time;
    String format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_content_activity);
        edit_name=(EditText)findViewById(R.id.medicine_name);
        edit_time = (TextView)findViewById(R.id.ed_time);
        edit_date = (TextView)findViewById(R.id.sms_date);
        edit_mobile = (EditText)findViewById(R.id.mobile_number);

        edit_description=(EditText)findViewById(R.id.description_name);

        System.out.print(edit_name);

        openAndQueryDatabase();
        resultList();
    }

    //    String medicineName, String date, String time, String dosage, String quantity, String stock,String interval, String description
    public void setSms(View view) {

        DataBaseHelper dbInsert = new DataBaseHelper(this);
        Media media = new Media(edit_name.getText().toString(), edit_date.getText().toString(), edit_time.getText().toString(), edit_mobile.getText().toString(),edit_description.getText().toString());
        Toast.makeText(this, edit_name.getText().toString(), Toast.LENGTH_LONG).show();
        dbInsert.createSms(media);
        Intent intent1 = new Intent(SmsContentActivity.this, SmsService.class);
        Intent intent = getIntent();
        startActivity(intent);
        startService(intent1);
    }

    public void click_Time(View view){
        final Dialog timerDialog = new Dialog(this);
        timerDialog.setContentView(R.layout.time_picker_dialog_layout);
        timerDialog.setTitle("Select your time");
        final TimePicker dialog_timepicker =(TimePicker)timerDialog.findViewById(R.id.dialog_timepicker);
        Button btn_dialog_setalarm = (Button)timerDialog.findViewById(R.id.btn_dialog_setalarm);

        btn_dialog_setalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = dialog_timepicker.getCurrentHour();
                int min = dialog_timepicker.getCurrentMinute();
                timerDialog.dismiss();
                showTime(hour, min);
            }
        });
        timerDialog.show();
    }
    public void click_Date(View view){
        final  Dialog dateDialog = new Dialog(this);
        dateDialog.setContentView(R.layout.calender_dialog_layout);
        dateDialog.setTitle("Select your date");
        final DatePicker calender = (DatePicker)dateDialog.findViewById(R.id.calender);
        Button button_calender = (Button)dateDialog.findViewById(R.id.calender_button);
        button_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int date = calender.getDayOfMonth();
                int month = calender.getMonth()+1;
                int year = calender.getYear();
                edit_date.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(date));
                dateDialog.dismiss();
            }
        });
        dateDialog.show();
    }
    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        }
        else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        edit_time.setText(new StringBuilder().append(hour).append(":").append(min).append(" ").append(format));
    }


    public void cancelInsert(View view){
        Intent intent = new Intent(SmsContentActivity.this,HomeActivity.class);
        startActivity(intent);

    }



    private void openAndQueryDatabase() {
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(this.getApplicationContext());
            newDB = dbHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT sms_medicine,sms_date,id,sms_mobile,sms_time FROM sms_table where id >0", null);
            if (c != null ) {
                while (c.moveToNext()) {
//                    do {
                    smsMedicine = c.getString(c.getColumnIndex("sms_medicine"));
                    smsDate = c.getString(c.getColumnIndex("sms_date"));
                    moblileNumber = c.getString(c.getColumnIndex("sms_mobile"));
                    smsTime = c.getString(c.getColumnIndex("sms_time"));
                    results.add("Name: " + smsMedicine + "  Date: " + smsDate+" Time:"+smsTime);
                    Log.d("biswas", moblileNumber);
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }


    private void resultList() {

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results));
        getListView().setTextFilterEnabled(true);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =  new Intent(SmsContentActivity.this,HomeActivity.class);
        startActivity(intent);
    }
}
