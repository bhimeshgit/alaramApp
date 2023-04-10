package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.WebUrl;

public class AdminLoginActivity extends AppCompatActivity {

    EditText etMobile, etPass;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().setTitle("Diet Manager");
        etMobile= findViewById(R.id.etMobile);
        etPass = findViewById(R.id.etPass);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String mobile = etMobile.getText().toString();
               String pass = etPass.getText().toString();
               if (mobile.trim().equals(WebUrl.ADMIN_MOBILE) && pass.trim().equals(WebUrl.ADMIN_PASS)){
                   Intent intent = new Intent(AdminLoginActivity.this,DietListAdminActivity.class);
                   startActivity(intent);
                   finish();
               }else{
                   Toast.makeText(AdminLoginActivity.this, "Invalid mobile number or password", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}