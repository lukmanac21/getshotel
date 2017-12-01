package com.example.lukman.finalprocj;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignupFragment extends Fragment {
    TextView name,username,pass,repass,email,address;
    Button save;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        name = (TextView)view.findViewById(R.id.name);
        username = (TextView)view.findViewById(R.id.username);
        pass = (TextView)view.findViewById(R.id.password);
        repass = (TextView)view.findViewById(R.id.repassword);
        email = (TextView)view.findViewById(R.id.email);
        address = (TextView)view.findViewById(R.id.address);
        save = (Button)view.findViewById(R.id.btnsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().matches("") || username.getText().toString().matches("") || pass.getText().toString().matches("")
                        || repass.getText().toString().matches("") || email.getText().toString().matches("") || address.getText().toString().matches("")){
                    Toast.makeText(getActivity(), "Completed Your Identity", Toast.LENGTH_LONG).show();
                }
                else if (pass.getText().toString() != repass.getText().toString()){
                    Toast.makeText(getActivity(), "Password Did Not Matches", Toast.LENGTH_LONG).show();
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