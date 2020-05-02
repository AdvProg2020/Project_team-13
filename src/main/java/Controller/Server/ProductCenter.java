package Controller.Server;

import Models.Product.Product;
import Models.Request;

import java.io.IOException;
import java.util.ArrayList;

public class ProductCenter {

    private String lastProductId;
    private ArrayList<Product> products;
    private static ProductCenter productCenter;

    private ProductCenter() {

    }

    public static ProductCenter getInstance() {
        if (productCenter == null) {
            productCenter = new ProductCenter();
        }
        return productCenter;
    }

    public String getLastProductId() {
        DataBase.getIncstance().setLastProductIdFromDataBase();
        return lastProductId;
    }

    public void setLastProductId(String lastProductId) {
        this.lastProductId = lastProductId;
    }

    public String getProductIdForCreateInProduct() {
        DataBase.getIncstance().setLastProductIdFromDataBase();
        this.lastProductId = "@p" + (Integer.parseInt(lastProductId.substring(2, lastProductId.length())) + 1);
        DataBase.getIncstance().replaceProductId(lastProductId);
        return this.lastProductId;
    }

}
