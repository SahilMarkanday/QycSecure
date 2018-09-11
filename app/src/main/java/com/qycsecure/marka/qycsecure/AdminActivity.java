package com.qycsecure.marka.qycsecure;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AdminActivity extends AppCompatActivity{
    ImageButton employees;
    ImageButton register;
    ImageButton addAdmin;
    Button logout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        employees = (ImageButton) findViewById(R.id.employees);
        register = (ImageButton) findViewById(R.id.register);
        logout = (Button) findViewById(R.id.logout);
        addAdmin = (ImageButton) findViewById(R.id.addAdmin);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, UserActivity.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        employees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, EmployeesActivity.class);
                startActivity(i);
            }
        });

        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AddAdminActivity.class);
                startActivity(i);
            }
        });

    }
}
