package com.xls.circleprogress;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xls.library.CircleProgressView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button downloadBtn;
    private CircleProgressView mCircleView;
    private Handler mHander = new Handler();
    private Timer timer;
    private float value = 0f;
    private Button randomBtn;
    private Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleView = (CircleProgressView) findViewById(R.id.my_circle);
        downloadBtn = (Button) findViewById(R.id.btn_moc_download);
        timer = new Timer();
        random =new Random(System.currentTimeMillis());
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleView.reset();
                mCircleView.setAnimatorTime(200);
                if(timer==null){
                    timer = new Timer();
                    value = 0;
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                                mCircleView.setValue(value);
                                value+=mCircleView.getMaxValue()/100;
                                if(value>mCircleView.getMaxValue()){
                                    timer.cancel();
                                    timer = null;
                                }
                           }
                       });
                    }
                },0,200);
            }
        });

        randomBtn = (Button) findViewById(R.id.btn_random);
        randomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleView.setAnimatorTime(1000);
                mCircleView.setValue(random.nextInt((int)mCircleView.getMaxValue()));
            }
        });
    }



}
