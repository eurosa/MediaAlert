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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryActivity extends ListActivity {
    private ArrayList<String> results=new ArrayList<String>();
    DataBaseHelper dataHandler= new DataBaseHelper(this);
    private SQLiteDatabase newDB;
    int db_id;
    final Context context = this;

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        openAndQueryDatabase();
        displayResultList();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Dialog dateDialog = new Dialog(context);
                dateDialog.setContentView(R.layout.history_dialog);
                dateDialog.setTitle("Select your date");
                Button btnDelete = (Button) dateDialog.findViewById(R.id.historyDelete);
                Button btnCancel = (Button) dateDialog.findViewById(R.id.historyCancel);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        data_delete();
                        dateDialog.dismiss();
                        Intent intent = new Intent(context,HistoryActivity.class);
                        startActivity(intent);
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateDialog.dismiss();
                    }
                });
                dateDialog.show();
            }
        });
    }

    private void openAndQueryDatabase() {
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(this.getApplicationContext());
            newDB = dbHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT medicine_name, id, date FROM media_store2 where id >0", null);

            if (c != null ) {
                if (c.moveToFirst()) {
                    do {
                        String medicineName = c.getString(c.getColumnIndex("medicine_name"));
                        String date = c.getString(c.getColumnIndex("date"));
                        db_id = c.getInt(c.getColumnIndex("id"));
                        results.add("Name: " + medicineName + "  Date: " + date);
                        Log.d("biswas", medicineName);
                    }while (c.moveToNext());
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }

    public void data_delete()
    {
        DataBaseHelper dbHelper = new DataBaseHelper(this.getApplicationContext());
        newDB = dbHelper.getWritableDatabase();
        newDB.rawQuery("DELETE FROM media_store2 where id= " + db_id, null).moveToFirst();
    }

    private void displayResultList() {

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results));
        getListView().setTextFilterEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HistoryActivity.this,HomeActivity.class);
        startActivity(intent);
    }
}
