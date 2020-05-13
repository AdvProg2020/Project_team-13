package Controller.Server;

import Models.Product.Category;
import Models.Product.Product;
import Models.Request;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CategoryCenter {
    private static CategoryCenter categoryCenter;
    private ArrayList<Category> allCategories = new ArrayList<>();

    private CategoryCenter() {
    }

    public static CategoryCenter getIncstance() {
        if (categoryCenter == null) {
            categoryCenter = new CategoryCenter();
        }
        return categoryCenter;
    }

    public void updateAllCategories() {
        DataBase.getInstance().setAllCategoriesFormDataBase();
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(ArrayList<Category> allCategories, boolean a) {
        this.allCategories = allCategories;
        Gson gson = new Gson();
        DataBase.getInstance().updateAllCategories(gson.toJson(this.allCategories));
    }

    public void setAllCategories(ArrayList<Category> allCategories) {
        this.allCategories = allCategories;
        Gson gson = new Gson();
        DataBase.getInstance().updateAllCategories(gson.toJson(this.allCategories));
    }

    public void removeCategory(String name) {
        updateAllCategories();
        for (Category category : allCategories) {
            if (category.getName().equals(name)) {
                ProductCenter.getInstance().updateAllProducts();
                if (category.getAllProducts() != null && !category.getAllProducts().isEmpty()) {
                    for (Product product : category.getAllProducts()) {
                        ProductCenter.getInstance().removeProduct(product);
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

        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("productRemoved", "productRemovesSuccessfully"));
    }

    public void addProductToCategory(Product product) {
        for (Category category : allCategories) {
            if(category.getName().equals(product.getProductsCategory())) {
                category.addProduct(product);
                DataBase.getInstance().updateAllCategories(new Gson().toJson(allCategories));
            }
        }
    }
}
