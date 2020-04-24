package Models.Product;

import Models.DiscountCode;
import Models.UserAccount.Customer;

import java.util.HashMap;

public class Cart {
    private Customer customer;
    private HashMap<Product,Integer> countOfEachProduct;
    private DiscountCode discountCode;
    private double totalPrice=0;
    private String receivingInformation;

    public void addProduct(Product product){
        countOfEachProduct.put(product,1);
    }
    public void changeCountOfProduct(Product product,int count){
        countOfEachProduct.replace(countOfEachProduct.get(product),count);
    }
    public double getTotalPrice(){

    }
    public Product getProductByID(String productID) {
       
    }
}
