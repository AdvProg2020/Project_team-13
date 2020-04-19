package Models;

import Models.Product.Product;
import Models.UserAccount.Customer;

public class Score {
    private Customer customer;
    private Product product;
    private int rate;

    public Score(Customer customer, Product product, int rate) {
        this.customer = customer;
        this.product = product;
        this.rate = rate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public int getRate() {
        return rate;
    }
}
