package com.Clinic_Accounting_System.utils;

public class CredUtils {

    private static CredUtils instance;

    private CredUtils() {}

    public static synchronized CredUtils getInstance(){
        if(instance == null) {
            instance = new CredUtils();
        }
        return instance;
    }

    public String getHash(String pass) {
        int hash = (pass.charAt(2) + pass.charAt(4) + pass.charAt(3) + pass.charAt(1)) * 65557 / 9973;
        return Integer.toString(hash);
    }

    public boolean validate(String str) {
        return str.length() >= 8;
    }

}
