package com.example.aayzstha.neuralvision;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

public class Aanlytics extends Activity {
    private LineChart mChart;
    RelativeLayout myly;
    AnimationDrawable animDraw;
    TextView tv1,tv2,tv3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analytics);
/////bg anim
     myly = findViewById(R.id.mybg);
     animDraw= (AnimationDrawable) myly.getBackground();
     animDraw.setEnterFadeDuration(3500);
     animDraw.setExitFadeDuration(3500);
     animDraw.start();
     //
     tv1=findViewById(R.id.tv_obj_det);
     tv2= findViewById(R.id.tv_obj_rec);
     tv3= findViewById(R.id.accuracy);
     Button bt_back=findViewById(R.id.back_btn);
     bt_back.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
       startActivity(new Intent(Aanlytics.this,MainActivity.class));
      }
     });
     showTextAnimation();
     showCircularProgressAnimation();

       mChart=findViewById(R.id.graph);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        ArrayList<Entry> yValues=new ArrayList<>();
        yValues.add(new Entry(0,10f));
        yValues.add(new Entry(1,60f));
        yValues.add(new Entry(2,30f));
        yValues.add(new Entry(3,50f));
        yValues.add(new Entry(4,100f));
        yValues.add(new Entry(5,20f));
        yValues.add(new Entry(6,30f));
        yValues.add(new Entry(7,10f));
        yValues.add(new Entry(8,60f));
        yValues.add(new Entry(9,30f));
        yValues.add(new Entry(10,50f));
        yValues.add(new Entry(11,100f));
        yValues.add(new Entry(12,10f));
        yValues.add(new Entry(13,60f));
        yValues.add(new Entry(14,30f));
        yValues.add(new Entry(15,50f));
        yValues.add(new Entry(16,100f));
        yValues.add(new Entry(17,20f));
        yValues.add(new Entry(18,30f));
        yValues.add(new Entry(19,10f));
        yValues.add(new Entry(20,60f));
        yValues.add(new Entry(21,30f));
        yValues.add(new Entry(22,50f));
        yValues.add(new Entry(23,100f));
        yValues.add(new Entry(24,100f));
        yValues.add(new Entry(25,20f));
        yValues.add(new Entry(26,30f));
        yValues.add(new Entry(27,10f));
        yValues.add(new Entry(28,60f));
        yValues.add(new Entry(29,30f));
        yValues.add(new Entry(30,50f));

        LineDataSet set1 = new LineDataSet(yValues, "Sep Analytics");

        set1.setFillAlpha(110);
        set1.setLineWidth(2f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.WHITE);
        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        mChart.setData(data);
        mChart.setVisibleXRangeMaximum(6);
        mChart.moveViewToX(3);


    }
 private void showTextAnimation() {
  ValueAnimator animator1 = ValueAnimator.ofInt(0, 1000);
  ValueAnimator animator2 = ValueAnimator.ofInt(0, 863);
  ValueAnimator animator3 = ValueAnimator.ofInt(0, 80);
  animator3.setDuration(2000);
  animator1.setDuration(1500);
  animator2.setDuration(1500);
  animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
   public void onAnimationUpdate(ValueAnimator animation) {
    tv1.setText(animation.getAnimatedValue().toString());
   }
  });

  animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
   public void onAnimationUpdate(ValueAnimator animation) {
    tv2.setText(animation.getAnimatedValue().toString());
   }
  });
  animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
   public void onAnimationUpdate(ValueAnimator animation) {
    tv3.setText(animation.getAnimatedValue().toString());
   }
  });
  animator3.start();
  animator1.start();
  animator2.start();
 }

 private void showCircularProgressAnimation(){
  CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.filter_circle);
  circularProgressBar.setColor(ContextCompat.getColor(this, R.color.progressbarfg));
  circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.progressbarbg));
  circularProgressBar.setProgressBarWidth(25);
  circularProgressBar.setBackgroundProgressBarWidth(5);
  int animationDuration = 1500;
  circularProgressBar.setProgressWithAnimation(80, animationDuration);

 }

 @Override
 public void onBackPressed() {
  super.onBackPressed();
   startActivity(new Intent(Aanlytics.this,MainActivity.class));
 }
}
