package com.example.cybercop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  {
    private sliderAdapter msliderAdapter;
    ViewPager mSlideViewPager;
    Button button;
    double lat=0,lon=0;
    final String city="",location="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String location="";
        mSlideViewPager = (ViewPager) findViewById(R.id.slideviwepager);
        msliderAdapter = new sliderAdapter(this);
        mSlideViewPager.setAdapter(msliderAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Mytimer(),3000,3000);
        gpstracker g = new gpstracker((Context) MainActivity.this);
                   g.getlocation();
//                  if(l!=null){
//                       lat=l.getLatitude();
//                       lon = l.getLongitude();
//
//                      Toast.makeText(MainActivity.this,"LATITUTE "+lat+" \nLONGITUTE "+lon,Toast.LENGTH_LONG).show();
//                     // direction.setText(String.valueOf(lat)+"  "+String.valueOf(lon));
//                  }
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String []loc=gpstracker.data();
                Intent i=new Intent(MainActivity.this,MainActivity2.class);

                Toast.makeText(MainActivity.this,"Area is "+loc[0],Toast.LENGTH_LONG).show();

                i.putExtra("loc",loc[0]);
                i.putExtra("sub_loc",loc[1]);
                startActivity(i);
                finish();

            }
        });
    }



    public class Mytimer extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(  new Runnable() {
                @Override
                public void run() {
                    if(mSlideViewPager.getCurrentItem()==0){
                        mSlideViewPager.setCurrentItem(1);
                    }
                    else if(mSlideViewPager.getCurrentItem()==1){
                        mSlideViewPager.setCurrentItem(2);
                    }
                    else{
                        mSlideViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

}