package com.example.w3e_52.newloginfragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;



public class FragmentLogout extends Fragment {
    ViewGroup root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_logout, null);
        return root;
    }
    void  getMessage(){

    }
    void setMessage(String msg){
        TextView txt=(TextView)root.findViewById(R.id.textLogout);
        TextView txt3 =(TextView)root.findViewById(R.id.textLogoutPass) ;


    }
}
