package com.qycsecure.marka.qycsecure;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EmployeeDetailsActivity extends AppCompatActivity {
    ImageView image;
    TextView name;
    TextView occupation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_employeedetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DataBaseHelper db = new DataBaseHelper(this);
        Intent i = getIntent();
        int position = i.getIntExtra("position", -1);
        ArrayList<Integer> listID = i.getIntegerArrayListExtra("listID");
        int pos = listID.get(position);
        byte[] b = db.getUserImage(pos);
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        image = (ImageView) findViewById(R.id.userImage);
        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 171, 124, false));
        name = (TextView) findViewById(R.id.name);
        occupation = (TextView) findViewById(R.id.occupation);
        name.setText(db.getName(pos));
        occupation.setText(db.getOccupation(pos));
    }
}
