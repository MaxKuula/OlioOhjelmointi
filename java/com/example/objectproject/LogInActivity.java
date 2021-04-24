package com.example.objectproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LogInActivity extends AppCompatActivity {

    UserManagement userMan = new UserManagement();
    FragmentManager fragMan;
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        fragMan = getSupportFragmentManager();

        /* When LogInActivity is started, LogInMainFragment is opened */
        fragMan.beginTransaction().replace(R.id.login_fragmentcontainer,
                new LogInMainFragment()).commit();

        context = LogInActivity.this;

       if (fileExist("log.txt")) {
           System.out.println("log file already exists");
       } else {
           LogManager.getInstance().createLog(context);
           LogManager.getInstance().appendLog(context, "log created");
       }

        if (fileExist("users.txt")) {
            System.out.println("user management file already exists");
        } else {
            userMan.createUserManagementFile(context);
            LogManager.getInstance().appendLog(context, "users file created");
        }

    }

    /* checks if file already exists */
    public boolean fileExist(String filename){
        File file = getBaseContext().getFileStreamPath(filename);
        return file.exists();
    }

    /* Replaces LogInMainFragment with LogInInfoFragment */
    public void createProfile() {
        fragMan.beginTransaction().replace(R.id.login_fragmentcontainer,
                new LogInInfoFragment()).commit();
    }

    /* Launches MainActivity */
    public void logIn() {
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
        LogManager.getInstance().appendLog(context,UserProfile.getInstance().getUsername() + ": logged in");
    }

    /* Launches MainActivity */
    public void confirmProfile() {
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
        LogManager.getInstance().appendLog(context,"new profile created");
    }
}