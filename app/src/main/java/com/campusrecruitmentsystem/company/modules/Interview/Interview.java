package com.campusrecruitmentsystem.company.modules.Interview;


public class Interview {
    private String student_full_name;
    private String jobTitle;
    private String company_location;
    private String studentProfilePic;
    private String SalleryRange;
    private String test_assigned;
    private String short_listed;
    private String hired;
    private String user_applied;
    private String joining_date;
    private Integer _id_pk;

    public Interview(String student_full_name, String jobTitle, String company_location, String sallery_range,  String studentProfilePic, String test_assigned, String short_listed, String hired,String user_applied,String joining_date, Integer _id_pk) {
        this.student_full_name = student_full_name;
        this.jobTitle = jobTitle;
        this.company_location = company_location;
        this.SalleryRange = sallery_range;
//        this.companyProfilePic = companyProfilePic;
        this.studentProfilePic = studentProfilePic;
        this.test_assigned = test_assigned;
        this.short_listed = short_listed;
        this.hired = hired;
        this.user_applied = user_applied;
        this.joining_date = joining_date;
        this._id_pk = _id_pk;
    }

    public String getSalleryRange() {
        return SalleryRange;
    }

    public void setSalleryRange(String salleryRange) {
        this.SalleryRange = salleryRange;
    }
    public String getStudent_full_name() {
        return student_full_name;
    }

    public void setStudent_full_name(String student_full_name) {
        this.student_full_name = student_full_name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany_location() {
        return company_location;
    }

    public void setCompany_location(String company_location) {
        this.company_location = company_location;
    }

 public String getstudentProfilePic() {
        return studentProfilePic;
    }

    public void setstudentProfilePic(String studentProfilePic) {
        this.studentProfilePic = studentProfilePic;
    }


    public String gettest_assigned() {
        return test_assigned;
    }

    public void settest_assigned(String test_assigned) {
        this.test_assigned = test_assigned;
    }

    public String getshort_listed() {
        return short_listed;
    }

    public void setshort_listed(String short_listed) {
        this.short_listed = short_listed;
    }
    public String gethired() {
        return hired;
    }

    public void sethired(String hired) {
        this.hired = hired;
    }


    public String getuser_applied() {
        return user_applied;
    }

    public void setuser_applied(String user_applied) {
        this.user_applied = user_applied;
    }

    public String getjoining_date() {
        return joining_date;
    }

    public void setjoining_date(String joining_date) {
        this.joining_date = joining_date;
    }

    public Integer get_id_pk() {
        return _id_pk;
    }

    public void set_id_pk(Integer _id_pk) {
        this._id_pk = _id_pk;
    }
}


