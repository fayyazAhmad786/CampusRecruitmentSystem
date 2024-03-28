package com.campusrecruitmentsystem.student.presentation.fragment_modules.companies_module;

public class Companies {

    private String companyName;
    private String companyProfilePic;
    private String company_email;

    public String get_id_pk() {
        return _id_pk;
    }

    public void set_id_pk(String _id_pk) {
        this._id_pk = _id_pk;
    }

    private String _id_pk;

    public Companies( String companyName, String companyProfilePic,String company_email, String _id_pk) {

        this.companyName = companyName;
        this.company_email = company_email;
        this.companyProfilePic = companyProfilePic;
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
}

