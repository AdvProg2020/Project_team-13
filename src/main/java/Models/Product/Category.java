package Models.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    private String name;
    private HashMap<String, ArrayList<String>> features;
    private ArrayList<Product> allProducts;

    public Category(String name, HashMap<String, ArrayList<String>> features) {
        this.name = name;
        this.features = features;
    }

    public void addProduct(Product product) {
        if (allProducts != null) {
            allProducts.add(product);
        } else {
            allProducts = new ArrayList<>();
            allProducts.add(product);
        }
    }

    public HashMap<String, ArrayList<String>> getFeatures() {
        return features;
    }

    public void setFeatures(HashMap<String, ArrayList<String>> features) {
        this.features = features;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProducts(Product product) {
        this.allProducts.add(product);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void editProduct(Product product) {
        if (allProducts != null) {
            for (Product product1 : this.allProducts) {
                if (product1.getProductId().equals(product.getProductId())) {
                    allProducts.set(allProducts.indexOf(product1), product);
                }
            }
        }
    }
}
