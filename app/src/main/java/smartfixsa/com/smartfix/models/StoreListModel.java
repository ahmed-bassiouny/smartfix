package smartfixsa.com.smartfix.models;

public class StoreListModel {
    String product;
    String company;
    String center;

    public StoreListModel(String product, String company, String center) {
        this.product = product;
        this.company = company;
        this.center = center;
    }

    public StoreListModel() {
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
