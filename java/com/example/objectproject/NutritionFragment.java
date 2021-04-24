package com.example.objectproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NutritionFragment extends Fragment implements View.OnClickListener {
    UserManagement userMan = new UserManagement();

    ProgressBar energyGoalBar;

    TextView energyGoalText;

    int energyGoal;
    int currentEnergy;

    String energyProgress = "0 / 2000 kCal";

    Button addNutrition;
    Button resetNutrition;

    TextView dairyText;
    TextView meatText;
    TextView vegetablesText;
    TextView restaurantText;
    TextView totalText;

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nutrition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addNutrition = view.findViewById(R.id.nutrition_addFood);
        addNutrition.setOnClickListener(this);

        resetNutrition = view.findViewById(R.id.nutrition_reset);
        resetNutrition.setOnClickListener(this);

        dairyText = view.findViewById(R.id.nutrition_dairy);
        dairyText.setText(String.valueOf(UserProfile.getInstance().getDairyCFP()));

        meatText = view.findViewById(R.id.nutrition_meat);
        meatText.setText(String.valueOf(UserProfile.getInstance().getMeatCFP()));

        vegetablesText = view.findViewById(R.id.nutrition_vegetables);
        vegetablesText.setText(String.valueOf(UserProfile.getInstance().getVegetableCFP()));

        restaurantText = view.findViewById(R.id.nutrition_restaurant);
        restaurantText.setText(String.valueOf(UserProfile.getInstance().getRestaurantCFP()));

        totalText = view.findViewById(R.id.nutrition_total);
        totalText.setText(String.valueOf(UserProfile.getInstance().getTotalCFP()));

        currentEnergy = UserProfile.getInstance().getDaysEnergy();
        energyGoal = UserProfile.getInstance().getEnergyGoal();

        energyProgress = String.valueOf(currentEnergy) + " / " + String.valueOf(energyGoal) + " kCal";

        energyGoalText = view.findViewById(R.id.nutrition_goalText);
        energyGoalText.setText(energyProgress);

        energyGoalBar = view.findViewById(R.id.nutrition_progressBar);
        energyGoalBar.setMax(energyGoal);
        energyGoalBar.setProgress(UserProfile.getInstance().getDaysEnergy());

        context = getActivity();

        UserProfile.getInstance().setSelectedPage("Nutrition");
        userMan.editUser(context, UserProfile.getInstance().getUsername());
    }

    /* this method senses which button is pressed and executes the correct functions */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nutrition_addFood:
                openPopUp();
                break;
            case R.id.nutrition_reset:
                resetNutrition();
                break;
        }

    }

    /* this method opens up a popup screen that has input boxes for all nutrition info */
    private void openPopUp() {
        PopUp popup = new PopUp();
        popup.show(getFragmentManager(), "nutrition popup");
        LogManager.getInstance().appendLog(context, UserProfile.getInstance().getUsername() + ": Popup opened (Nutrition)");
    }

    /* resets nutrition progress and sets current energy to 0 */
    private void resetNutrition() {
        UserProfile.getInstance().resetEnergyInfo();
        currentEnergy = 0;
        energyProgress = String.valueOf(currentEnergy) + " / " + String.valueOf(energyGoal) + " kCal";
        energyGoalText.setText(energyProgress);
        energyGoalBar.setProgress(0);
        userMan.editUser(context, UserProfile.getInstance().getUsername());
        LogManager.getInstance().appendLog(context, UserProfile.getInstance().getUsername() + ": Nutrition reset (Nutrition)");
    }

    /* sets info inputs from popup to actual program */
    public void setNutritionInfo() {
        currentEnergy = UserProfile.getInstance().getDaysEnergy();
        energyProgress = String.valueOf(currentEnergy) + " / " + String.valueOf(energyGoal);
        energyGoalText.setText(energyProgress);
        energyGoalBar.setProgress(currentEnergy);
        dairyText.setText(String.format("%.2f", UserProfile.getInstance().getDairyCFP()));
        meatText.setText(String.format("%.2f", UserProfile.getInstance().getMeatCFP()));
        vegetablesText.setText(String.format("%.2f", UserProfile.getInstance().getVegetableCFP()));
        restaurantText.setText(String.format("%.2f", UserProfile.getInstance().getRestaurantCFP()));
        totalText.setText(String.format("%.2f", UserProfile.getInstance().getTotalCFP()));
        userMan.editUser(context, UserProfile.getInstance().getUsername());
        LogManager.getInstance().appendLog(context, UserProfile.getInstance().getUsername() + ": Nutrition info set");
    }
}
