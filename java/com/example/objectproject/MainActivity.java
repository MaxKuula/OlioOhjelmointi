package com.example.objectproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PopUp.PopUpListener {
    DrawerLayout drawer;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Creates a toolbar that has a hamburger menu icon */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wellbeing");

        /* side menu setup */
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationview = findViewById(R.id.nav_view);
        navigationview.setNavigationItemSelectedListener(this);

        /* Makes the hamburger menu icon act as a button that opens side menu */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /* When activity is started, opens HomepageFragment */
        if (savedInstanceState == null) {
            if (UserProfile.getInstance().getSelectedPage().equals("Homepage")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmet_container,
                        new HomepageFragment()).commit();
                navigationview.setCheckedItem(R.id.nav_homepage);
            }
            else if (UserProfile.getInstance().getSelectedPage().equals("Profile")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmet_container,
                        new ProfileFragment()).commit();
                navigationview.setCheckedItem(R.id.nav_profile);
            }
            else if (UserProfile.getInstance().getSelectedPage().equals("Nutrition")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmet_container,
                        new NutritionFragment()).commit();
                navigationview.setCheckedItem(R.id.nav_nutrition);
            }
            else if (UserProfile.getInstance().getSelectedPage().equals("Activity")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmet_container,
                        new ActivityFragment()).commit();
                navigationview.setCheckedItem(R.id.nav_activity);
            }
        }

        context = MainActivity.this;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    /* This method defines what happens when each item in side menu is clicked (opens correct fragment) */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_homepage:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmet_container, new HomepageFragment()).commit();
                LogManager.getInstance().appendLog(context, "Moved to Homepage");
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmet_container, new ProfileFragment()).commit();
                LogManager.getInstance().appendLog(context, "Moved to Profile");
                break;
            case R.id.nav_nutrition:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmet_container, new NutritionFragment()).commit();
                LogManager.getInstance().appendLog(context, "Moved to Nutrition");
                break;
            case R.id.nav_activity:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmet_container, new ActivityFragment()).commit();
                LogManager.getInstance().appendLog(context, "Moved to Activity");
                break;
            case R.id.nav_logout:
                logOut();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    /* This method makes it so that if the back button is pressed, when the side menu is opened, the side menu is closed instead of the phone closing the app */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /* This method applies the texts from the nutrition popup */
    @Override
    public void applyTexts(String energy, String beef, String fish, String pork, String dairy, String cheese, String rice, String salad, String restaurant) {
        NutritionFragment fragment = (NutritionFragment) getSupportFragmentManager().findFragmentById(R.id.fragmet_container);
        fragment.setNutritionInfo();
    }

    /* hides soft keyboard */
    public void keyboardDown() {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /* resets info in UserProfile and starts LogInActivity */
    public void logOut() {
        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
        startActivity(intent);
        LogManager.getInstance().appendLog(context,UserProfile.getInstance().getUsername() + ": logged out");
        UserProfile.getInstance().resetAll();
    }
}