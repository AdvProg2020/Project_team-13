package Controller.Server;

import Models.Product.Category;
import Models.Product.Product;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.util.ArrayList;

public class CategoryCenter {
    private static CategoryCenter categoryCenter;
    private ArrayList<Category> allCategories = new ArrayList<>();

    private CategoryCenter() {
    }


    public static CategoryCenter getIncstance() {
        if(categoryCenter == null){
            synchronized (CategoryCenter.class) {
                if(categoryCenter == null){
                    categoryCenter = new CategoryCenter();
                }
            }
        }
        return categoryCenter;
    }

    public synchronized void updateAllCategories() {
        DataBase.getInstance().setAllCategoriesFormDataBase();
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(ArrayList<Category> allCategories, boolean a) {
        this.allCategories = allCategories;
        Gson gson = new Gson();
    }


    public synchronized void removeCategory(String name, DataOutputStream dataOutputStream) {
        updateAllCategories();
        for (Category category : allCategories) {
            if (category.getName().equals(name)) {
                ProductCenter.getInstance().updateAllProducts();
                if (category.getAllProducts() != null && !category.getAllProducts().isEmpty()) {
                    for (Product product : category.getAllProducts()) {
                        ProductCenter.getInstance().removeProduct(product, dataOutputStream);
                    }
                }
            }
        }

        for (Category category : allCategories) {
            if (category.getName().equals(name)) {
                allCategories.remove(category);
                Gson gson = new Gson();
                DataBase.getInstance().updateAllCategories(gson.toJson(allCategories));
                break;
            }
        }

        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("productRemoved", "productRemovesSuccessfully"), dataOutputStream);
    }

    public synchronized void addProductToCategory(Product product) {
        for (Category category : allCategories) {
            if (category.getName().equals(product.getProductsCategory())) {
                category.addProduct(product);
                DataBase.getInstance().updateAllCategories(new Gson().toJson(allCategories));
            }
        }
    }

    public synchronized void editCategory(Category category, DataOutputStream dataOutputStream) {
        for (int i = 0; i < allCategories.size(); i++) {
            if (allCategories.get(i).getName().equalsIgnoreCase(category.getName())) {
                allCategories.set(i, category);
            }
            ArrayList<String> features = new ArrayList<>();
            for (String feature : category.getFeatures().keySet()) {
                features.add(feature);
            }

            if (category.getAllProducts() != null && !category.getAllProducts().isEmpty()) {
                for (Product product : category.getAllProducts()) {
                    product.getFeaturesOfCategoryThatHas().put(features.get(features.size() - 1), "");
                    ProductCenter.getInstance().getProductWithId(product.getProductId()).getFeaturesOfCategoryThatHas().put(features.get(features.size() - 1), "");
                }
            }
        }
        DataBase.getInstance().updateAllCategories(new Gson().toJson(allCategories));
        ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@Category successfully edited", dataOutputStream);
    }

    public synchronized void removeProductFromCategory(Product product) {
        for (Category category : allCategories) {
            if (category.getName().trim().equals(product.getProductsCategory().trim())) {
                for (Product product1 : category.getAllProducts()) {
                    if (product1.getProductId().equals(product.getProductId())) {
                        category.getAllProducts().remove(product1);
                        break;
                    }
                }
            }
        }
        DataBase.getInstance().updateAllCategories(new Gson().toJson(allCategories));
    }

    public synchronized void replaceCategory(Category category, DataOutputStream dataOutputStream) {
        for (int i = 0; i < allCategories.size(); i++) {
            if (allCategories.get(i).getName().equals(category.getName())) {
                allCategories.set(i, category);
                break;
            }
        }
        DataBase.getInstance().updateAllCategories(new Gson().toJson(allCategories));
        ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@Category successfully edited", dataOutputStream);
    }

    public synchronized void deleteCategoryFeature(Category category) {
        for (int i = 0; i < allCategories.size(); i++) {
            if (allCategories.get(i).getName().equalsIgnoreCase(category.getName())) {
                allCategories.set(i, category);
            }
        }
        if (category.getAllProducts() != null && !category.getAllProducts().isEmpty()) {
            ArrayList<String> features = new ArrayList<>();
            for (String feature : category.getFeatures().keySet()) {
                features.add(feature);
            }
            for (String feature : category.getAllProducts().get(0).getFeaturesOfCategoryThatHas().keySet()) {
                if (!features.contains(feature)) {
                    for (Product product : category.getAllProducts()) {
                        product.deleteFeaturesOfCategoryThatHas(feature);
                        ProductCenter.getInstance().getProductWithId(product.getProductId()).deleteFeaturesOfCategoryThatHas(feature);
                    }
                    ProductCenter.getInstance().setAllProducts();
                    break;
                }
            }
        }
        DataBase.getInstance().updateAllCategories(new Gson().toJson(allCategories));
    }

    public synchronized void updateProductInCategory(Product product) {
        firstLoop:
        for (Category category : allCategories) {
            if (category.getName().equals(product.getProductsCategory())) {
                for (Product product1 : category.getAllProducts()) {
                    for (int i = 0; i < category.getAllProducts().size(); i++) {
                        if (category.getAllProducts().get(i).getProductId().equals(product.getProductId())) {
                            category.getAllProducts().set(i, product);
                            DataBase.getInstance().updateAllCategories(new Gson().toJson(allCategories));
                            break firstLoop;
                        }
                    }
                }
            }
        }
    }

    public synchronized void editProductInCategory(Product product) {
        if (allCategories != null) {
            for (Category category : allCategories) {
                if (product.getProductsCategory().equals(category.getName())) {
                    category.editProduct(product);
                }
            }
            DataBase.getInstance().updateAllCategories(new Gson().toJson(allCategories));
        }
    }
}
