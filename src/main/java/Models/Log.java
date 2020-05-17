package Models;

import Models.Product.Product;

import java.util.ArrayList;
import java.util.Date;

public class Log {
    private String id;
    private Date date;
    protected double price;
    private String receiverUserName;
    private String otherSideUserName;
    private ArrayList<Product> allProducts;
    private ReceivingStatus receivingStatus;
    private double reduceCostForOffs;

    public Log(String id, Date date, String receiverUserName, String otherSideUserName, ArrayList<Product> allProducts, ReceivingStatus receivingStatus, double reduceCostForOffs) {
        this.id = id;
        this.date = date;
        this.receiverUserName = receiverUserName;
        this.otherSideUserName = otherSideUserName;
        this.allProducts = allProducts;
        this.receivingStatus = receivingStatus;
        this.reduceCostForOffs = reduceCostForOffs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReceiverUserName() {
        return receiverUserName;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setReceiverUserName(String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }

    public String getOtherSideUserName() {
        return otherSideUserName;
    }

    public void setOtherSideUserName(String otherSideUserName) {
        this.otherSideUserName = otherSideUserName;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public ReceivingStatus getReceivingStatus() {
        return receivingStatus;
    }

    public double getReduceCostForOffs() {
        return reduceCostForOffs;
    }
}
