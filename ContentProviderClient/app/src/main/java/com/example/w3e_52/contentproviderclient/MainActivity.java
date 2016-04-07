package com.example.w3e_52.contentproviderclient;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    public void onClickAddName(View view) {
//        // Add a new student record
//        ContentValues values = new ContentValues();
//
//        values.put(StudentsProvider.NAME,
//                ((EditText)findViewById(R.id.editText2)).getText().toString());
//
//        values.put(StudentsProvider.GRADE,
//                ((EditText)findViewById(R.id.editText3)).getText().toString());
//
//        Uri uri = getContentResolver().insert(
//                StudentsProvider.CONTENT_URI, values);
//
//        Toast.makeText(getBaseContext(),
//                uri.toString(), Toast.LENGTH_LONG).show();
//    }

    public void onClickRetrieveStudents(View view) {

        // Retrieve student records
        String URL = "content://om.radhason.android/books";

        Uri students = Uri.parse(URL);
        Cursor c = managedQuery(students, null, null, null, "name");

        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(String.valueOf(0))) +
                                ", " +  c.getString(c.getColumnIndex(String.valueOf(0))) +
                                ", " + c.getString(c.getColumnIndex(String.valueOf(0))),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }
}
