package com.example.objectproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    /* buttonMode = 0 means that text is not editable and 1 means that it is editable */
    int buttonMode;
    String selectedGender = "Male";
    String selectedActivityLevel = "Sedentary";

    TextView profile_header;

    EditText nameText;
    EditText ageText;
    EditText weightText;
    EditText heightText;
    EditText locationText;
    TextView bmiText;

    Spinner genderSpinner;
    Spinner activityLevelSpinner;

    Button editButton;

    UserManagement userMan = new UserManagement();

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        buttonMode = 0;
        profile_header = view.findViewById(R.id.profile_text);

        bmiText = view.findViewById(R.id.profile_bmiText);
        bmiText.setText(String.valueOf(String.format("%.2f", UserProfile.getInstance().getBmi())));

        /* Profile info EditText setup, setting texts and selections from UserProfile class, and making them uneditable */
        nameText = view.findViewById(R.id.profile_name);
        nameText.setText(UserProfile.getInstance().getName());
        nameText.setEnabled(false);

        ageText = view.findViewById(R.id.profile_age);
        ageText.setText(String.valueOf(UserProfile.getInstance().getAge()));
        ageText.setEnabled(false);

        weightText = view.findViewById(R.id.profile_weight);
        weightText.setText(String.valueOf(UserProfile.getInstance().getWeight()));
        weightText.setEnabled(false);

        heightText = view.findViewById(R.id.profile_height);
        heightText.setText(String.valueOf(UserProfile.getInstance().getHeight()));
        heightText.setEnabled(false);

        locationText = view.findViewById(R.id.profile_location);
        locationText.setText(UserProfile.getInstance().getLocation());
        locationText.setEnabled(false);

        genderSpinner = view.findViewById(R.id.profile_gender);

        /* Gender Spinner population */
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.genders, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        /* Gender spinner selected string selection */
        if (UserProfile.getInstance().getGender().equals("Male")) {
            genderSpinner.setSelection(0);
        }
        else {
            genderSpinner.setSelection(1);
        }
        genderSpinner.setEnabled(false);

        activityLevelSpinner = view.findViewById(R.id.profile_activityLevel);

        /* Activity Level Spinner population */
        ArrayAdapter<CharSequence> activityLevelAdapter = ArrayAdapter.createFromResource(getContext(), R.array.activitylevels, android.R.layout.simple_spinner_item);
        activityLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelSpinner.setAdapter(activityLevelAdapter);

        /* Activity Level Spinner selected string selection */
        if (UserProfile.getInstance().getActivityLevel().equals("Sedentary")) {
            activityLevelSpinner.setSelection(0);
        }
        else if (UserProfile.getInstance().getActivityLevel().equals("Light")) {
            activityLevelSpinner.setSelection(1);
        }
        else if (UserProfile.getInstance().getActivityLevel().equals("Moderate")) {
            activityLevelSpinner.setSelection(2);
        }
        else if (UserProfile.getInstance().getActivityLevel().equals("Active")) {
            activityLevelSpinner.setSelection(3);
        }
        else {
            activityLevelSpinner.setSelection(4);
        }
        activityLevelSpinner.setEnabled(false);

        /* spinner listeners, so it is possible to select something from them */
        genderSpinner.setOnItemSelectedListener(this);
        activityLevelSpinner.setOnItemSelectedListener(this);

        editButton = view.findViewById(R.id.profile_button);
        editButton.setOnClickListener(this);

        context = getActivity();

        UserProfile.getInstance().setSelectedPage("Profile");
        userMan.editUser(context, UserProfile.getInstance().getUsername());
    }

    /* This method defines what happens when something is selected from one of the spinners */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.profile_gender) {
            selectedGender = parent.getSelectedItem().toString();
        }
        else if (parent.getId() == R.id.profile_activityLevel) {
            selectedActivityLevel = parent.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /* This method defines what happens when the button is clicked in the profile fragment */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profile_button) {
            editButtonClick();
        }
    }

    /* this method contains what happens when the button is clicked (when buttonMode = 0 edit mode is off and vice versa) */
    public void editButtonClick() {
        if (buttonMode == 0) {
            editButton.setText("Confirm");

            /* Makes all boxes editable */
            nameText.setEnabled(true);
            ageText.setEnabled(true);
            weightText.setEnabled(true);
            heightText.setEnabled(true);
            locationText.setEnabled(true);
            genderSpinner.setEnabled(true);
            activityLevelSpinner.setEnabled(true);

            buttonMode = 1;

            LogManager.getInstance().appendLog(context, UserProfile.getInstance().getUsername() + ": Started editing profile (Profile)");
        }
        else if (buttonMode == 1) {

            /* Checks that all boxes are filled */
            if (nameText.getText().toString().equals("") || ageText.getText().toString().equals("") || weightText.getText().toString().equals("") || heightText.getText().toString().equals("") || locationText.getText().toString().equals("")) {
                profile_header.setText("Fill each box!");
                profile_header.setTextColor(Color.RED);
                return;
            }

            /* Checks that all boxes that require numbers contain numbers and not text */
            else if (TextUtils.isDigitsOnly(ageText.getText().toString()) == false || TextUtils.isDigitsOnly(weightText.getText().toString()) == false || TextUtils.isDigitsOnly(heightText.getText().toString()) == false) {
                profile_header.setText("Age, height, and weight must be numbers!");
                profile_header.setTextColor(Color.RED);
                return;
            }
            editButton.setText("Edit");

            /* Makes all boxes uneditable */
            nameText.setEnabled(false);
            ageText.setEnabled(false);
            weightText.setEnabled(false);
            heightText.setEnabled(false);
            locationText.setEnabled(false);
            genderSpinner.setEnabled(false);
            activityLevelSpinner.setEnabled(false);

            /* Saves info from boxes to UserProfile */
            UserProfile.getInstance().setBasicInfo(nameText.getText().toString(), Integer.parseInt(ageText.getText().toString()), Integer.parseInt(weightText.getText().toString()), Integer.parseInt(heightText.getText().toString()), selectedGender, locationText.getText().toString(), selectedActivityLevel);
            bmiText.setText(String.format("%.2f", UserProfile.getInstance().getBmi()));

            buttonMode = 0;
            profile_header.setText("");

            userMan.editUser(context, UserProfile.getInstance().getUsername());

            LogManager.getInstance().appendLog(context, UserProfile.getInstance().getUsername() + ": Stopped editing profile (Profile)");
        }
    }
}
