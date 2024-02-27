package com.campusrecruitmentsystem.GetterSetter;

public class AppliedJobGetterSetter {


    private static String questions="";

    private static String jobTitle="";
    private static String jobDescription="";
    private static String jobCompanyType ="";
    private static String jobLocation="";
    private static String jobSallery="";
    private static String jobDeadline="";
    private static String userResume="";
    private static String JobTest="";
    private static String UserApplied="";
    private static String StudentFullName="";
    private static String StudentProfilePic="";

    public static String getStudentFullName() {
        return StudentFullName;
    }

    public static void setStudentFullName(String studentFullName) {
        StudentFullName = studentFullName;
    }

    public static String getStudentProfilePic() {
        return StudentProfilePic;
    }

    public static void setStudentProfilePic(String studentProfilePic) {
        StudentProfilePic = studentProfilePic;
    }




    public static String getUserApplied() {
        return UserApplied;
    }

    public static void setUserApplied(String userApplied) {
        UserApplied = userApplied;
    }



    public static String getJobTest() {
        return JobTest;
    }

    public static void setJobTest(String jobTest) {
        JobTest = jobTest;
    }



    public static String getUserResume() {
        return userResume;
    }

    public static void setUserResume(String userResume) {
        AppliedJobGetterSetter.userResume = userResume;
    }


    public static String getCompanyEmail() {
        return companyEmail;
    }

    public static void setCompanyEmail(String companyEmail) {
        AppliedJobGetterSetter.companyEmail = companyEmail;
    }

    public static String getCompanyname() {
        return companyname;
    }

    public static void setCompanyname(String companyname) {
        AppliedJobGetterSetter.companyname = companyname;
    }

    private static String companyEmail="";
    private static String companyname="";

    public static String getProfile_pic_company() {
        return profile_pic_company;
    }

    public static void setProfile_pic_company(String profile_pic_company) {
        AppliedJobGetterSetter.profile_pic_company = profile_pic_company;
    }

    private static String profile_pic_company="";
    public static String getQuestions() {
        return questions;
    }

    public static void setQuestions(String questions) {
        AppliedJobGetterSetter.questions = questions;
    }

    public static String getJobTitle() {
        return jobTitle;
    }

    public static void setJobTitle(String jobTitle) {
        AppliedJobGetterSetter.jobTitle = jobTitle;
    }

    public static String getJobDescription() {
        return jobDescription;
    }

    public static void setJobDescription(String jobDescription) {
        AppliedJobGetterSetter.jobDescription = jobDescription;
    }

    public static String getJobCompanyType() {
        return jobCompanyType;
    }

    public static void setJobCompanyType(String jobCompanyType) {
        AppliedJobGetterSetter.jobCompanyType = jobCompanyType;
    }

    public static String getJobLocation() {
        return jobLocation;
    }

    public static void setJobLocation(String jobLocation) {
        AppliedJobGetterSetter.jobLocation = jobLocation;
    }

    public static String getJobSallery() {
        return jobSallery;
    }

    public static void setJobSallery(String jobSallery) {
        AppliedJobGetterSetter.jobSallery = jobSallery;
    }

    public static String getJobDeadline() {
        return jobDeadline;
    }

    public static void setJobDeadline(String jobDeadline) {
        AppliedJobGetterSetter.jobDeadline = jobDeadline;
    }


}
