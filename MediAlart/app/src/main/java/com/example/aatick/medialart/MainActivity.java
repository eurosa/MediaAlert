package com.example.aatick.medialart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread splashThread = new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    sleep(SPLASH_TIME_OUT);
                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        splashThread.start();
    }
}
