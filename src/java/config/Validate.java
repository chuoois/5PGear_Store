package config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

    public static boolean checkEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkFullName(String fullname) {
        String regex = "^[\\p{L} ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fullname);
        return matcher.matches();
    }

    public static boolean checkPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?:{}|<>]).{8,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean checkPhone(String phone) {
        String regex = "^0\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
