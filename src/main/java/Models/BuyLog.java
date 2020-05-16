package Models;

import Models.Product.Product;

import java.util.ArrayList;
import java.util.Date;

public class BuyLog extends Log{
    double price;
    public BuyLog(String id,double price, Date date,  String receiverUserName, String otherSideUserName, ArrayList<Product> allProducts, ReceivingStatus receivingStatus, double reduceCostForOffs) {
        super(id, date, receiverUserName, otherSideUserName, allProducts, receivingStatus, reduceCostForOffs);
        this.price=price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
