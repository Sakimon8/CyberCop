package com.example.cybercop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import java.util.Timer;

public class sliderAdapter extends PagerAdapter {
    Timer timer;
    Context context;
    int i=0;
    LayoutInflater layoutInflater;
    public sliderAdapter(Context context){
        this.context=context;
    }
    public int[] slide_images= {
            R.drawable.camera3,
            R.drawable.location4,
            R.drawable.search3,
    };

    public String[] slide_heading={
            "      PROTECT YOUR CAMERA",
            " KNOW ABOUT YOUR LOCALITY",
            "        SAFE SEARCH ENGINE",

    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==(ConstraintLayout) o;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

       layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
       View view = layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slideImageView=(ImageView)view.findViewById(R.id.imageView);
        TextView slideHeading=(TextView)view.findViewById(R.id.textView);
        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_heading[position]);
        container.addView(view);
       return view;
    }
    @Override
    public void destroyItem(final ViewGroup container, int position, Object object){
        container.removeView((ConstraintLayout)object);

    }}


