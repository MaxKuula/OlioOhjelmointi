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

public class ActivityFragment extends Fragment implements View.OnClickListener {
    UserManagement userMan = new UserManagement();

    TextView errorText;
    TextView goalProgressText;
    ProgressBar progressbar;
    Button addActivity;
    Button changeGoal;
    Button resetActivity;

    EditText activityInput;
    EditText activityGoalInput;

    int activityGoal = 0;
    int currentActivity = 0;
    String goalTextInsert;

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        errorText = view.findViewById(R.id.activity_errorText);
        goalProgressText = view.findViewById(R.id.activity_goalText);

        activityInput = view.findViewById(R.id.activity_addActivityInput);
        activityGoalInput = view.findViewById(R.id.activity_activityGoalInput);

        progressbar = view.findViewById(R.id.activity_progressBar);

        addActivity = view.findViewById(R.id.activity_addActivityButton);
        addActivity.setOnClickListener(this);

        changeGoal = view.findViewById(R.id.activity_changeGoalButton);
        changeGoal.setOnClickListener(this);

        resetActivity = view.findViewById(R.id.activity_resetActivityButton);
        resetActivity.setOnClickListener(this);

        currentActivity = UserProfile.getInstance().getCurrentActivity();
        activityGoal = UserProfile.getInstance().getActivityGoal();

        progressbar.setMax(activityGoal);
        progressbar.setProgress(currentActivity);

        goalTextInsert = currentActivity + " / " + activityGoal + " min";
        goalProgressText.setText(goalTextInsert);

        context = getActivity();

        UserProfile.getInstance().setSelectedPage("Activity");
        userMan.editUser(context, UserProfile.getInstance().getUsername());
    }

    /* this method senses which button is pressed and executes the correct functions */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_addActivityButton:
                /* checks that EditText has text in it */
                if (activityInput.getText().toString().equals("")) {
                    errorText.setText("Enter a value");
                    return;
                }
                /* checks that EditText text is numerical */
                else if (TextUtils.isDigitsOnly(activityInput.getText().toString()) == false) {
                    errorText.setText("Entered activity value should be a number");
                    return;
                }
                addActivity();
                ((MainActivity)getActivity()).keyboardDown();
                break;
            case R.id.activity_changeGoalButton:
                if (activityGoalInput.getText().toString().equals("")) {
                    errorText.setText("Enter a value");
                    return;
                }
                else if (TextUtils.isDigitsOnly(activityInput.getText().toString()) == false) {
                    errorText.setText("Entered goal value should be a number");
                    return;
                }
                setActivityGoal();
                ((MainActivity)getActivity()).keyboardDown();
                break;
            case R.id.activity_resetActivityButton:
                resetActivity();
                break;
        }
    }

    /* adds activity to UserProfile and sets it to the right places */
    private void addActivity() {
        currentActivity = currentActivity + Integer.parseInt(activityInput.getText().toString());
        UserProfile.getInstance().setCurrentActivity(currentActivity);
        goalTextInsert = currentActivity + " / " + activityGoal + " min";
        goalProgressText.setText(goalTextInsert);
        progressbar.setProgress(currentActivity);
        errorText.setText("");
        userMan.editUser(context, UserProfile.getInstance().getUsername());
        LogManager.getInstance().appendLog(context, activityInput.getText().toString() + " min activity added (activity)");
    }

    /* sets activity goal to UserProfile and sets it to the right places */
    private void setActivityGoal() {
        activityGoal = Integer.parseInt(activityGoalInput.getText().toString());
        UserProfile.getInstance().setActivityGoal(activityGoal);
        goalTextInsert = currentActivity + " / " + activityGoal + " min";
        progressbar.setMax(activityGoal);
        goalProgressText.setText(goalTextInsert);
        errorText.setText("");
        userMan.editUser(context, UserProfile.getInstance().getUsername());
        LogManager.getInstance().appendLog(context, "Activity goal set to " + activityGoalInput.getText().toString() + " (activity)");
    }

    /* sets activity to 0 */
    private void resetActivity() {
        UserProfile.getInstance().resetActivity();
        currentActivity = UserProfile.getInstance().getCurrentActivity();
        goalTextInsert = currentActivity + " / " + activityGoal + " min";
        goalProgressText.setText(goalTextInsert);
        progressbar.setProgress(0);
        userMan.editUser(context, UserProfile.getInstance().getUsername());
        LogManager.getInstance().appendLog(context, "Activity reset (activity)");
    }
}
