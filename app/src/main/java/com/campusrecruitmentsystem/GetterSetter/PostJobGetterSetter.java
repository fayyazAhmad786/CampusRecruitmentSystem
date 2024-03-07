package com.campusrecruitmentsystem.GetterSetter;

public class PostJobGetterSetter {


    private static String questions="";

    private static String jobTitle="";
    private static String jobDescription="";
    private static String jobCompanyType ="";
    private static String jobLocation="";
    private static String jobSallery="";
    private static String jobDeadline="";

    public static String getCompanyEmail() {
        return companyEmail;
    }

    public static void setCompanyEmail(String companyEmail) {
        PostJobGetterSetter.companyEmail = companyEmail;
    }

    public static String getCompanyname() {
        return companyname;
    }

    public static void setCompanyname(String companyname) {
        PostJobGetterSetter.companyname = companyname;
    }

    private static String companyEmail="";
    private static String companyname="";

    public static String getProfile_pic_company() {
        return profile_pic_company;
    }

    public static void setProfile_pic_company(String profile_pic_company) {
        PostJobGetterSetter.profile_pic_company = profile_pic_company;
    }

    private static String profile_pic_company="";

    public static String getJob_id() {
        return job_id;
    }

    public static void setJob_id(String job_id) {
        PostJobGetterSetter.job_id = job_id;
    }

    private static String job_id="";
    public static String getQuestions() {
        return questions;
    }

    public static void setQuestions(String questions) {
        PostJobGetterSetter.questions = questions;
    }

    public static String getJobTitle() {
        return jobTitle;
    }

    public static void setJobTitle(String jobTitle) {
        PostJobGetterSetter.jobTitle = jobTitle;
    }

    public static String getJobDescription() {
        return jobDescription;
    }

    public static void setJobDescription(String jobDescription) {
        PostJobGetterSetter.jobDescription = jobDescription;
    }

    public static String getJobCompanyType() {
        return jobCompanyType;
    }

    public static void setJobCompanyType(String jobCompanyType) {
        PostJobGetterSetter.jobCompanyType = jobCompanyType;
    }

    public static String getJobLocation() {
        return jobLocation;
    }

    public static void setJobLocation(String jobLocation) {
        PostJobGetterSetter.jobLocation = jobLocation;
    }

    public static String getJobSallery() {
        return jobSallery;
    }

    public static void setJobSallery(String jobSallery) {
        PostJobGetterSetter.jobSallery = jobSallery;
    }

    public static String getJobDeadline() {
        return jobDeadline;
    }

    public static void setJobDeadline(String jobDeadline) {
        PostJobGetterSetter.jobDeadline = jobDeadline;
    }


}
