package com.example.objectproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LogInInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    TextView header;

    EditText nameInput;
    EditText ageInput;
    EditText weightInput;
    EditText heightInput;
    EditText locationInput;

    Spinner genderInput;
    String selectedGender;

    Spinner activityLevelInput;
    String selectedActivityLevel;

    Button confirmButton;

    Context context;

    UserManagement userMan = new UserManagement();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logininfo, container, false);    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        header = view.findViewById(R.id.logInInfoHeader);
        nameInput = view.findViewById(R.id.info_name);
        ageInput = view.findViewById(R.id.info_age);
        weightInput = view.findViewById(R.id.info_weight);
        heightInput = view.findViewById(R.id.info_height);
        locationInput = view.findViewById(R.id.info_location);
        confirmButton = view.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this);

        genderInput = view.findViewById(R.id.info_gender);
        /* Population of genderInput Spinner */
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.genders, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderInput.setAdapter(genderAdapter);

        activityLevelInput = view.findViewById(R.id.info_activitylevel);
        /* Population of activityLevelInput Spinner */
        ArrayAdapter<CharSequence> activityLevelAdapter = ArrayAdapter.createFromResource(getContext(), R.array.activitylevels, android.R.layout.simple_spinner_item);
        activityLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelInput.setAdapter(activityLevelAdapter);

        genderInput.setOnItemSelectedListener(this);
        activityLevelInput.setOnItemSelectedListener(this);

        context = getActivity();
    }

    /* This method defines what happens when a spinner item is clicked (also tests which spinner is clicked) */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.info_gender) {
            selectedGender = parent.getSelectedItem().toString();
        }
        else if (parent.getId() == R.id.info_activitylevel) {
            selectedActivityLevel = parent.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /* This method defines what happens when the confirm button is pressed in LogInInfo */
    @Override
    public void onClick(View v) {
        String name = nameInput.getText().toString();
        String location = locationInput.getText().toString();

        /* Checks that all boxes have something in them */
        if (name.equals("") || ageInput.getText().toString().equals("") || weightInput.getText().toString().equals("") || heightInput.getText().toString().equals("") || location.equals("")) {
            header.setText("Fill each box!");
            header.setTextColor(Color.RED);
            return;
        }

        /* Checks that all boxes that require numbers have numbers and not text */
        else if (TextUtils.isDigitsOnly(ageInput.getText().toString()) == false || TextUtils.isDigitsOnly(weightInput.getText().toString()) == false || TextUtils.isDigitsOnly(heightInput.getText().toString()) == false) {
            header.setText("Age, height, and weight must be numbers!");
            header.setTextColor(Color.RED);
            return;
        }

        int age = Integer.parseInt(ageInput.getText().toString());
        int weight = Integer.parseInt(weightInput.getText().toString());
        int height = Integer.parseInt(heightInput.getText().toString());

        switch (v.getId()) {
            case R.id.confirmButton:
                /* Saves info to UserProfile */
                UserProfile.getInstance().setBasicInfo(name, age, weight, height, selectedGender, location, selectedActivityLevel);

                /* Calls confirmProfile() function from LogInActivity */
                ((LogInActivity)getActivity()).confirmProfile();

                userMan.addUser(context);
                break;
        }
    }
}
