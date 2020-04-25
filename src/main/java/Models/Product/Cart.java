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

    public Cart(Customer customer) {
        this.customer = customer;
        this.countOfEachProduct = new HashMap<>();
        this.discountCode =null;
        this.allproduct =new ArrayList<>();
        this.totalPrice = 0.0;
        this.receivingInformation = null;
    }

    public void addProduct(Product product) {
        countOfEachProduct.put(product.getProductId(), 1);
        allproduct.add(product);
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
        return null;
    }
}
