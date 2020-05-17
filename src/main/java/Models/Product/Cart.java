package Models.Product;

import Controller.Client.ClientController;
import Models.DiscountCode;
import Models.UserAccount.Customer;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    private String customerID;
    private HashMap<String, Integer> countOfEachProduct;
    private DiscountCode discountCode;
    private ArrayList<Product> allproduct;
    private double totalPrice = 0;
    private String receivingInformation;

    public Cart() {
        this.countOfEachProduct = new HashMap<>();
        this.discountCode =null;
        this.allproduct =new ArrayList<>();
        this.totalPrice = 0.0;
        this.receivingInformation = null;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void addProduct(Product product) {
        if(findProductWithID(product.getProductId())==null) {
            countOfEachProduct.put(product.getProductId(), 1);
            allproduct.add(product);
            ClientController.getInstance().getCurrentMenu().showMessage("product successfully added to cart");
        }else{
            ClientController.getInstance().getCurrentMenu().printError("This product has already been added to the cart");
        }
    }

    public String getCustomerID() {
        return customerID;
    }

    public HashMap<String, Integer> getCountOfEachProduct() {
        return countOfEachProduct;
    }

    public DiscountCode getDiscountCode() {
        return discountCode;
    }

    public ArrayList<Product> getAllproduct() {
        return allproduct;
    }

    public String getReceivingInformation() {
        return receivingInformation;
    }

    public void changeCountOfProduct(String productID, int count) {
        if(findProductWithID(productID).getNumberOfAvailableProducts()-count>=0) {
            if (countOfEachProduct.get(productID) + count > 0) {
                countOfEachProduct.replace(productID, countOfEachProduct.get(productID) + count);
                ClientController.getInstance().getCurrentMenu().showMessage("The new number of this product is " + countOfEachProduct.get(productID));
            } else if (countOfEachProduct.get(productID) + count == 0) {
                countOfEachProduct.remove(productID);
                allproduct.remove(findProductWithID(productID));
                ClientController.getInstance().getCurrentMenu().showMessage("this product removed from your cart");
            } else if (countOfEachProduct.get(productID) + count < 0) {
                ClientController.getInstance().getCurrentMenu().printError("You only have " + countOfEachProduct.get(productID) + " of this products in your cart");
            }
        }else{
            ClientController.getInstance().getCurrentMenu().printError("there are no more than "+findProductWithID(productID).getNumberOfAvailableProducts()+" of this product in the store");
        }
    }

    public double getTotalPrice() {
        totalPrice = 0;
        for (String productID : countOfEachProduct.keySet()) {
            Product product = getProductByID(productID);
            totalPrice += countOfEachProduct.get(productID) * product.getCostAfterOff();
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
    public void showProducts(){
        String show="";
        for (Product product : allproduct) {
            show += product.getProductId()+" "+ product.getProductName()+" "+countOfEachProduct.get(product.getProductId())+" "+product.getProductCost()+"\n";
        }
        ClientController.getInstance().getCurrentMenu().showMessage(show);
    }

    public void setReceivingInformation(String receivingInformation) {
        this.receivingInformation = receivingInformation;
    }

    public Product findProductWithID(String productID) {
        for (Product product : allproduct) {
            if (product.getProductId().equals(productID)) {
                return product;
            }
        }
        return null;
    }
}
