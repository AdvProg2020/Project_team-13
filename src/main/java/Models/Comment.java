package Models;

import Models.Product.Product;
import Models.UserAccount.Customer;

public class Comment {
    private String customerId;
    private String  productId;
    private CommentStatus commentStatus;
    private boolean didCustomerBuyProduct;
    private Request confirmRequest;
    private String text;

    public Comment(String customerId, String productId, CommentStatus commentStatus, String text) {
        this.customerId = customerId;
        this.productId = productId;
        this.commentStatus = commentStatus;
        this.text = text;
    }


}
