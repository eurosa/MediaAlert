package com.example.w3e_52.databaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTitle, editAuthor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTitle = (EditText) findViewById(R.id.editText2);
        editAuthor = (EditText) findViewById(R.id.editText3);
    }
        public void onClickAddName(View view){
            Toast.makeText(getApplicationContext(),"Data has been inserted",Toast.LENGTH_LONG).show();
        MySQLiteHelper db = new MySQLiteHelper(this);

        Book book=new Book(editTitle.getText().toString(),editAuthor.getText().toString());
        db.addBook(book);

    }
        /**
         * CRUD Operations
         * */
        // add Books
//        db.addBook(new Book("Android Application Development Cookbook", "Wei Meng Lee"));
//        db.addBook(new Book("Android Programming: The Big Nerd Ranch Guide", "Bill Phillips and Brian Hardy"));
//        db.addBook(new Book("Learn Android App Development", "Wallace Jackson"));
//
//        // get all books
//        List<Book> list = db.getAllBooks();
//
//        // delete one book
//        db.deleteBook(list.get(0));
//
//        // get all books
//        db.getAllBooks();


}