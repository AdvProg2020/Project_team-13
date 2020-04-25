package Models;

import Models.Product.Product;

import java.util.ArrayList;
import java.util.Date;

public class Log {
    private String id;
    private Date date;
    private int price;
    private String receiverUserName;
    private String otherSideUserName;
    private ArrayList<Product> allProducts;

    public Log(String id, Date date, int price, String receiverUserName, String otherSideUserName, ArrayList<Product> allProducts) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.receiverUserName = receiverUserName;
        this.otherSideUserName = otherSideUserName;
        this.allProducts = allProducts;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getReceiverUserName() {
        return receiverUserName;
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
}
