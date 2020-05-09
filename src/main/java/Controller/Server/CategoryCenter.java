package Controller.Server;

import Models.Product.Category;
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

    public void setAllCategories(ArrayList<Category> allCategories,boolean a) {
        this.allCategories = allCategories;
        Gson gson=new Gson();
        DataBase.getInstance().updateAllCategories(gson.toJson(this.allCategories));
    }

    public void setAllCategories(ArrayList<Category> allCategories) {
        this.allCategories = allCategories;
        Gson gson=new Gson();
        DataBase.getInstance().updateAllCategories(gson.toJson(this.allCategories));
    }
}
