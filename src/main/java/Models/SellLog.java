package Models;

import Models.Product.Product;

import java.util.ArrayList;
import java.util.Date;

public class SellLog extends Log {
    public SellLog(String id, Date date, int price, String receiverUserName, String otherSideUserName, ArrayList<Product> allProducts, ReceivingStatus receivingStatus, double reduceCostForOffs) {
        super(id, date, price, receiverUserName, otherSideUserName, allProducts, receivingStatus, reduceCostForOffs);
    }
}
