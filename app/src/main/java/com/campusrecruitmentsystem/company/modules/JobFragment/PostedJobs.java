package com.campusrecruitmentsystem.company.modules.JobFragment;


public class PostedJobs {
    private String company_name;
    private String jobTitle;
    private String company_location;
    private String companyProfilePic;
    private String applicationDeadline;
    private String SalleryRange;

    public PostedJobs(String company_name, String jobTitle, String company_location, String sallery_range, String companyProfilePic, String applicationDeadline) {
        this.company_name = company_name;
        this.jobTitle = jobTitle;
        this.company_location = company_location;
        this.SalleryRange = sallery_range;
        this.companyProfilePic = companyProfilePic;
        this.applicationDeadline = applicationDeadline;
    }

    public String getSalleryRange() {
        return SalleryRange;
    }

    public void setSalleryRange(String salleryRange) {
        this.SalleryRange = salleryRange;
    }
    public String getcompany_name() {
        return company_name;
    }

    public void setcompany_name(String company_name) {
        this.company_name = company_name;
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

    public String getCompanyProfilePic() {
        return companyProfilePic;
    }

    public void setCompanyProfilePic(String companyProfilePic) {
        this.companyProfilePic = companyProfilePic;
    }

    public String getapplicationDeadline() {
        return applicationDeadline;
    }

    public void setapplicationDeadline(String applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }
}


