package com.apoorv.dubey.android.instadia;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Apoorv on 29/12/17.
 */

public class ValidationUtils {

 public static boolean isValidName(String name){
     Pattern pattern;
     Matcher matcher;

     String name_regex = "^[\\p{L} .'-]+$";

     pattern = Pattern.compile(name_regex);

     matcher = pattern.matcher(name);


     if (matcher.matches()) {
         return true;
     }
     return false;
 }

    public static boolean isConfirmedName(String trim, String trim1) {

        if (trim1.equals(trim)) {
            return true;
        }else
            return false;
    }

    public static boolean isValidAge(String age) {
        Pattern pattern;
        Matcher matcher;
        String number_regex = "^[0-9]*$";
        pattern = Pattern.compile(number_regex);
        matcher = pattern.matcher(age);
        if (matcher.matches() && age.length()<3) {
            return true;
        }
        return false;
    }

    public static boolean isConfirmedAge(String trim, String trim1) {
        if (trim1.equals(trim))
            return  true;
        else
            return false;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern;
        Matcher matcher;
        String number_regex = "^[0-9]*$";
        pattern = Pattern.compile(number_regex);
        matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()) {
            if (phoneNumber.length() == 10) {
                if (phoneNumber.charAt(0) > '5' )
                    return true;
            }
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;

        String email_regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(email_regex);

        matcher = pattern.matcher(email);


        if (matcher.matches() || email.length()==0) {
            return true;
        }
        return false;
    }

    public static boolean isValidAmount(String amount,String userValue) {
        Pattern pattern;
        Matcher matcher;
        String number_regex = "\\d+(\\.\\d+)?";
        if((userValue).equals(null) || userValue== null || (!userValue.equals(null) && userValue.length()==0)){
            return false;
        }       else if(Double.valueOf(amount)>=Double.valueOf(userValue) ) {
            pattern = Pattern.compile(number_regex);
            matcher = pattern.matcher(amount);
            if (matcher.matches()) {
                return true;
            }}
        return false;
    }



}
