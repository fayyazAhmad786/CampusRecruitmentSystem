package com.campusrecruitmentsystem.GetterSetter;

import android.graphics.Bitmap;

public class RegisterGetterSetter {

    public static String getFirst_name() {
        return first_name;
    }

    public static void setFirst_name(String first_name) {
        RegisterGetterSetter.first_name = first_name;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        RegisterGetterSetter.location = location;
    }

    public static String getCompany_name() {
        return company_name;
    }

    public static void setCompany_name(String company_name) {
        RegisterGetterSetter.company_name = company_name;
    }

    public static String getNo_of_employees() {
        return no_of_employees;
    }

    public static void setNo_of_employees(String no_of_employees) {
        RegisterGetterSetter.no_of_employees = no_of_employees;
    }

    public static String getEmail_address() {
        return email_address;
    }

    public static void setEmail_address(String email_address) {
        RegisterGetterSetter.email_address = email_address;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        RegisterGetterSetter.password = password;
    }

    public static String getSignUp_Type() {
        return SignUp_Type;
    }

    public static void setSignUp_Type(String signUp_Type) {
        SignUp_Type = signUp_Type;
    }

    private static String SignUp_Type="";
    private static String first_name="";
    private static String location ="";
    private static String company_name ="";
    private static String no_of_employees ="";
    private static String email_address="";
    private static String password="";
    private static String strImagePath = "";
    private static String strFileName = "";
    private static String strImageName = "";
    private static Bitmap bitMapCameraImage = null;

    public static Bitmap getBitMapCameraImage() {
        return bitMapCameraImage;
    }

    public static void setBitMapCameraImage(Bitmap bitMapCameraImage) {
        RegisterGetterSetter.bitMapCameraImage = bitMapCameraImage;
    }

    public static String getStrImageName() {
        return strImageName;
    }

    public static void setStrImageName(String strImageName) {
        RegisterGetterSetter.strImageName = strImageName;
    }

    public static String getStrFileName() {
        return strFileName;
    }

    public static void setStrFileName(String strFileName) {
        RegisterGetterSetter.strFileName = strFileName;
    }
    public static String getStrImagePath() {
        return strImagePath;
    }

    public static void setStrImagePath(String strImagePath) {
        RegisterGetterSetter.strImagePath = strImagePath;
    }

    public static String getProfession() {
        return profession;
    }

    public static void setProfession(String profession) {
        RegisterGetterSetter.profession = profession;
    }

    private static String profession="";

}
