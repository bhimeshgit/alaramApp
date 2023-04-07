package com.example.myapplication.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.myapplication.R;
import com.example.myapplication.admin.viewmodel.DietAdminViewModel;
import com.example.myapplication.db.DietEntity;
import com.example.myapplication.utils.JsonParserVolley;
import com.example.myapplication.utils.WebUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DietAddUpdateActivity extends AppCompatActivity {

    private AutoCompleteTextView ageAutoTxtView;
    private EditText edt_water, edt_morning, edt_morning_break, edt_lunch, edt_eve_break, edt_dinner, edt_exercise;
    private DietAdminViewModel dietAdminViewModel;
    private ExecutorService executorService;
    private Callback callback;
    private boolean isUpdateMode = false;
    private DietEntity dietEntityReceived;
    private Button addBtn;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_add_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ageAutoTxtView = findViewById(R.id.ageAutoTxtView);
        edt_water= findViewById(R.id.edt_water);
        edt_morning= findViewById(R.id.edt_morning);
        edt_morning_break= findViewById(R.id.edt_morning_break);
        edt_lunch= findViewById(R.id.edt_lunch);
        edt_eve_break= findViewById(R.id.edt_eve_break);
        edt_dinner= findViewById(R.id.edt_dinner);
        edt_exercise = findViewById(R.id.edt_exercise);
        dietAdminViewModel = new ViewModelProvider(DietAddUpdateActivity.this).get(DietAdminViewModel.class);
        executorService = Executors.newSingleThreadExecutor();
        addBtn = findViewById(R.id.btnAdd);
        if (getIntent().hasExtra("updateMode")){
            getSupportActionBar().setTitle("Update Diet");
            isUpdateMode = true;
            dietEntityReceived = (DietEntity) getIntent().getExtras().getSerializable("diet_entity");
            setDietEntity(dietEntityReceived);
        } else if(getIntent().hasExtra("bmi")){
            getSupportActionBar().setTitle("Diet Recommended");
        }else{
            getSupportActionBar().setTitle("Add Diet");
        }

        if (!isUpdateMode) {
            setAgeListAdapter();
        }
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDiet();
            }
        });
        clearAllFields();
        if (isUpdateMode){
            addBtn.setText("Update");
            setAllFieldsForUpdate();

        }
        ageAutoTxtView.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }

    private void setAgeListAdapter(){
        // create list of customer
        ArrayList<String> customerList = getAgeList();
        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(DietAddUpdateActivity.this, android.R.layout.simple_spinner_item, customerList);
        //Set adapter
        ageAutoTxtView.setAdapter(adapter);
    }
    public void setDietEntity(DietEntity dietEntity){
        this.dietEntityReceived = dietEntity;
        isUpdateMode = true;
    }

    private ArrayList<String> getAgeList()
    {
        ArrayList<String> ageList = new ArrayList<>();
        ageList.add("< 18.5"); // Under weight
        ageList.add("18.5 to 24.9"); //healthy
        ageList.add("25.0 to 29.9"); //Overweight
        ageList.add(">=30.0"); //very fat and overweight
        return ageList;
    }

    private void addDiet(){
        try {
            String ageRange = ageAutoTxtView.getText().toString();
            String water = edt_water.getText().toString();
            String morning = edt_morning.getText().toString();
            String morningBreak = edt_morning_break.getText().toString();
            String lunch = edt_lunch.getText().toString();
            String eveningBrek = edt_eve_break.getText().toString();
            String dinner = edt_dinner.getText().toString();
            String exercise = edt_exercise.getText().toString();
            if (ageRange.trim().equals("")) {
                Toast.makeText(DietAddUpdateActivity.this, "Please select the age range", Toast.LENGTH_SHORT).show();
                return;
            }
            if (water.trim().equals("")) {
                Toast.makeText(DietAddUpdateActivity.this, "Please add daily water intake", Toast.LENGTH_SHORT).show();
                return;
            }
            if (morning.trim().equals("")) {
                Toast.makeText(DietAddUpdateActivity.this, "Please add morning drink", Toast.LENGTH_SHORT).show();
                return;
            }
            if (morningBreak.trim().equals("")) {
                Toast.makeText(DietAddUpdateActivity.this, "Please add morning breakfast", Toast.LENGTH_SHORT).show();
                return;
            }
            if (lunch.trim().equals("")) {
                Toast.makeText(DietAddUpdateActivity.this, "Please add lunch", Toast.LENGTH_SHORT).show();
                return;
            }
            if (eveningBrek.trim().equals("")) {
                Toast.makeText(DietAddUpdateActivity.this, "Please add evening breakfast", Toast.LENGTH_SHORT).show();
                return;
            }
            if (dinner.trim().equals("")) {
                Toast.makeText(DietAddUpdateActivity.this, "Please add dinner", Toast.LENGTH_SHORT).show();
                return;
            }
            if (exercise.trim().equals("")) {
                Toast.makeText(DietAddUpdateActivity.this, "Please add exercise", Toast.LENGTH_SHORT).show();
                return;
            }


            DietEntity dietEntity = new DietEntity();
            dietEntity.water = water;
            dietEntity.bmi_range = ageRange;
            dietEntity.morning_drink = morning;
            dietEntity.morning_breakfast = morningBreak;
            dietEntity.lunch = lunch;
            dietEntity.evening_breakfast = eveningBrek;
            dietEntity.dinner = dinner;
            dietEntity.exercise = exercise;
            if (isUpdateMode){
                dietEntity.dietId = dietEntityReceived.dietId;
            }
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    if (isUpdateMode) {
//                        dietAdminViewModel.updateDiet(dietEntity);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(DietAddUpdateActivity.this, "updated successfully", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        });
//                    } else {
//                        boolean isExist = dietAdminViewModel.isDietExist(ageRange);
//                        if (isExist) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(DietAddUpdateActivity.this, "Diet is already exist for this age range", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            return;
//                        }
//                        dietAdminViewModel.addDiet(dietEntity);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(DietAddUpdateActivity.this, "Diet added successfully", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        });
//                    }
//                }
//            });
            if (isUpdateMode) {
                updateDietToserver(dietEntity);
            } else{
                addDietToServer(dietEntity);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }



    private void clearAllFields() {
        ageAutoTxtView.setText("");
        edt_water.setText("");
        edt_morning.setText("");
        edt_morning_break.setText("");
        edt_lunch.setText("");
        edt_eve_break.setText("");
        edt_dinner.setText("");
        edt_exercise.setText("");
    }

    private void setAllFieldsForUpdate() {
        ageAutoTxtView.setText(dietEntityReceived.bmi_range);
        edt_water.setText(dietEntityReceived.water);
        edt_morning.setText(dietEntityReceived.morning_drink);
        edt_morning_break.setText(dietEntityReceived.morning_breakfast);
        edt_lunch.setText(dietEntityReceived.lunch);
        edt_eve_break.setText(dietEntityReceived.evening_breakfast);
        edt_dinner.setText(dietEntityReceived.dinner);
        edt_exercise.setText(dietEntityReceived.exercise);
    }

    private void addDietToServer(DietEntity dietEntity) {
        showProgressLoad();
        final JsonParserVolley jsonParserVolley = new JsonParserVolley(DietAddUpdateActivity.this);
        jsonParserVolley.addParameter("bmi_range",dietEntity.bmi_range);
        jsonParserVolley.addParameter("water",dietEntity.water);
        jsonParserVolley.addParameter("morning_drink",dietEntity.morning_drink);
        jsonParserVolley.addParameter("morning_break",dietEntity.morning_breakfast);
        jsonParserVolley.addParameter("lunch",dietEntity.lunch);
        jsonParserVolley.addParameter("evening_break",dietEntity.evening_breakfast);
        jsonParserVolley.addParameter("dinner",dietEntity.dinner);
        jsonParserVolley.addParameter("exercise",dietEntity.exercise);
        String url = WebUrl.ADD_DIET;
        jsonParserVolley.executeRequest(Request.Method.POST, url,new JsonParserVolley.VolleyCallback() {
                    @Override
                    public void getResponse(String response) {
                        hideProgressLoad();
                        try {
                            if (response != null){
                                if(response.trim().length() > 0){
                                    JSONObject jsonObject=new JSONObject(response);
                                    String success = jsonObject.get("success").toString();
                                    if (success != null && success.trim().equals("1")) {
                                        Toast.makeText(DietAddUpdateActivity.this, "Diet added successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else if (success != null && success.trim().equals("2")) {
                                        Toast.makeText(DietAddUpdateActivity.this, "Diet is already exist for this bmi range", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(DietAddUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("stest","response="+e.getMessage());
                        }
                    }
                }
        );

    }

    private void updateDietToserver(DietEntity dietEntity) {
        showProgressLoad();
        final JsonParserVolley jsonParserVolley = new JsonParserVolley(DietAddUpdateActivity.this);
        jsonParserVolley.addParameter("bmi_range",dietEntity.bmi_range);
        jsonParserVolley.addParameter("water",dietEntity.water);
        jsonParserVolley.addParameter("morning_drink",dietEntity.morning_drink);
        jsonParserVolley.addParameter("morning_break",dietEntity.morning_breakfast);
        jsonParserVolley.addParameter("lunch",dietEntity.lunch);
        jsonParserVolley.addParameter("evening_break",dietEntity.evening_breakfast);
        jsonParserVolley.addParameter("dinner",dietEntity.dinner);
        jsonParserVolley.addParameter("exercise",dietEntity.exercise);

        String url = WebUrl.Update_DIET;
        jsonParserVolley.executeRequest(Request.Method.POST, url,new JsonParserVolley.VolleyCallback() {
                    @Override
                    public void getResponse(String response) {
                        hideProgressLoad();
                        try {
                            if (response != null){
                                if(response.trim().length() > 0){
                                    JSONObject jsonObject=new JSONObject(response);
                                    String success = jsonObject.get("success").toString();
                                    if (success != null && success.trim().equals("1")) {
                                        Toast.makeText(DietAddUpdateActivity.this, "Diet updated successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }  else {
                                        Toast.makeText(DietAddUpdateActivity.this, "No changes added", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("stest","response="+e.getMessage());
                        }
                    }
                }
        );
    }


    private void showProgressLoad(){
        pDialog = new ProgressDialog(DietAddUpdateActivity.this);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    private void hideProgressLoad(){
        if (pDialog != null){
            pDialog.dismiss();
        }
    }
}