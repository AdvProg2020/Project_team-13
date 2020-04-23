package Models;

import Models.Product.Product;
import Models.UserAccount.Seller;

import java.util.ArrayList;
import java.util.Date;

public class Offer {

    private double amount;
    private Seller seller;
    private ArrayList<Product> products;
    private double maxDiscountAmount;
    private Date startTime;
    private Date endTime;
    private OfferStatus offerStatus;

    public Offer(double amount, Seller seller, ArrayList<Product> products, double maxDiscountAmount, Date startTime, Date endTime, OfferStatus offerStatus) {
        this.amount = amount;
        this.seller = seller;
        this.products = products;
        this.maxDiscountAmount = maxDiscountAmount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.offerStatus = offerStatus;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public double getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(double maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public double getAmount() {
        return amount;
    }
}
