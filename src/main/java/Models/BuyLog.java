package Models;

import Models.Product.Product;

import java.util.ArrayList;
import java.util.Date;

public class BuyLog extends Log{
    private ReceivingStatus receivingStatus;
    private double discountAmount;

    public BuyLog(String id, Date date, int price, String receiverUserName, String otherSideUserName, ArrayList<Product> allProducts) {
        super(id, date, price, receiverUserName, otherSideUserName, allProducts);
    }
}
