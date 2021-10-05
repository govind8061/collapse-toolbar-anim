package com.example.smoothcolapse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;

import android.gesture.Gesture;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GestureDetectorCompat mGestureDetectorCompat;
    Toolbar toolbar;
    ImageView image;
    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGestureDetectorCompat=new GestureDetectorCompat(this,new GestureListener());
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        image=findViewById(R.id.image);
        tvTitle=findViewById(R.id.tvTitle);

        toolbar.setTranslationY(-140);
        toolbar.setAlpha(0);
        image.setTranslationY(-140);
        tvTitle.setTranslationY(-140);


    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        int threshold=100;
        int velocity_threshold=100;
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float xdiff=e2.getX()-e1.getX();
            float ydiff=e2.getY()-e1.getY();

            if (Math.abs(ydiff)>Math.abs(xdiff)){
                if (Math.abs(ydiff)>threshold && Math.abs(velocityY)>velocity_threshold){
                    if (ydiff>0){
                        toolbar.setVisibility(View.GONE);
                        image.setVisibility(View.VISIBLE);
                        tvTitle.setVisibility(View.VISIBLE);
                        toolbar.animate().setDuration(200).alpha(0).start();
                        toolbar.animate().setDuration(300).translationY(-140).start();
                        image.animate().setDuration(300).translationY(0).start();
                        tvTitle.animate().setDuration(300).translationY(0).start();

                    }else {
                        toolbar.setVisibility(View.VISIBLE);
                        toolbar.animate().setDuration(200).alpha(1).start();
                        toolbar.animate().setDuration(300).translationY(0).start();
                        image.animate().setDuration(300).translationY(-440).start();
                        tvTitle.animate().setDuration(300).translationY(-440).start();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                image.setVisibility(View.GONE);
                                tvTitle.setVisibility(View.GONE);
                            }
                        },200);
                    }
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}