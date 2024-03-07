package com.campusrecruitmentsystem.student.modules;

public class Job {
    private String companyLocation;
    private String jobTitle;
    private String companyName;
    private String companyProfilePic;
    private String SalleryRange;

    public String get_id_pk() {
        return _id_pk;
    }

    public void set_id_pk(String _id_pk) {
        this._id_pk = _id_pk;
    }

    private String _id_pk;

    public Job(String companyLocation, String jobTitle, String companyName,String sallery_range, String companyProfilePic,String _id_pk) {
        this.companyLocation = companyLocation;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.SalleryRange = sallery_range;
        this.companyProfilePic = companyProfilePic;
        this._id_pk = _id_pk;
    }

    public String getSalleryRange() {
        return SalleryRange;
    }

    public void setSalleryRange(String salleryRange) {
        this.SalleryRange = salleryRange;
    }
    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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
}

