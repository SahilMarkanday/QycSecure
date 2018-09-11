package com.qycsecure.marka.qycsecure;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class RecognitionActivity extends AppCompatActivity{
    FrameLayout frameLayout3;
    Camera camera;
    ShowCamera showCamera;
    CountDownTimer cdTimer;
    TextView countDown3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        frameLayout3 = (FrameLayout)findViewById(R.id.frameLayout3);
        countDown3 = (TextView) findViewById(R.id.countDown3);
        //open camera
        camera = Camera.open(1);
        showCamera = new ShowCamera(this, camera);
        frameLayout3.addView(showCamera);
        cdTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDown3.setText(""+ millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                camera.takePicture(null, null, mPictureCallback);
            }
        }.start();
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            /*Bitmap capturedBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            capturedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();*/
            //showEnterLabelDialog(data);
            camera.startPreview();
        }
    };
}
