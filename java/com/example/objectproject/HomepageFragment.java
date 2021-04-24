package com.example.objectproject;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomepageFragment extends Fragment implements View.OnClickListener {
    UserManagement userMan = new UserManagement();

    TextView nutritionGoalText;
    TextView activityGoalText;
    TextView weightText;
    TextView errorText;

    ProgressBar nutritionProgressBar;
    ProgressBar activityProgressBar;

    EditText weightInput;
    EditText energyInput;
    EditText activityInput;

    Button updateWeightButton;
    Button addEnergyButton;
    Button addActivityButton;
    Button logOutButton;

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        nutritionGoalText = view.findViewById(R.id.homepage_foodGoalText);
        nutritionGoalText.setText(UserProfile.getInstance().getDaysEnergy() + " / " + UserProfile.getInstance().getEnergyGoal() + " kCal");

        activityGoalText = view.findViewById(R.id.homepage_activityGoalText);
        activityGoalText.setText(String.valueOf(UserProfile.getInstance().getCurrentActivity()) + " / " + String.valueOf(UserProfile.getInstance().getActivityGoal()) + " min");

        weightText = view.findViewById(R.id.homepage_weightText);
        weightText.setText(String.valueOf(UserProfile.getInstance().getWeight()));

        errorText = view.findViewById(R.id.homepage_errorText);

        nutritionProgressBar = view.findViewById(R.id.homepage_nutritionProgressBar);
        nutritionProgressBar.setMax(UserProfile.getInstance().getEnergyGoal());
        nutritionProgressBar.setProgress(UserProfile.getInstance().getDaysEnergy());

        activityProgressBar = view.findViewById(R.id.homepage_activityProgressBar);
        activityProgressBar.setMax(UserProfile.getInstance().getActivityGoal());
        activityProgressBar.setProgress(UserProfile.getInstance().getCurrentActivity());

        weightInput = view.findViewById(R.id.homepage_weightInput);
        energyInput = view.findViewById(R.id.homepage_energyInput);
        activityInput = view.findViewById(R.id.homepage_activityInput);

        updateWeightButton = view.findViewById(R.id.homepage_updateWeightButton);
        updateWeightButton.setOnClickListener(this);

        addEnergyButton = view.findViewById(R.id.homepage_addEnergyButton);
        addEnergyButton.setOnClickListener(this);

        addActivityButton = view.findViewById(R.id.homepage_addActivityButton);
        addActivityButton.setOnClickListener(this);

        logOutButton = view.findViewById(R.id.homepage_logoutButton);
        logOutButton.setOnClickListener(this);

        context = getActivity();

        UserProfile.getInstance().setSelectedPage("Homepage");
        userMan.editUser(context, UserProfile.getInstance().getUsername());
    }

    /* this method senses which button is pressed and executes the correct functions */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homepage_updateWeightButton:
                /* checks that EditText has text in it */
                if (weightInput.getText().toString().equals("")) {
                    errorText.setText("Insert value");
                    return;
                }
                /* checks that EditText text is numerical */
                else if (TextUtils.isDigitsOnly(weightInput.getText().toString()) == false) {
                    errorText.setText("Input must be a number");
                    return;
                }
                setWeight(weightInput.getText().toString());
                ((MainActivity)getActivity()).keyboardDown();
                break;
            case R.id.homepage_addEnergyButton:
                if (energyInput.getText().toString().equals("")) {
                    errorText.setText("Insert value");
                    return;
                }
                else if (TextUtils.isDigitsOnly(energyInput.getText().toString()) == false) {
                    errorText.setText("Input must be a number");
                    return;
                }
                addEnergy(energyInput.getText().toString());
                ((MainActivity)getActivity()).keyboardDown();
                break;
            case R.id.homepage_addActivityButton:
                if (activityInput.getText().toString().equals("")) {
                    errorText.setText("Insert value");
                    return;
                }
                else if (TextUtils.isDigitsOnly(activityInput.getText().toString()) == false) {
                    errorText.setText("Input must be a number");
                    return;
                }
                addActivity(activityInput.getText().toString());
                ((MainActivity)getActivity()).keyboardDown();
                break;
            case R.id.homepage_logoutButton:
                ((MainActivity)getActivity()).logOut();
                break;
        }
    }

    /* sets new weight to UserProfile and sets it to the right places on the screen */
    private void setWeight(String newWeight) {
        UserProfile.getInstance().setWeight(Integer.parseInt(newWeight));
        weightText.setText(String.valueOf(UserProfile.getInstance().getWeight()));
        weightInput.setText("");
        nutritionProgressBar.setMax(UserProfile.getInstance().getEnergyGoal());
        nutritionGoalText.setText(UserProfile.getInstance().getDaysEnergy() + " / " + UserProfile.getInstance().getEnergyGoal() + " kCal");
        userMan.editUser(context, UserProfile.getInstance().getUsername());
        LogManager.getInstance().appendLog(context, UserProfile.getInstance().getUsername() + ": Weight set to " + newWeight + " (Homepage)");
    }

    /* adds energy from EditText to UserProfile and sets it to the right places on the screen */
    private void addEnergy(String energy) {
        UserProfile.getInstance().setDaysEnergy(UserProfile.getInstance().getDaysEnergy() + Integer.parseInt(energy));
        nutritionGoalText.setText(String.valueOf(UserProfile.getInstance().getDaysEnergy()) + " / " + String.valueOf(UserProfile.getInstance().getEnergyGoal()) + " kCal");
        nutritionProgressBar.setProgress(UserProfile.getInstance().getDaysEnergy());
        errorText.setText("");
        energyInput.setText("");
        userMan.editUser(context, UserProfile.getInstance().getUsername());
        LogManager.getInstance().appendLog(context, UserProfile.getInstance().getUsername() + ": " + energy + " energy added (Homepage)");
    }

    /* adds activity from EditText to UserProfile and sets it to the right places on the screen */
    private void addActivity(String activity) {
        UserProfile.getInstance().setCurrentActivity(UserProfile.getInstance().getCurrentActivity() + Integer.parseInt(activity));
        activityGoalText.setText(String.valueOf(UserProfile.getInstance().getCurrentActivity()) + " / " + String.valueOf(UserProfile.getInstance().getActivityGoal()) + " min");
        activityProgressBar.setProgress(UserProfile.getInstance().getCurrentActivity());
        errorText.setText("");
        activityInput.setText("");
        userMan.editUser(context, UserProfile.getInstance().getUsername());
        LogManager.getInstance().appendLog(context, UserProfile.getInstance().getUsername() + ": " + activity + " activity added (Homepage)");
    }

}
