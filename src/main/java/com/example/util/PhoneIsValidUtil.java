package com.example.util;


public class PhoneIsValidUtil {

    public static boolean checkPhone(String phone) {
        if (!phone.startsWith("+998") || phone.length() != 13) return false;
        return phone.substring(2).chars().allMatch(Character::isDigit);
    }
}
