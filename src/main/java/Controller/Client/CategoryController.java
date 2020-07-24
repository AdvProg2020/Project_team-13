package Controller.Client;

import Models.Product.Category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoryController {
    private ArrayList<Category> allCategories;
    private static CategoryController categoryController;
    private Category currentCategory;


    private CategoryController() {
        this.allCategories = new ArrayList<>();
    }

    public static CategoryController getInstance() {
        if (categoryController == null) {
            categoryController = new CategoryController();
        }
        return categoryController;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public void editCategoryFeatures(Category category, String kindOfEdit) {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("editCategory", kindOfEdit + new Gson().toJson(category)));
    }

    public void updateAllCategories() {
        ClientController.getInstance().sendMessageToServer("@getAllCategories@");
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(String message) {
        Type categoryListType = new TypeToken<ArrayList<Category>>() {
        }.getType();
        Gson gson = new Gson();
        if (message != null&& !message.isEmpty() ) {
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
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("removeCategory", name));
    }

    public Category getCategoryWithName(String name) {
        for (Category category : allCategories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }


}
