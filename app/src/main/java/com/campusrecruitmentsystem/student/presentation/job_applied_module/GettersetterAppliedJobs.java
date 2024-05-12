package com.campusrecruitmentsystem.student.presentation.job_applied_module;


public class GettersetterAppliedJobs {
    private String txtlocation;
    private String txtdeadline;
    private String companyName;
    private String companyProfilePic;
    private String txtJobTitle;
    private String ApplicationDeadline;
    private int _id_pk;



    public GettersetterAppliedJobs(String companyLocation, String jobTitle, String companyName, String sallery_range, String companyProfilePic, String application_deadline, int _id_pk) {
        this.txtlocation = companyLocation;
        this.txtdeadline = jobTitle;
        this.companyName = companyName;
        this.txtJobTitle = sallery_range;
        this.companyProfilePic = companyProfilePic;
        this.ApplicationDeadline = application_deadline;
        this._id_pk = _id_pk;
    }

    public String getTxtJobTitle() {
        return txtJobTitle;
    }

    public void setTxtJobTitle(String txtJobTitle) {
        this.txtJobTitle = txtJobTitle;
    }
    public String getTxtlocation() {
        return txtlocation;
    }

    public void setTxtlocation(String txtlocation) {
        this.txtlocation = txtlocation;
    }

    public String getTxtdeadline() {
        return txtdeadline;
    }

    public void setTxtdeadline(String txtdeadline) {
        this.txtdeadline = txtdeadline;
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
    public String getApplicationDeadline() {
        return ApplicationDeadline;
    }

    public void setApplicationDeadline(String ApplicationDeadline) {
        this.ApplicationDeadline = ApplicationDeadline;
    }


    public int get_id_pk() {
        return _id_pk;
    }

    public void set_id_pk(int _id_pk) {
        this._id_pk = _id_pk;
    }
}

