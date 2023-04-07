package com.example.myapplication.admin.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.admin.Callback;
import com.example.myapplication.admin.viewmodel.DietAdminViewModel;
import com.example.myapplication.db.DietEntity;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DietAddFragment extends Fragment{

    private AutoCompleteTextView ageAutoTxtView;
    private EditText edt_water, edt_morning, edt_morning_break, edt_lunch, edt_eve_break, edt_dinner, edt_exercise;
    private DietAdminViewModel dietAdminViewModel;
    private ExecutorService executorService;
    private Callback callback;
    private boolean isUpdateMode = false;
    private DietEntity dietEntityReceived;
    private Button addBtn;
    public DietAddFragment() {
        // Required empty public constructor
    }
    public DietAddFragment(Callback callback) {
        this.callback = callback;
    }

    public void setDietEntity(DietEntity dietEntity){
        if (dietEntity == null){
            isUpdateMode = false;
            dietEntityReceived = null;
        } else {
            this.dietEntityReceived = dietEntity;
            isUpdateMode = true;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diet_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ageAutoTxtView = view.findViewById(R.id.ageAutoTxtView);
        edt_water= view.findViewById(R.id.edt_water);
        edt_morning= view.findViewById(R.id.edt_morning);
        edt_morning_break= view.findViewById(R.id.edt_morning_break);
        edt_lunch= view.findViewById(R.id.edt_lunch);
        edt_eve_break= view.findViewById(R.id.edt_eve_break);
        edt_dinner= view.findViewById(R.id.edt_dinner);
        edt_exercise = view.findViewById(R.id.edt_exercise);
        dietAdminViewModel = new ViewModelProvider(getActivity()).get(DietAdminViewModel.class);
        executorService = Executors.newSingleThreadExecutor();
        addBtn = view.findViewById(R.id.btnAdd);
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

    @Override
    public void onResume() {
        super.onResume();

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

    private void setAgeListAdapter(){
        // create list of customer
        ArrayList<String> customerList = getAgeList();
        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, customerList);
        //Set adapter
        ageAutoTxtView.setAdapter(adapter);
    }

    private ArrayList<String> getAgeList()
    {
        ArrayList<String> ageList = new ArrayList<>();
        ageList.add("18-40");
        ageList.add("40-60");
        ageList.add("60 Above");
        ageList.add("18-30");
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
                Toast.makeText(getActivity(), "Please select the age range", Toast.LENGTH_SHORT).show();
                return;
            }
            if (water.trim().equals("")) {
                Toast.makeText(getActivity(), "Please add daily water intake", Toast.LENGTH_SHORT).show();
                return;
            }
            if (morning.trim().equals("")) {
                Toast.makeText(getActivity(), "Please add morning drink", Toast.LENGTH_SHORT).show();
                return;
            }
            if (morningBreak.trim().equals("")) {
                Toast.makeText(getActivity(), "Please add morning breakfast", Toast.LENGTH_SHORT).show();
                return;
            }
            if (lunch.trim().equals("")) {
                Toast.makeText(getActivity(), "Please add lunch", Toast.LENGTH_SHORT).show();
                return;
            }
            if (eveningBrek.trim().equals("")) {
                Toast.makeText(getActivity(), "Please add evening breakfast", Toast.LENGTH_SHORT).show();
                return;
            }
            if (dinner.trim().equals("")) {
                Toast.makeText(getActivity(), "Please add dinner", Toast.LENGTH_SHORT).show();
                return;
            }
            if (exercise.trim().equals("")) {
                Toast.makeText(getActivity(), "Please add exercise", Toast.LENGTH_SHORT).show();
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
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    if (isUpdateMode) {
                        dietAdminViewModel.updateDiet(dietEntity);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "updated successfully", Toast.LENGTH_SHORT).show();
                                callback.fragmentButtonClick(DietAddFragment.this);
                            }
                        });
                    } else {
                        boolean isExist = dietAdminViewModel.isDietExist(ageRange);
                        if (isExist) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Diet is already exist for this age range", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }
                        dietAdminViewModel.addDiet(dietEntity);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Diet added successfully", Toast.LENGTH_SHORT).show();
                                callback.fragmentButtonClick(DietAddFragment.this);
                            }
                        });
                    }
                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }

    }


}