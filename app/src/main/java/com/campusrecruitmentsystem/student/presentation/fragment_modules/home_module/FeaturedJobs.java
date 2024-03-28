package com.campusrecruitmentsystem.student.presentation.fragment_modules.home_module;


public class FeaturedJobs {
    private String companyLocation;
    private String jobTitle;
    private String companyName;
    private String companyProfilePic;
    private String SalleryRange;

    public FeaturedJobs(String companyLocation, String jobTitle, String companyName,String sallery_range, String companyProfilePic) {
        this.companyLocation = companyLocation;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.SalleryRange = sallery_range;
        this.companyProfilePic = companyProfilePic;
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


