package smartfixsa.com.smartfix.models;

public class CompanyMobileModel {
    private int img_company;
    private String companymobile_name;

    public CompanyMobileModel(int img_company, String companymobile_name) {
        this.img_company = img_company;
        this.companymobile_name = companymobile_name;
    }

    public int getImg_company() {
        return img_company;
    }

    public String getCompanymobile_name() {
        return companymobile_name;
    }

    public void setImg_company(int img_company) {
        this.img_company = img_company;
    }

    public void setCompanymobile_name(String companymobile_name) {
        this.companymobile_name = companymobile_name;
    }
}
