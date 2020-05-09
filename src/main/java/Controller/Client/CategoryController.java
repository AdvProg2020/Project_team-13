package Controller.Client;

import Models.Product.Category;
import View.Menu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoryController {
    private ArrayList<Category> allCategories;
    private static CategoryController categoryController;

    private CategoryController() {
        this.allCategories = new ArrayList<>();
    }

    public static CategoryController getInstance() {
        if (categoryController == null) {
            categoryController = new CategoryController();
        }
        return categoryController;
    }

    public void updateAllCategories() {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("getAllCategories", "getAllCategories"));
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public boolean isThereCategoryWithThisName(String name) {
        if (allCategories != null && !allCategories.isEmpty()) {
            for (Category category : allCategories) {
                if (category.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setAllCategories(ArrayList<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public void setAllCategories(String message) {
        Type categoryListType = new TypeToken<ArrayList<Category>>() {
        }.getType();
        Gson gson = new Gson();
        if (!message.isEmpty() && message != null) {
            ArrayList<Category> allCategories = gson.fromJson(message, categoryListType);
            this.allCategories = allCategories;
        }
    }

    public void addNewCategory(Category category) {
        allCategories.add(category);
        Gson gson = new Gson();
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("updateAllCategories", gson.toJson(allCategories)));
    }

    public void removeCategory(String name) {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("removeCategory",name));
    }

    public Category getCategoryWithName(String name) {
        for (Category category : allCategories) {
            if(category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
}
