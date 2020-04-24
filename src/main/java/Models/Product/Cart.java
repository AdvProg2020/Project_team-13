package Models.Product;

import Models.DiscountCode;
import Models.UserAccount.Customer;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    private Customer customer;
    private HashMap<String, Integer> countOfEachProduct;
    private DiscountCode discountCode;
    private ArrayList<Product> allproduct;
    private double totalPrice = 0;
    private String receivingInformation;

    public void addProduct(Product product) {
        countOfEachProduct.put(product, 1);
    }

    public void changeCountOfProduct(String productID, int count) {
        countOfEachProduct.replace(productID, countOfEachProduct.get(productID) + count);
    }

    public double getTotalPrice() {
        totalPrice = 0;
        for (String productID : countOfEachProduct.keySet()) {
            Product product = getProductByID(productID);
            totalPrice += countOfEachProduct.get(productID) * product.getProductCost();
        }
        return totalPrice;
    }

    public Product getProductByID(String productID) {
        for (Product product : allproduct) {
            if (product.getProductId().equals(productID)) {
                return product;
            }
        }
    }
}
