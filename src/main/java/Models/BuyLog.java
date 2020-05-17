package Models;

import Models.Product.Product;

import java.util.ArrayList;
import java.util.Date;

public class BuyLog extends Log{
    public BuyLog(String id,double price, Date date,  String receiverUserName, String otherSideUserName, ArrayList<Product> allProducts, ReceivingStatus receivingStatus, double reduceCostForOffs) {
        super(id, date, receiverUserName, otherSideUserName, allProducts, receivingStatus, reduceCostForOffs);
        this.price=price;
    }


}
