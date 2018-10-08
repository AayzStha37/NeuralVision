package com.example.aayzstha.neuralvision;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {
    static int count = 0;
    RelativeLayout myly;
    AnimationDrawable animDraw;
    boolean btnFlag;
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //full screen
        TextView objCount = findViewById(R.id.obj_count);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String str = sdf.format(new Date());
        String mid = "24:00:00";
        if (str.contains(mid)) {
            count = 0;
            objCount.setText("000");
        } else {
            if (count == 0) objCount.setText("000");
            else if (count > 0 && count <= 9) objCount.setText("0" + "0" + String.valueOf(count));
            else if (count > 9 && count <= 99) objCount.setText("0" + String.valueOf(count));
            else objCount.setText("0" + String.valueOf(count));
        }
//for background animation
        myly = findViewById(R.id.mybg);
        animDraw = (AnimationDrawable) myly.getBackground();
        animDraw.setEnterFadeDuration(3500);
        animDraw.setExitFadeDuration(3500);
        animDraw.start();
//
        SwipeButton enableButton = (SwipeButton) findViewById(R.id.swipe_btn);

        enableButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                Toast.makeText(MainActivity.this, "Device dis-connected!", Toast.LENGTH_SHORT).show();
            }
        });

        Button bt_shut = findViewById(R.id.btn_shutter);
        Button bt_gal = findViewById(R.id.btn_gal);
        Button bt_analytics = findViewById(R.id.bt_analytics);
        Button bt_option = findViewById(R.id.bt_option);
        bt_shut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //btnFlag=false;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
        bt_gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        bt_analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Aanlytics.class));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST) {
            Uri uri = data.getData();
            Intent intent = new Intent(MainActivity.this, ImageCaptureGal.class);
            intent.putExtra("imageURI", uri.toString());
            startActivity(intent);
            finish();
        } else {
            Bitmap bMap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            bMap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            byte[] byteArray = bStream.toByteArray();
            Intent anotherIntent = new Intent(this, ImageCapture.class);
            anotherIntent.putExtra("image", byteArray);
            startActivity(anotherIntent);
            finish();
        }

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 3000);
    }



    /*@Override
    protected void onPause() {
        super.onPause();
        SharedPreferences settings = getSharedPreferences("YOUR_PREF_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("SNOW_DENSITY", count);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = getSharedPreferences("YOUR_PREF_NAME", 0);
         count = settings.getInt("SNOW_DENSITY", 0);*/
    }

