package Models.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    private String name;
    private HashMap<String ,ArrayList<String>> features;
    private ArrayList<Product> allProducts;

    public Category(String name, HashMap<String, ArrayList<String>> features) {
        this.name = name;
        this.features = features;
    }

    public void addProduct(Product product) {
        allProducts.add(product);
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
}
