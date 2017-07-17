package com.xls.circleprogress;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button downloadBtn;
    private CircleProgressView mCircleView;
    private Handler mHander = new Handler();
    private Timer timer;
    private int mPercent = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleView = (CircleProgressView) findViewById(R.id.my_circle);
        downloadBtn = (Button) findViewById(R.id.btn_moc_download);
        timer = new Timer();

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer==null){
                    timer = new Timer();
                    mPercent = 0;
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mCircleView.setPercent(mPercent++);
                        if(mPercent>100){
                            timer.cancel();
                            timer = null;
                        }
                    }
                },0,100);
            }
        });
    }

}
