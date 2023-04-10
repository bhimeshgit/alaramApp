package com.example.myapplication.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.admin.DietAddUpdateActivity;
import com.example.myapplication.db.User;
import com.example.myapplication.utils.AppSettingSharePref;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserWelcomeActivity extends AppCompatActivity {

    private TextView titleTv, bmiTv;
    private Button profileBtn, dietBtn;
    private User user;
    float userbmi =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_welcome);
        getSupportActionBar().setTitle("Diet Manager");
        initiateId();
        fetchUserDetail();
        registerListener();
    }

    private void fetchUserDetail() {
        int uid = AppSettingSharePref.getInstance(this).getUid();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
              user = new UserRepository(UserWelcomeActivity.this).getUserById(uid).get(0);
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      setUserDetails();
                  }
              });
            }
        });
    }

    private void setUserDetails() {
        titleTv.setText("Welcome "+ user.name);
        userbmi = calculateBmi();
        bmiTv.setText("Your BMI is "+userbmi);
    }

    private float calculateBmi() {
        float weight = Float.parseFloat(user.weight);
        float height = Float.parseFloat(user.height);
        float bmi = weight/(height*height);
        return bmi;
    }

    private void initiateId() {
        titleTv= findViewById(R.id.titleTv);
        bmiTv = findViewById(R.id.bmiTv);
        profileBtn = findViewById(R.id.profileBtn);
        dietBtn = findViewById(R.id.dietBtn);
    }


    private void registerListener() {
        dietBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserWelcomeActivity.this, UserDietActivity.class);
                intent.putExtra("bmi",userbmi);
                startActivity(intent);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserWelcomeActivity.this, UserRegistrationActivity.class);
                intent.putExtra("isLogin",true);
                startActivity(intent);
            }
        });
    }
}