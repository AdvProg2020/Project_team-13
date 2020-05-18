package Models;

import Models.Product.Product;
import Models.UserAccount.Customer;

public class Score {
    private String customerID;
    private String  productID;
    private int rate;

    public Score(String customerID, String productID, int rate) {
        this.customerID = customerID;
        this.productID = productID;
        this.rate = rate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getProductID() {
        return productID;
    }

    public int getRate() {
        return rate;
    }
}
