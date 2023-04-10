package com.example.myapplication.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.db.User;
import com.example.myapplication.utils.AppSettingSharePref;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRegistrationActivity extends AppCompatActivity {

    private EditText etName, etMobile, etAge, etWeight, etHeight;
    private Button regBtn;
    private RadioButton radioMale, radioFemale;
    private UserRepository userRepository;
    private boolean isUpdateMode =false;
    private TextView titleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        getSupportActionBar().setTitle("User Registration");
        initiateId();
        registerListener();
        if(getIntent().hasExtra("isLogin")){
            titleTv.setText("User Profile");
            isUpdateMode =true;
            regBtn.setText("Update");
            setDataToFields();
        }else {
            isUpdateMode = false;
            checkUserLogin();
        }
    }

    private void setDataToFields() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                UserRepository userRepository1 = new UserRepository(UserRegistrationActivity.this);
                List<User> userList = userRepository1.getUserById(AppSettingSharePref.getInstance(UserRegistrationActivity.this).getUid());
                if(userList!=null && userList.size() >0){
                    User user = userList.get(0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUserInfo(user);
                        }
                    });
                }
            }
        });
    }

    private void setUserInfo(User user) {
        etName.setText(user.name);
        etMobile.setText(user.contact);
        etAge.setText(user.age);
        etWeight.setText(user.weight);
        etHeight.setText(user.height);
        if(user.gender.equals("male")){
            radioMale.setChecked(true);
            radioFemale.setChecked(false);
        }else{
            radioFemale.setChecked(true);
            radioMale.setChecked(false);
        }
    }

    private void checkUserLogin() {
        if (!AppSettingSharePref.getInstance(this).getUserName().trim().equals("")){
            goToWelcomeScreen();
        }
    }

    private void goToWelcomeScreen() {
        finish();
        Intent intent = new Intent(this, UserWelcomeActivity.class);
        intent.putExtra("userid",AppSettingSharePref.getInstance(this).getUid());
        startActivity(intent);
    }

    private void registerListener() {
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    addUser();


            }

        });
        radioFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioFemale.isChecked()) {
                    radioMale.setChecked(false);
                } else {
                    radioFemale.setChecked(true);
                }
            }
        });
        radioMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioMale.isChecked()) {
                    radioFemale.setChecked(false);
                } else {
                    radioMale.setChecked(true);
                }
            }
        });
    }

    private void initiateId() {
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        regBtn = findViewById(R.id.regBtn);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        titleTv = findViewById(R.id.titleTv);
        userRepository = new UserRepository(UserRegistrationActivity.this);
    }

    private void addUser() {
        String name = etName.getText().toString();
        String contact = etMobile.getText().toString();
        String age = etAge.getText().toString();
        String weight = etWeight.getText().toString();
        String height = etHeight.getText().toString();
        String gender = (radioFemale.isChecked())? "female": "male";
        if (name.equals("") || contact.equals("") || age.equals("") || weight.equals("") || height.equals("")){
            showToastAlert("All fields are compulsory");
            return;
        }
        User user = new User();
        user.name = name;
        user.contact = contact;
        user.age = age;
        user.weight = weight;
        user.height = height;
        user.gender = gender;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if(isUpdateMode){
                        user.user_id = AppSettingSharePref.getInstance(UserRegistrationActivity.this).getUid();
                     userRepository.updateUser(user);
                    } else {
                        userRepository.addUser(user);
                    }
                    int uid = userRepository.getUser(user.contact).get(0).user_id;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isUpdateMode){
                                Toast.makeText(UserRegistrationActivity.this, "User detail updated successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                showToastAlert("User registered successfully");
                            }
                            AppSettingSharePref.getInstance(UserRegistrationActivity.this).setUsername(user.name);
                            AppSettingSharePref.getInstance(UserRegistrationActivity.this).setUid(uid);
                            goToWelcomeScreen();
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void showToastAlert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}