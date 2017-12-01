package com.example.lukman.finalprocj;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class LogFragment extends Fragment {
    TextView username,password;
    Button login;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        username = (TextView)view.findViewById(R.id.username);
        password = (TextView)view.findViewById(R.id.password);
        login = (Button)view.findViewById(R.id.btnlog);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().matches("") || password.getText().toString().matches("")){
                    Toast.makeText(getActivity(),"Completed Your Access Login",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    /*Intent myintent = new Intent(LogFragment.this,MainActivity.class);
                    startActivity(myintent);
                    overridePendingTransition(R.anim.slideanim2, R.anim.slideanim1);*/
                }
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}