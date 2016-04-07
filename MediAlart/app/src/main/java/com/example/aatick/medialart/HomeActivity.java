package com.example.aatick.medialart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class HomeActivity extends ListActivity {
    ImageButton btn_insert,btn_sms,btn_followup,btn_setting,btn_history;
    final Context context = this;
    private int db_id,dose,interval,storage;
    private String medicineName,date,description,time;
    private ArrayList<String>results=new ArrayList<String>();
    DataBaseHelper dataHandler= new DataBaseHelper(this);
    private SQLiteDatabase newDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        btn_insert = (ImageButton)findViewById(R.id.btn_insert);
        btn_followup =(ImageButton)findViewById(R.id.btn_followup);
        btn_history =(ImageButton)findViewById(R.id.btn_history);
        btn_setting =(ImageButton)findViewById(R.id.btn_setting);
        btn_sms = (ImageButton)findViewById(R.id.btn_sms);
        openAndQueryDatabase();
        displayResultList();

        animation();

//       This is for effect of clicking on listview

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                show_data(position);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Details..");
                TextView name = (TextView) dialog.findViewById(R.id.name);
                name.setText("Medicine Name:" + medicineName);
                TextView stock = (TextView) dialog.findViewById(R.id.storage);
                stock.setText("Storage:" + storage);
                TextView medinterval = (TextView) dialog.findViewById(R.id.interval);
                medinterval.setText("Time Interval:" + interval);
                TextView meddescription = (TextView) dialog.findViewById(R.id.description);
                meddescription.setText("Description:" + description);
                TextView mdose = (TextView) dialog.findViewById(R.id.dose);
                mdose.setText("Dose/Quantity:" + dose);

                Button btn_cancel = (Button) dialog.findViewById(R.id.cancel);
                Button btn_update = (Button) dialog.findViewById(R.id.update);
                Button btn_delete = (Button) dialog.findViewById(R.id.delete);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
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
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data_update(position);
                    }
                });
                dialog.show();
            }
        });
    }
    public void data_update(int pos){
        Intent intent = new Intent(this,UpdateActivity.class);
        intent.putExtra("position",pos);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getListView().getAdapter().getCount() >10){
            Toast.makeText(HomeActivity.this, "Please delete the unnecessary item ", Toast.LENGTH_SHORT).show();
        }
    }

    public void show_data(int pos){
    DataBaseHelper dbHelper = new DataBaseHelper(this.getApplicationContext());
    newDB = dbHelper.getWritableDatabase();
    Cursor c = newDB.rawQuery("SELECT medicine_name,description,dosage,time_interval,time, stock, date,id FROM media_store where id >0", null);
    c.moveToPosition(pos);
    medicineName = c.getString(c.getColumnIndex("medicine_name"));
                date = c.getString(c.getColumnIndex("date"));
                time = c.getString(c.getColumnIndex("time"));
                db_id = c.getInt(c.getColumnIndex("id"));
                dose = c.getInt(c.getColumnIndex("dosage"));
                interval = c.getInt(c.getColumnIndex("time_interval"));
                storage = c.getInt(c.getColumnIndex("stock"));
                description = c.getString(c.getColumnIndex("description"));

}

    private void openAndQueryDatabase() {
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(this.getApplicationContext());
            newDB = dbHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT medicine_name,description,dosage,time_interval,time, stock, date,id FROM media_store where id >0", null);

            if (c != null ) {
                if (c.moveToFirst()) {
                    do {
                        String medicineName = c.getString(c.getColumnIndex("medicine_name"));
                        String date = c.getString(c.getColumnIndex("date"));
                        time = c.getString(c.getColumnIndex("time"));
                        storage = c.getInt(c.getColumnIndex("stock"));
                        results.add("Name: " + medicineName + "  Date: " + date+" Time:"+ time+ "  Stock: "+storage);
                    Log.d("biswas",medicineName);
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

    public void animation(){
        RotateAnimation rAnim = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rAnim.setDuration(1000);
        btn_insert.startAnimation(rAnim);
        btn_followup.startAnimation(rAnim);
        btn_history.startAnimation(rAnim);
        btn_setting.startAnimation(rAnim);
        btn_sms.startAnimation(rAnim);
    }

    public void data_delete()
    {
        DataBaseHelper dbHelper = new DataBaseHelper(this.getApplicationContext());
        newDB = dbHelper.getWritableDatabase();
        newDB.rawQuery("DELETE FROM media_store where id= "+db_id, null).moveToFirst();
    }

    public void click_Insert(View view){
        Intent intent= new Intent(HomeActivity.this,InsertActivity.class);
        startActivity(intent);
    }
    public void click_History(View view){
        Intent intent= new Intent(HomeActivity.this,HistoryActivity.class);
        startActivity(intent);
    }
    public void click_Setting(View view){
        Intent intent= new Intent(HomeActivity.this,SettingActivity.class);
        startActivity(intent);

    }
    public void click_Followup(View view){
        Intent intent= new Intent(HomeActivity.this,FollowUpActivity.class);
        startActivity(intent);
    }
    public void click_SmsContent(View view){
        Intent intent= new Intent(HomeActivity.this,SmsContentActivity.class);
        startActivity(intent);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        finish();
//    }



}
