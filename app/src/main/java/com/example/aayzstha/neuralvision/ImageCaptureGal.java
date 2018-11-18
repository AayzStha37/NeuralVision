package com.example.aayzstha.neuralvision;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.icu.text.Normalizer2;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emredavarci.noty.Noty;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.makeramen.roundedimageview.RoundedImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Aayz Stha on 9/24/2018.
 */

public class ImageCaptureGal extends Activity {
    RelativeLayout myly;
    AnimationDrawable animDraw;
    RelativeLayout dBox;
    RelativeLayout rlUp;
    ProgressBar progressBar;
    ProgressBar progressBar2;
    Bitmap bmp;
    String number;
   // EditText url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_cap_gall);
        //animation background
        myly = findViewById(R.id.mybg);
        animDraw= (AnimationDrawable) myly.getBackground();
        animDraw.setEnterFadeDuration(3500);
        animDraw.setExitFadeDuration(3500);
        animDraw.start();

        final Button send=findViewById(R.id.send);
        Button back=findViewById(R.id.back_btn);
        ImageView img=findViewById(R.id.img_cap);
      //url=findViewById(R.id.url);
        dBox=findViewById(R.id.d_box);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        final CubeGrid cb = new CubeGrid();
        progressBar2 = (ProgressBar)findViewById(R.id.spin_kit2);
        final ThreeBounce tb = new ThreeBounce();
        //////

        String uriSt = getIntent().getStringExtra("imageURI");
        Uri uri = Uri.parse(uriSt);
        try {
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        img.setImageBitmap(roundCornerImage(bmp,10));
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlUp=findViewById(R.id.popup);
                if(isNetworkAvailable()){
                    rlUp.setVisibility(view.INVISIBLE);
                    dBox.setVisibility(view.VISIBLE);
                    progressBar.setIndeterminateDrawable(cb);
                    progressBar2.setIndeterminateDrawable(tb);

                   try {
                        sendPhoto(bmp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                  // startActivity(new Intent(ImageCaptureGal.this,ImageResult.class));
                }
                else{
                    //no internt
                    Noty.init(ImageCaptureGal.this, "Oops, no Internet Connection!",rlUp,
                            Noty.WarningStyle.SIMPLE)
                            .setWarningBoxBgColor("#f82121")
                            .setWarningTappedColor("#f82121")
                            .setWarningBoxPosition(Noty.WarningPos.CENTER)
                            .setWarningBoxRadius(15,15,15,15)
                            .setWarningBoxMargins(0,0,0,0)
                            .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400,400)
                            .show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ImageCaptureGal.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
    public Bitmap roundCornerImage(Bitmap raw, float round) {
        int width = raw.getWidth();
        int height = raw.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawARGB(0, 0, 0, 0);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#000000"));

        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        canvas.drawRoundRect(rectF, round, round, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(raw, rect, rect, paint);

        return result;
    }
    private void sendPhoto(Bitmap bitmap) throws Exception {
        new UploadTask().execute(bitmap);
    }

    private class UploadTask extends AsyncTask<Bitmap, Void, Void> {

        protected Void doInBackground(Bitmap... bitmaps) {
            if (bitmaps[0] == null)
                return null;


            String ImageString=encodeImage(bitmaps[0]);


            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("image",ImageString);
            } catch (Exception e) {
                e.printStackTrace();
            }

            DefaultHttpClient httpclient = new DefaultHttpClient();
            try {
                HttpPost httppost = new HttpPost("http://192.168.43.33:5000/predict"); // server

                StringEntity se;
                se = new StringEntity(jsonObject.toString());
                httppost.setEntity(se);

                httppost.setHeader("Content-type", "application/json");

                HttpResponse response = null;

                try {
                    response = httpclient.execute(httppost);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    if (response != null){

                        String result = response.getStatusLine().toString();

                    }

                    String result="";
                    try {
                        BufferedReader rd = new BufferedReader(new InputStreamReader(
                                response.getEntity().getContent()));
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result = result + line;
                        }
                        Log.i("msgr", result);
                        number=result;
                        Intent i = new Intent(ImageCaptureGal.this, ImageResult.class);
                        i.putExtra("RES", number);
                        startActivity(i);


                    } catch (Exception e) {
                        number = "error";
                    }
                } finally {

                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } finally {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }
    public String encodeImage(Bitmap imageByteArray) {
        return android.util.Base64.encodeToString(getBytesFromBitmap(imageByteArray),
                android.util.Base64.NO_WRAP);
    }

}
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ImageCaptureGal.this,MainActivity.class));
    }
}
