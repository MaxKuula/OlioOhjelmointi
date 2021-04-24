package com.example.objectproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PopUp extends AppCompatDialogFragment {

    private EditText energyText;
    private EditText beefText;
    private EditText fishText;
    private EditText porkText;
    private EditText dairyText;
    private EditText cheeseText;
    private EditText riceText;
    private EditText saladText;
    private EditText restaurantText;
    private PopUpListener listener;

    APIManager apiMan = new APIManager();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_popup, null);

        builder.setView(view)
                .setTitle("Nutrition")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    /* what happens when "ok" button is clicked */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String energy = energyText.getText().toString();
                        UserProfile.getInstance().setDaysEnergy(UserProfile.getInstance().getDaysEnergy()+Integer.parseInt(energy));
                        String beef = beefText.getText().toString();
                        String fish = fishText.getText().toString();
                        String pork = porkText.getText().toString();
                        String dairy = dairyText.getText().toString();
                        String cheese = cheeseText.getText().toString();
                        String rice = riceText.getText().toString();
                        String salad = saladText.getText().toString();
                        String restaurant = restaurantText.getText().toString();

                        String url = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=omnivore&query.lowCarbonPreference=false&query.beefLevel="+beef+"&query.fishLevel="+fish+"&query.porkPoultryLevel="+pork+"&query.dairyLevel="+dairy+"&query.cheeseLevel="+cheese+"&query.riceLevel="+rice+"&query.eggLevel=3&query.winterSaladLevel="+salad+"&query.restaurantSpending="+restaurant;

                        apiMan.readJSON(url);

                        listener.applyTexts(energy, beef, fish, pork, dairy, cheese, rice, salad, restaurant);
                    }
                });

        energyText = view.findViewById(R.id.popup_energyText);
        beefText = view.findViewById(R.id.popup_beefText);
        fishText = view.findViewById(R.id.popup_fishText);
        porkText = view.findViewById(R.id.popup_porkText);
        dairyText = view.findViewById(R.id.popup_dairyText);
        cheeseText = view.findViewById(R.id.popup_cheeseText);
        riceText = view.findViewById(R.id.popup_riseText);
        saladText = view.findViewById(R.id.popup_saladText);
        restaurantText = view.findViewById(R.id.popup_restaurantText);

        return builder.create();
    }

    /* this method defines what popup listener does (runs applyTexts function) */
    public interface PopUpListener {
        void applyTexts(String energy, String beef, String fish, String pork, String dairy, String cheese, String rice, String salad, String restaurant);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        /* trys that popup listener is implemented */
        try {
            listener = (PopUpListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement PopUpListener");
        }
    }
}
