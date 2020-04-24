package Models;

import Models.Product.Product;
import Models.UserAccount.Customer;

public class Comment {
    private Customer customer;
    private Product product;
    private CommentStatus commentStatus;
    private boolean didCustomerBuyProduct;
    private Request confirmRequest;
    private String text;

    public Comment(Customer customer, Product product, CommentStatus commentStatus, String text) {
        this.customer = customer;
        this.product = product;
        this.commentStatus = commentStatus;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment" +
                "customer=" + customer +
                ", product=" + product +
                ", commentStatus=" + commentStatus +
                ", didCustomerBuyProduct=" + didCustomerBuyProduct +
                ", confirmRequest=" + confirmRequest +
                ", text='" + text + '\'' +
                '}';
    }
}
