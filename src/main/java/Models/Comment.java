package Models;

import Models.Product.Product;
import Models.UserAccount.Customer;

public class Comment {
    private String productId;
    private CommentStatus commentStatus;
    private boolean didCustomerBuyProduct;
    private String title,content;

    public Comment(String productId, CommentStatus commentStatus, boolean didCustomerBuyProduct, String title, String content) {
        this.productId = productId;
        this.commentStatus = commentStatus;
        this.didCustomerBuyProduct = didCustomerBuyProduct;
        this.title = title;
        this.content = content;
    }

    public String getProductId() {
        return productId;
    }
    public String toString() {
        return
                "product= " + productId + "\n"
                +"Did Customer Buy Product? " + (didCustomerBuyProduct ? "yes":"no") + "\n"
                +"Title= " + title+"\n"
                + "Content= " +content;
    }
    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public boolean isDidCustomerBuyProduct() {
        return didCustomerBuyProduct;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
