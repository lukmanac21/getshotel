package com.example.lukman.finalprocj;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class SpashActivity extends AppCompatActivity {
    Animation frombottom, fromtop;
    ImageView ballon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        ballon = (ImageView) findViewById(R.id.imageView);;
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        ballon.setAnimation(fromtop);
        Thread mythread = new Thread(){
            @Override
            public void run() {

                try {
                    sleep(2000);
                    Intent myintent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(myintent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };
        mythread.start();

    }

}
