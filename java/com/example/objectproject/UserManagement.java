package com.example.objectproject;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class UserManagement {

    public void createUserManagementFile(Context context) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("users.txt", MODE_PRIVATE));
            osw.write("");
            osw.close();
        } catch (IOException e) {

        }
    }

    public void addUser(Context context) {

        String username = UserProfile.getInstance().getUsername();
        String password = UserProfile.getInstance().getPassword();
        String name = UserProfile.getInstance().getName();
        String age = String.valueOf(UserProfile.getInstance().getAge());
        String weight = String.valueOf(UserProfile.getInstance().getWeight());
        String height = String.valueOf(UserProfile.getInstance().getHeight());
        String gender = UserProfile.getInstance().getGender();
        String location = UserProfile.getInstance().getLocation();
        String activityLevel = UserProfile.getInstance().getActivityLevel();
        String bmi = String.valueOf(UserProfile.getInstance().getBmi());
        String energyGoal = String.valueOf(UserProfile.getInstance().getEnergyGoal());
        String daysEnergy = String.valueOf(UserProfile.getInstance().getDaysEnergy());
        String activityGoal = String.valueOf(UserProfile.getInstance().getActivityGoal());
        String currentActivity = String.valueOf(UserProfile.getInstance().getCurrentActivity());
        String currentPage = UserProfile.getInstance().getSelectedPage();

        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("users.txt", Context.MODE_APPEND));
            osw.write(username + ";" + password + ";" + name + ";" + age + ";" + weight + ";" + height + ";" + gender + ";" + location + ";" + activityLevel + ";" + bmi + ";" + energyGoal + ";" + daysEnergy + ";" + activityGoal + ";" + currentActivity + ";" + currentPage + ";\n");
            osw.close();
        } catch (IOException e) {

        }
        LogManager.getInstance().appendLog(context, "User: " + username + " added");
    }

    /* checks that username and password match user database and sets info to UserProfile */
    public boolean logIn(Context context, String username, String password) {
        try {
            InputStream ins = context.openFileInput("users.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String s = "";

            while ((s = br.readLine()) != null) {
                System.out.println(s);
                String array[] = s.split(";");

                if (array[0].equals(username)) {
                    System.out.println("User found!");
                    if (array[1].equals(password)) {
                        System.out.println("Password match!");

                        UserProfile.getInstance().setLogInInfo(username, password);
                        UserProfile.getInstance().setBasicInfo(array[2], Integer.valueOf(array[3]), Integer.valueOf(array[4]), Integer.valueOf(array[5]), array[6], array[7], array[8]);
                        UserProfile.getInstance().setDaysEnergy(Integer.valueOf(array[11]));
                        UserProfile.getInstance().setActivityGoal(Integer.valueOf(array[12]));
                        UserProfile.getInstance().setCurrentActivity(Integer.valueOf(array[13]));
                        UserProfile.getInstance().setSelectedPage(array[14]);

                        return true;
                    }
                }
            }

            ins.close();
        } catch (IOException e) {

        }
        return false;
    }

    /* deletes and re-adds the updated user to users.txt file */
    public void editUser(Context context, String username) {
        try {
            List<String> rows = new LinkedList<>();
            int marker = 0;
            int rowLocation = 0;

            InputStream ins = context.openFileInput("users.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String s = "";

            while ((s = br.readLine()) != null) {
                rows.add(s);

                String array[] = s.split(";");
                if (array[0].equals(username)) {
                    rowLocation = marker;
                }
                marker++;
            }

            rows.remove(rowLocation);

            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("users.txt", MODE_PRIVATE));
            for (int i=0; i<rows.size(); i++) {
                osw.write(rows.get(i) + "\n");
            }
            osw.close();

            addUser(context);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* checks if there is a user with sepecific username in users.txt */
    public boolean doesUserExist(Context context, String username) {
        try {
            InputStream ins = context.openFileInput("users.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String s = "";

            while ((s = br.readLine()) != null) {
                System.out.println(s);
                String array[] = s.split(";");

                if (array[0].equals(username)) {
                    System.out.println("User found!");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}