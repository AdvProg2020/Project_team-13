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

    public String getCategoriesStringForm() {
        updateAllCategories();
        String categoriesStringForm = "";
        if (allCategories != null && !allCategories.isEmpty()) {
            for (Category category : allCategories) {
                categoriesStringForm += category.getName() + "\n";
            }
            return categoriesStringForm.substring(0, categoriesStringForm.length() - 1);
        }else return "";
    }

    public static CategoryController getInstance() {
        if (categoryController == null) {
            categoryController = new CategoryController();
        }
        return categoryController;
    }

    public void printAllCategories() {
        updateAllCategories();
        if (allCategories != null) {
            String allCategories = "";
            int categoryCount = 1;
            for (Category category : this.allCategories) {
                if (category.getAllProducts() != null) {
                    allCategories += (categoryCount) + "." + category.getName() + "\t" + "Number of products in this category: " + category.getAllProducts().size() + " \n";
                } else {
                    allCategories += (categoryCount) + "." + category.getName() + "\t" + "Number of products in this category: " + "0" + " \n";
                }
                categoryCount++;
            }
            if (allCategories.length() != 0 && !allCategories.isEmpty()) {
                allCategories = allCategories.substring(0, allCategories.length() - 1);
            }
            ClientController.getInstance().getCurrentMenu().showMessage(allCategories);
        } else {
            ClientController.getInstance().getCurrentMenu().showMessage("there is no caegory");
        }
    }

    public void editCategoryFeatures(Category category, String kindOfEdit) {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("editCategory", kindOfEdit + new Gson().toJson(category)));
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
