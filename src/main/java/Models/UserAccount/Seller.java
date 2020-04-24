package Models.UserAccount;

import Models.Offer;
import Models.Product.Product;
import Models.Request;

import java.util.ArrayList;

public class Seller extends UserAccount{
    private String companyName;
    private boolean isAccepted;
    private ArrayList<Product> allProducts;
    private ArrayList<Offer> allOffer;
    private ArrayList<Request> allRequests;

    public Seller(String username, String password, String firstName, String lastName, String email, String phoneNumber, int credit, String companyName, boolean isAccepted) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.companyName = companyName;
        this.isAccepted = isAccepted;
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
        personalInfo += this.username + "\\*\\";
        personalInfo += this.firstName + "\\*\\";
        personalInfo += this.lastName + "\\*\\";
        personalInfo += this.email + "\\*\\";
        personalInfo += this.phoneNumber + "\\*\\";
        personalInfo += this.companyName + "\\*\\";
        return personalInfo;
    }
}
