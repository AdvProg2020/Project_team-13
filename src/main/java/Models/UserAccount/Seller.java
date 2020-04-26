package Models.UserAccount;

import Models.Log;
import Models.Offer;
import Models.Product.Product;
import Models.Request;

import java.util.ArrayList;

public class Seller extends UserAccount {
    private String companyName;
    private boolean isAccepted;
    private ArrayList<Product> allProducts;
    private ArrayList<Offer> allOffer;
    private ArrayList<Request> allRequests;

    public Seller(String username, String password, String firstName, String lastName, String email, String phoneNumber, double credit, String companyName, boolean isAccepted) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.companyName = companyName;
        this.isAccepted = isAccepted;
        this.type="@Seller";
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public ArrayList<Offer> getAllOffer() {
        return allOffer;
    }

    public void setAllOffer(ArrayList<Offer> allOffer) {
        this.allOffer = allOffer;
    }

    public ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public void setAllRequests(ArrayList<Request> allRequests) {
        this.allRequests = allRequests;
    }

    @Override
    public String viewPersonalInfo() {
        String personalInfo = "";
        personalInfo += this.username + "\n";
        personalInfo += this.firstName + "\n";
        personalInfo += this.lastName + "\n";
        personalInfo += this.email + "\n";
        personalInfo += this.phoneNumber + "\n";
        personalInfo += this.companyName + "\n";
        return personalInfo;
    }

    public String viewSalesHistory() {
        String history = "";
        for (Log sellLog : this.historyOfTransaction) {
            history += sellLog.getId() + "\\*\\";
            history += sellLog.getReceiverUserName() + "\\*\\";
            history += sellLog.getPrice() + "\\*\\";
            history += '\n';
        }
        return history;
    }

    public String viewAllProducts() {
        String products = "";
        for (Product product : this.allProducts) {
            products += product.getProductId() + "\\*\\";
            products += product.getProductName() + "\\*\\";
            products += product.getProductCost() + "\\*\\";
            products += '\n';
        }
        return products;
    }

    public String viewProduct(String productID) {
        String productInfo = "";
        Product product = getProductByID(productID);
        productInfo += product.getProductId() + "\\*\\";
        productInfo += product.getProductName() + "\\*\\";
        productInfo += product.getProductCompany() + "\\*\\";
        productInfo += product.getProductCost() + "\\*\\";
        productInfo += product.getProductStatus() + "\\*\\";
        productInfo += product.getProductsCategory() + "\\*\\";
        productInfo += product.getNumberOfAvailableProducts() + "\\*\\";
        productInfo += product.getDescription() + "\\*\\";
        productInfo += product.getAverageScore() + "\\*\\";
        productInfo += product.getCostAfterOff() + "\\*\\";
        productInfo += product.getComments() + "\\*\\";
        productInfo += product.getFeaturesOfCategoryThatHas() + "\\*\\";
        return productInfo;

    }

    public Product getProductByID(String productID) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productID))
                return product;
        }
        return null;
    }
}
