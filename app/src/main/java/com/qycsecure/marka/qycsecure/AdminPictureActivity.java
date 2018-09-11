package com.qycsecure.marka.qycsecure;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class AdminPictureActivity extends AppCompatActivity {
    FrameLayout frameLayout2;
    Camera camera;
    ShowCamera showCamera;
    CountDownTimer cdTimer;
    TextView countDown2;
    Boolean exists = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpicture);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        frameLayout2 = (FrameLayout)findViewById(R.id.frameLayout2);
        countDown2 = (TextView) findViewById(R.id.countDown2);
        //open camera
        camera = Camera.open(1);
        showCamera = new ShowCamera(this, camera);
        frameLayout2.addView(showCamera);
        cdTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDown2.setText(""+ millisUntilFinished/1000);
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
            Intent i = getIntent();
            String name = i.getStringExtra("name");
            String username = i.getStringExtra("username");
            String password = i.getStringExtra("password");
            String occupation = "Admin";
            DataBaseHelper db = new DataBaseHelper(AdminPictureActivity.this);
            db.addAdmin(name, username, password, occupation, data);
            Intent j = new Intent(AdminPictureActivity.this, AdminActivity.class);
            startActivity(j);
            camera.startPreview();
        }
    };
}
