package com.campusrecruitmentsystem.company.modules.CandidateFragment;


public class AppliedJobs {
    private String student_full_name;
    private String jobTitle;
    private String company_location;
    private String companyProfilePic;
    private String SalleryRange;
    private String test_assigned;

    public AppliedJobs(String student_full_name, String jobTitle, String company_location, String sallery_range, String companyProfilePic, String test_assigned) {
        this.student_full_name = student_full_name;
        this.jobTitle = jobTitle;
        this.company_location = company_location;
        this.SalleryRange = sallery_range;
        this.companyProfilePic = companyProfilePic;
        this.test_assigned = test_assigned;
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

    public String getCompanyProfilePic() {
        return companyProfilePic;
    }

    public void setCompanyProfilePic(String companyProfilePic) {
        this.companyProfilePic = companyProfilePic;
    }


    public String gettest_assigned() {
        return test_assigned;
    }

    public void settest_assigned(String test_assigned) {
        this.test_assigned = test_assigned;
    }
}


