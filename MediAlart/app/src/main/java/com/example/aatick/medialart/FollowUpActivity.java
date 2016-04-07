package com.example.aatick.medialart;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class FollowUpActivity extends ListActivity {
    private SQLiteDatabase newDB;
    private ArrayList<String> results=new ArrayList<String>();
    String format;
    final Context context = this;
    int db_id;
    String doctorname,location,date;
    EditText edit_doctor_name,edit_location,edit_disease_name,edit_description;
    TextView  edit_date,edit_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_up_activity);

        edit_doctor_name=(EditText)findViewById(R.id.doctor_name);
        edit_date=(TextView)findViewById(R.id.tv_followup_date);
        edit_time=(TextView)findViewById(R.id.tv_time);
        edit_location=(EditText)findViewById(R.id.location_name);
        edit_disease_name=(EditText)findViewById(R.id.disease_name);
        edit_description=(EditText)findViewById(R.id.description);
        openAndQueryDatabase();
        displayResultList();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                show_data(position);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.followup_dialog);
                dialog.setTitle("Details..");

                TextView tvName = (TextView) dialog.findViewById(R.id.doctorName);
                tvName.setText("Doctors Name;" + doctorname);
                TextView tvLocation = (TextView) dialog.findViewById(R.id.doctorLocation);
                tvLocation.setText("Location:" + location);

                Button btn_delete = (Button) dialog.findViewById(R.id.follow_delete);
                Button btn_cancel = (Button) dialog.findViewById(R.id.follow_cancel);

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data_delete();
                        dialog.dismiss();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getListView().getAdapter().getCount()> 10){
            Toast.makeText(FollowUpActivity.this, "Please delete the unnecessary item", Toast.LENGTH_SHORT).show();
        }
    }

    public void data_delete()
    {
        DataBaseHelper dbHelper = new DataBaseHelper(this.getApplicationContext());
        newDB = dbHelper.getWritableDatabase();
        newDB.rawQuery("DELETE FROM media_follower where id= " + db_id, null).moveToFirst();
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
        Intent intent = new Intent(FollowUpActivity.this,HomeActivity.class);
        startActivity(intent);

    }

    public void setFollow(View view) {
        if (edit_doctor_name.getText().length() == 0 || edit_date.getText().length() == 0 || edit_time.getText().length() == 0 ||
                edit_disease_name.getText().length() == 0 || edit_location.getText().length() == 0) {

            Toast.makeText(FollowUpActivity.this, "Please fill up all the fields ", Toast.LENGTH_SHORT).show();
        } else {

            DataBaseHelper dbInsert = new DataBaseHelper(this);
            Media media = new Media(edit_doctor_name.getText().toString(), edit_date.getText().toString(), edit_time.getText().toString(), edit_location.getText().toString()
                    , edit_disease_name.getText().toString(), edit_description.getText().toString());
            dbInsert.createMediaForFollow(media);

            Intent intent = new Intent(this.getBaseContext(), FollowUpActivity.class);
            Intent intent2 = new Intent(this.getBaseContext(), FollowService.class);
            this.finish();
            startActivity(intent);
            startService(intent2);
        }
    }

    public void show_data(int pos){

        DataBaseHelper dbHelper = new DataBaseHelper(this.getApplicationContext());
        newDB = dbHelper.getWritableDatabase();
        Cursor c = newDB.rawQuery("SELECT doctor_name, id, doctor_location, follow_date,follow_time FROM media_follower where id >0", null);
        c.moveToPosition(pos);
                    doctorname = c.getString(c.getColumnIndex("doctor_name"));
                    location = c.getString(c.getColumnIndex("doctor_location"));
                    date = c.getString(c.getColumnIndex("follow_date"));
                    db_id = c.getInt(c.getColumnIndex("id"));
                    String followTime = c.getString(c.getColumnIndex("follow_time"));
    }
    private void openAndQueryDatabase() {
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(this.getApplicationContext());
            newDB = dbHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT doctor_name, id, doctor_location, follow_date,follow_time FROM media_follower where id >0", null);

            if (c != null ) {
                if (c.moveToFirst()) {
                    do {
                        doctorname = c.getString(c.getColumnIndex("doctor_name"));
                        location = c.getString(c.getColumnIndex("doctor_location"));
                        String date = c.getString(c.getColumnIndex("follow_date"));
                        db_id = c.getInt(c.getColumnIndex("id"));
                        String followTime = c.getString(c.getColumnIndex("follow_time"));
                        results.add("Name: " + doctorname + "  Location: " + location +"  "+ date+" "+"Time: "+followTime );
                    }while (c.moveToNext());
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }
    private void displayResultList() {

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results));
        getListView().setTextFilterEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FollowUpActivity.this,HomeActivity.class);
        startActivity(intent);
    }
}
