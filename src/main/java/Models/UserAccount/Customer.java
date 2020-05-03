package Models.UserAccount;

import Models.DiscountCode;
import Models.Product.Cart;
import Models.Request;

import java.util.ArrayList;

public class Customer extends UserAccount{
    private Cart cart;
    private ArrayList<Request> allRequests;

    public Customer(String username, String password, String firstName, String lastName, String email, String phoneNumber, double credit) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.cart = new Cart(username);
        this.type="@Customer";
    }

    public Cart getCart() {
        return cart;
    }

    public ArrayList<Request> getAllRequests() {
        return allRequests;
    }


    @Override
    public String viewPersonalInfo() {
        String personalInfo = "";
        personalInfo += this.username + "\n";
        personalInfo += this.firstName + "\n";
        personalInfo += this.lastName + "\n";
        personalInfo += this.email + "\n";
        personalInfo += this.phoneNumber + "\n";
        personalInfo += this.credit ;
        return personalInfo;
    }
}
