package com.example.objectproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LogInMainFragment extends Fragment implements View.OnClickListener {
    TextView header;
    EditText usernameInput;
    EditText passwordInput;
    View view;

    Button signInbutton;
    Button registerButton;

    String username;
    String password;

    UserManagement userMan = new UserManagement();

    PasswordChecker passCheck = new PasswordChecker();

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loginmain, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        header = view.findViewById(R.id.logInHeader);

        usernameInput = view.findViewById(R.id.usernameText);
        passwordInput = view.findViewById(R.id.passwordText);

        signInbutton = view.findViewById(R.id.logInButton);
        signInbutton.setOnClickListener(this);

        registerButton = view.findViewById(R.id.createProfileButton);
        registerButton.setOnClickListener(this);

        context = getActivity();
    }

    /* This method tests what button is clicked and runs the correct functions */
    @Override
    public void onClick(View v) {

        /* Checks that both username and password fields have something in them */
        if (usernameInput.getText().toString().equals("") || passwordInput.getText().toString().equals("")) {
            header.setText("Insert both username and password.");
            header.setTextColor(Color.RED);
            return;
        }

        switch (v.getId()){
            case R.id.logInButton:
                /* If Log In is clicked */
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();

                if (userMan.logIn(context, username, password) == true) {
                    ((LogInActivity)getActivity()).logIn();
                } else {
                    header.setText("Username or password incorrect!");
                    header.setTextColor(Color.RED);
                }
                break;
            case R.id.createProfileButton:
                /* If create new profile is clicked */
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();

                if (userMan.doesUserExist(context, username) == true) {
                    header.setText("User already exist, please change the username");
                    return;
                }

                if (passCheck.checkLength(password) == false) {
                    header.setText("Password must be at least 12 characters long");
                    header.setTextColor(Color.RED);
                    return;
                }
                else if (passCheck.checkLetterandNumber(password) == false) {
                    header.setText("Password must have one capital and lowercase letter and a number");
                    header.setTextColor(Color.RED);
                    return;
                }
                else if (passCheck.checkSpecial(password) == false) {
                    header.setText("Password must contain one special character");
                    header.setTextColor(Color.RED);
                    return;
                }

                /* Saves log in info to UserProfile */
                UserProfile.getInstance().setLogInInfo(username, password);

                /* Calls createProfile() method from LogInActivity */
                ((LogInActivity)getActivity()).createProfile();
                break;
        }

    }
}
