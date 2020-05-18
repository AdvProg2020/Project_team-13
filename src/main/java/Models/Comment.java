package Models;

import Models.Product.Product;
import Models.UserAccount.Customer;

public class Comment {
    private Customer customer;
    private String productId;
    private CommentStatus commentStatus;
    private boolean didCustomerBuyProduct;
    private Request confirmRequest;
    private String text;

    public Comment(Customer customer, String productId, CommentStatus commentStatus, String text) {
        this.customer = customer;
        this.productId = productId;
        this.commentStatus = commentStatus;
        this.text = text;
    }

    @Override
    public String toString() {
        return "customer=" + customer + "\n"
                +"product=" + productId + "\n"
                +"commentStatus=" + commentStatus + "\n"
                +"didCustomerBuyProduct=" + didCustomerBuyProduct + "\n"
                +"confirmRequest=" + confirmRequest + "\n"
                +"text=" + text;
    }
}
