package com.example.aayzstha.neuralvision;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mapzen.speakerbox.Speakerbox;

import java.util.Locale;

import cdflynn.android.library.checkview.CheckView;


public class ImageResult extends Activity {
    RelativeLayout myly;
    AnimationDrawable animDraw;
    CheckView  mCheckView;
    TextView result;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
   // TextToSpeech t1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.count++;
        setContentView(R.layout.image_result);
        myly = findViewById(R.id.mybg);
        mCheckView=findViewById(R.id.check);
        Button back=findViewById(R.id.back);
        //bg animation
        animDraw = (AnimationDrawable) myly.getBackground();
        animDraw.setEnterFadeDuration(3500);
        animDraw.setExitFadeDuration(3500);
        animDraw.start();
        //check animation
        mCheckView.check();
        result=findViewById(R.id.result);

        String response= getIntent().getStringExtra("RES");
        result.setText(response.replaceAll(System.getProperty("line.separator"), ""));
        Speakerbox speakerbox = new Speakerbox(getApplication());
        speakerbox.play("There is a " + response + " in front of you");
        //demo voice
       /* Speakerbox speakerbox2  = new Speakerbox(getApplication());
        speakerbox2.play("there is a dog in front of you");*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent i= new Intent(ImageResult.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ImageResult.this,MainActivity.class));
    }
    }
