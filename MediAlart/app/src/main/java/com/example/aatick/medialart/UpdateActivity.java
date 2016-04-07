
package com.example.aatick.medialart;

        import android.app.Activity;
        import android.app.AlarmManager;
        import android.app.Dialog;
        import android.app.IntentService;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CalendarView;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.RadioButton;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.widget.Toast;

        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.GregorianCalendar;

public class UpdateActivity extends Activity {

    EditText edit_name,edit_dose,edit_quantity,edit_description,edit_interval,edit_stock;
    RadioButton radio_dose,radio_quantity;
    TextView edit_date,edit_time;
    int db_id;
    String format;
    String medicineName,date,description,time,interval,storage,dose;
    SQLiteDatabase newDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_activity);
        int value = getIntent().getExtras().getInt("position");
        getData(value);
        edit_name=(EditText)findViewById(R.id.medicine_na);
        edit_name.setText(medicineName);
        edit_time = (TextView)findViewById(R.id.ed_time);
        edit_time.setText(time);
        edit_date = (TextView)findViewById(R.id.ed_date);
        edit_date.setText(date);
        edit_dose = (EditText)findViewById(R.id.edit_dose);
        edit_dose.setText(dose);
        edit_quantity =(EditText)findViewById(R.id.edit_quantity);
        edit_quantity.setText(dose);
        radio_dose =(RadioButton)findViewById(R.id.radio_dose);
        radio_quantity = (RadioButton)findViewById(R.id.radio_quantity);
        edit_description=(EditText)findViewById(R.id.description_name);
        edit_description.setText(description);
        edit_interval=(EditText)findViewById(R.id.interval_name);
        edit_interval.setText(interval);
        edit_stock=(EditText)findViewById(R.id.stock_name);
        edit_stock.setText(storage);
        if(radio_dose.isChecked()){
            edit_quantity.setVisibility(View.GONE);
        }
    }
    public void setAlarm(View view) {

        if (edit_name.getText().length() == 0 || edit_date.getText().length()== 0 || edit_time.length() == 0 ||
                edit_dose.getText().length() == 0 || edit_stock.getText().length() == 0 || edit_interval.getText().length() == 0 )
        {
            Toast.makeText(UpdateActivity.this, "Please fill all the fields ", Toast.LENGTH_SHORT).show();
        }
        else {
            DataBaseHelper dbInsert = new DataBaseHelper(this);
            Media media = new Media(edit_name.getText().toString(), edit_date.getText().toString(), edit_time.getText().toString(), edit_dose.getText().toString()
                    , edit_quantity.getText().toString(), edit_stock.getText().toString(), edit_interval.getText().toString(), edit_description.getText().toString());

            dbInsert.updatetable(media,db_id);
            Intent intent = new Intent(UpdateActivity.this, HomeActivity.class);
            Intent intent1 = new Intent(UpdateActivity.this, AlarmService.class);
            startActivity(intent);
            startService(intent1);
            Toast.makeText(this, "SAl" + intent1, Toast.LENGTH_LONG).show();
        }
    }
    public void getData(int position){
        DataBaseHelper dbHelper = new DataBaseHelper(this.getApplicationContext());
        newDB = dbHelper.getWritableDatabase();
        Cursor c = newDB.rawQuery("SELECT medicine_name,description,dosage,time_interval,time, stock, date,id FROM media_store where id >0", null);
        c.moveToPosition(position);
        medicineName = c.getString(c.getColumnIndex("medicine_name"));
        date = c.getString(c.getColumnIndex("date"));
        time = c.getString(c.getColumnIndex("time"));
        db_id = c.getInt(c.getColumnIndex("id"));
        dose = c.getString(c.getColumnIndex("dosage"));
        interval = c.getString(c.getColumnIndex("time_interval"));
        storage = c.getString(c.getColumnIndex("stock"));
        description = c.getString(c.getColumnIndex("description"));
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
    public void click_radio_dose(View view){
        edit_quantity.setVisibility(View.GONE);
        edit_dose.setVisibility(View.VISIBLE);
    }
    public void click_radio_quantity(View view){
        edit_dose.setVisibility(View.GONE);
        edit_quantity.setVisibility(View.VISIBLE);
    }

    public void cancelInsert(View view){
        Intent intent = new Intent(UpdateActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UpdateActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
