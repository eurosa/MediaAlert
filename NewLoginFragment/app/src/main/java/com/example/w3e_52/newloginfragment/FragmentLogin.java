package com.example.w3e_52.newloginfragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FragmentLogin extends Fragment {

    EditText A_input;
    Button A_enter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login, null);
        init(root);
        return root;
    }
    OnButtonPressListener buttonListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            buttonListener = (OnButtonPressListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }
    void init(ViewGroup root){
        Button but=(Button)root.findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                buttonListener.onButtonPressed("Message From First Fragment");
            }
        });
    }

}
