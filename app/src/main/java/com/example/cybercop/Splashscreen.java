package com.example.cybercop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class Splashscreen extends AppCompatActivity {
    public static int SPLASH=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ImageView i=(ImageView)findViewById(R.id.img);

        Animation top,bootom;
        top = AnimationUtils.loadAnimation(Splashscreen.this, R.anim.top);
        bootom=AnimationUtils.loadAnimation(Splashscreen.this, R.anim.bottom);

        i.startAnimation(top);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home=new Intent(Splashscreen.this, MainActivity.class);
                startActivity(home);
                finish();
            }
        },SPLASH);

    }
}