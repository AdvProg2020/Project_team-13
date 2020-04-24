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
        countOfEachProduct.replace(product,countOfEachProduct.get(product)+count);
    }
    public double getTotalPrice(){
        totalPrice=0;
        for (Product product : countOfEachProduct.keySet()) {
            totalPrice+=countOfEachProduct.get(product)*product.getProductCost();
        }
        return totalPrice;
    }
    public Product getProductByID(String productID) {
        for (Product product : countOfEachProduct.keySet()) {
            if(product.getProductId().equals(productID)){
                return product;
            }
        }
    }
}
