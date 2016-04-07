package com.example.w3e_52.newloginfragment;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends FragmentActivity {
EditText editName,editPass;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName=(EditText)findViewById(R.id.editText);
        editPass=(EditText)findViewById(R.id.editText2);
    }

    public void onSend(View view) {


        // TODO Auto-generated method stub
        FragmentLogout Obj = (FragmentLogout) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        Bundle bundle = new Bundle();
        bundle.putString("name",editName.getText().toString());
        bundle.putString("pass",editPass.getText().toString());
        Obj.setMessage(String.valueOf(bundle));

    }
}