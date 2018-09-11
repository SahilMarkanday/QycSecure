package com.qycsecure.marka.qycsecure;

import android.content.DialogInterface;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    Camera camera;
    ShowCamera showCamera;
    CountDownTimer cdTimer;
    String string1;
    String string2;
    TextView countDown;
    Boolean exists = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        frameLayout = (FrameLayout)findViewById(R.id.frameLayout);
        countDown = (TextView) findViewById(R.id.countDown);
        //open camera
        camera = Camera.open(1);

        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);
        cdTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDown.setText(""+ millisUntilFinished/1000);
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
            showEnterLabelDialog(data);
            camera.startPreview();
        }
    };

    private void showEnterLabelDialog(byte[] data) {
        final byte[] b = data;
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Please enter your name and occupation:");

        final EditText name = new EditText(RegisterActivity.this);
        name.setInputType(InputType.TYPE_CLASS_TEXT);
        final EditText occupation = new EditText(RegisterActivity.this);
        occupation.setInputType(InputType.TYPE_CLASS_TEXT);
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(name);
        layout.addView(occupation);
        builder.setView(layout);

        builder.setPositiveButton("Submit", null); // Set up positive button, but do not provide a listener, so we can check the string before dismissing the dialog
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false); // User has to input a name
        AlertDialog dialog = builder.create();

        // Source: http://stackoverflow.com/a/7636468/2175837
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button mButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        string1 = name.getText().toString().trim();
                        string2 = occupation.getText().toString().trim();
                        if (!string1.isEmpty() && !string2.isEmpty()) { // Make sure the input is valid
                            // If input is valid, dismiss the dialog and add the label to the array
                            dialog.dismiss();
                            DataBaseHelper db = new DataBaseHelper(RegisterActivity.this);
                            //check if the name entered is already registered
                            List<String> users = db.getAllNames();
                            for(int i = 0; i < users.size(); i++){
                                if (string1.equals(users.get(i))) {
                                    exists = true;
                                    break;
                                }
                            }
                            if(!exists){
                                db.addUser(string1, string2, b);
                            }

                        }
                    }
                });
            }
        });

        // Show keyboard, so the user can start typing straight away
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        dialog.show();
    }
}
