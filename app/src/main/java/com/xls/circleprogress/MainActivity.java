package com.xls.circleprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private int[] values = new int[]{15,25,55,5};
    private CircleProressView mCPV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCPV = (CircleProressView) findViewById(R.id.cpv);
        mCPV.setmData(initData());
    }

    private List<CircleProressView.PieData> initData(){
        List<CircleProressView.PieData> dataList = new ArrayList<>();
        for(int i=0;i<4;i++){
            CircleProressView.PieData pieData = new CircleProressView.PieData();
            pieData.setColor(mColors[i]);
            pieData.setName("a"+i);
            pieData.setPercent(values[i]/100f);
            dataList.add(pieData);
        }
        return dataList;
    }
}
