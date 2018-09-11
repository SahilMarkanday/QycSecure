package com.qycsecure.marka.qycsecure;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddAdminActivity extends AppCompatActivity {
    EditText nameInput;
    EditText usernameInput;
    EditText passwordInput;
    EditText confirmPassword;
    ImageButton takePicture;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addadmin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nameInput = (EditText) findViewById(R.id.nameInput);
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        confirmPassword = (EditText) findViewById(R.id.passwordCheck);
        takePicture = (ImageButton) findViewById(R.id.adminPicture);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nInput = nameInput.getText().toString();
                String uInput = usernameInput.getText().toString();
                String pInput = passwordInput.getText().toString();
                String cPassword = confirmPassword.getText().toString();
                if(pInput.equals(cPassword)){
                    Intent i = new Intent(AddAdminActivity.this, AdminPictureActivity.class);
                    i.putExtra("name", nInput);
                    i.putExtra("username", uInput);
                    i.putExtra("password", pInput);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getBaseContext(), "Password does not match", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
