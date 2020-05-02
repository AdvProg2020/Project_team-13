package Controller.Server;

import Models.Product.Category;
import Models.Request;

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

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }
}
