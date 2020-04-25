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
        this.cart = new Cart(this);
        this.type="@Customer";
    }

    public Cart getCart() {
        return cart;
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }

    public ArrayList<Request> getAllRequests() {
        return allRequests;
    }


    @Override
    public String viewPersonalInfo() {
        return null;
    }
}
