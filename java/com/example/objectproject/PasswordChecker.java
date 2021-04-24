package com.example.objectproject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordChecker {

    //(sisältää vähintään yhden numeron, erikoismerkin, ison ja pienen kirjaimen, on vähintään 12 merkkiä pitkä)

    public boolean checkLength(String password) {
        if (password.length() >= 12) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkLetterandNumber(String password) {
        char check;
        boolean hasCapital = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;
        for(int i=0;i < password.length();i++) {
            check = password.charAt(i);
            if( Character.isDigit(check)) {
                hasNumber = true;
            }
            else if (Character.isUpperCase(check)) {
                hasCapital = true;
            } else if (Character.isLowerCase(check)) {
                hasLowercase = true;
            }
            if(hasNumber && hasCapital && hasLowercase)
                return true;
        }
        return false;
    }

    public boolean checkSpecial(String password){
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        boolean hasSpecial = matcher.find();
        if (hasSpecial) {
            return true;
        }
        else
            return false;
    }
}
