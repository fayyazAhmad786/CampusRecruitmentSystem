package com.campusrecruitmentsystem.student.presentation.fragment_modules.companies_module;


public class FeaturedCompanies {
    private String companyName;
    private String companyProfilePic;
    private String companyLocation;
    private String company_email;
    private String _id_pk;



    public FeaturedCompanies(String company_email,String companyLocation,String companyName,String companyProfilePic,String _id_pk) {
        this.companyName = companyName;
        this.companyProfilePic = companyProfilePic;
        this.company_email = company_email;
        this.companyLocation = companyLocation;
        this._id_pk = _id_pk;

    }



    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getcompanyLocation() {
        return companyLocation;
    }

    public void setcompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getCompanyProfilePic() {
        return companyProfilePic;
    }

    public void setCompanyProfilePic(String companyProfilePic) {
        this.companyProfilePic = companyProfilePic;
    }
    public String get_id_pk() {
        return _id_pk;
    }

    public void set_id_pk(String _id_pk) {
        this._id_pk = _id_pk;
    }
}


