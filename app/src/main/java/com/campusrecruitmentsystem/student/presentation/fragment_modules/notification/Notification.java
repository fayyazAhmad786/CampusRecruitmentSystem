package com.campusrecruitmentsystem.student.presentation.fragment_modules.notification;

public class Notification {

    private String companyName;
    private String companyProfilePic;
    private String company_email;
    private String notification_text;
    private String current_date_time;
    private String job_id;
    private String notification_status;

    public int get_id_pk() {
        return _id_pk;
    }

    public void set_id_pk(int _id_pk) {
        this._id_pk = _id_pk;
    }

    private int _id_pk;

    public Notification( String company_email,String companyName, String companyProfilePic,String notification_text,
                         String current_date_time, String job_id, String notification_status, int _id_pk) {

        this.companyName = companyName;
        this.company_email = company_email;
        this.companyProfilePic = companyProfilePic;
        this.notification_text = notification_text;
        this.current_date_time = current_date_time;
        this.job_id = job_id;
        this.notification_status = notification_status;
        this._id_pk = _id_pk;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyProfilePic() {
        return companyProfilePic;
    }

    public void setCompanyProfilePic(String companyProfilePic) {
        this.companyProfilePic = companyProfilePic;
    }

    public String getcompany_email() {
        return company_email;
    }

    public void setcompany_email(String company_email) {
        this.company_email = company_email;
    }

    public String getnotification_text() {
        return notification_text;
    }

    public void setnotification_text(String notification_text) {
        this.notification_text = notification_text;
    }

    public String getcurrent_date_time() {
        return current_date_time;
    }

    public void setcurrent_date_time(String current_date_time) {
        this.current_date_time = current_date_time;
    }
    public String getjob_id() {
        return job_id;
    }

    public void setjob_id(String job_id) {
        this.job_id = job_id;
    }
    public String getnotification_status() {
        return notification_status;
    }

    public void setnotification_status(String notification_status) {
        this.notification_status = notification_status;
    }
}

