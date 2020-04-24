package Models.Product;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<String> featureName;
    private ArrayList<Product> allProducts;

    public Category(String name, ArrayList<String> featureName) {
        this.name = name;
        this.featureName = featureName;
    }


    public void addProduct(Product product){
        allProducts.add(product);
    }

    public ArrayList<String> getFeatureName() {
        return featureName;
    }
    public void addFeature(String featureName){
        this.featureName.add(featureName);
    }
    public void removeFeature(String featureName){
        this.featureName.remove(featureName);
    }
    public boolean isThisFeatureExists(String featureName){
        return this.featureName.contains(featureName);
    }
    public  void editFeatureName(String featureName){

    }
}
