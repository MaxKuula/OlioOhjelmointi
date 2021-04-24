package com.example.objectproject;

public class UserProfile {
    private static UserProfile user = new UserProfile();

    Calculator calc = new Calculator();

    private String selectedPage = "Homepage";


    /* Log in info*/
    private  String username = "";
    private String password = "";

    /* Basic info */
    private String name = "";
    private int age = 0;
    private int weight = 0;
    private int height = 0;
    private String gender = "";
    private String location = "";
    private String activityLevel = "";
    private double bmi = 0;

    private int energyGoal = 0;
    private int daysEnergy = 0;

    private int activityGoal = 0;
    private int currentActivity = 0;

    /* carbon footprint info */
    private double dairyCFP = 0;
    private double meatCFP = 0;
    private double vegetableCFP = 0;
    private double restaurantCFP = 0;
    private double totalCFP = 0;


    public static UserProfile getInstance() {return user;}

    public void setSelectedPage(String page) {
        selectedPage = page;
    }

    public void setWeight(int input) {
        weight = input;
        bmi = calc.bmiCalc(weight, height);
        energyGoal = calc.calorieGoalCalc(gender, weight, height, age, activityLevel);
    }

    public void setLogInInfo(String usernameInput, String passwordInput) {
        username = usernameInput;
        password = passwordInput;
    }

    public void setBasicInfo(String nameInput, int ageInput, int weightInput, int heightInput, String genderInput, String locationInput, String activityLevelInput) {
        name = nameInput;
        age = ageInput;
        weight = weightInput;
        height = heightInput;
        gender = genderInput;
        location = locationInput;
        activityLevel = activityLevelInput;

        bmi = calc.bmiCalc(weight, height);
        energyGoal = calc.calorieGoalCalc(gender, weight, height, age, activityLevel);
    }

    public void setCarbonFPInfo(double dairy, double meat, double vegetables, double restaurant, double total) {
        dairyCFP = dairy;
        meatCFP = meat;
        vegetableCFP = vegetables;
        restaurantCFP = restaurant;
        totalCFP = total;
    }

    public void setCurrentActivity(int activity) {
        currentActivity = activity;
    }

    public void setActivityGoal(int goal) {
        activityGoal = goal;
    }

    public void setDaysEnergy (int input) {
        daysEnergy = input;
    }

    public void resetEnergyInfo() {
        daysEnergy = 0;
    }

    public void resetActivity() {
        currentActivity = 0;
    }

    public String getSelectedPage() {
        return selectedPage;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public int getWeight() {
        return weight;
    }
    public int getHeight() {
        return height;
    }
    public String getGender() {
        return gender;
    }
    public String getLocation() {
        return location;
    }
    public String getActivityLevel() {
        return activityLevel;
    }
    public double getBmi() { return bmi; }

    public int getEnergyGoal() { return energyGoal; }
    public int getDaysEnergy() { return daysEnergy; }

    public double getDairyCFP() { return dairyCFP; }
    public double getMeatCFP() { return meatCFP; }
    public double getVegetableCFP() { return vegetableCFP; }
    public double getRestaurantCFP() { return restaurantCFP; }
    public double getTotalCFP() { return totalCFP; }

    public int getActivityGoal() { return activityGoal; }
    public int getCurrentActivity() { return currentActivity; }

    public void resetAll() {
        username = "";
        password = "";
        name = "";
        age = 0;
        weight = 0;
        height = 0;
        gender = "";
        location = "";
        activityLevel = "";
        bmi = 0;
        energyGoal = 0;
        daysEnergy = 0;
        activityGoal = 0;
        currentActivity = 0;
        dairyCFP = 0;
        meatCFP = 0;
        vegetableCFP = 0;
        restaurantCFP = 0;
        totalCFP = 0;
    }
}
