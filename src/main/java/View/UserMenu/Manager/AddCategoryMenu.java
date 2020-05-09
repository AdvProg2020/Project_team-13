package View.UserMenu.Manager;

import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Controller.Client.ProductController;
import Models.Product.Category;
import View.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AddCategoryMenu extends Menu {

    public AddCategoryMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void help() {
        String addCategoryMenu = "";
        addCategoryMenu += "1.Add category\n";
        addCategoryMenu += "2.Back\n";
        addCategoryMenu += "3.Help";
        System.out.println(addCategoryMenu);
    }

    @Override
    public void execute() {
        String command;
        addCategory();
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("add category")) {
                addCategory();
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else {
                System.out.println("command is invalid.");
            }
        }
        back();
    }

    private void addCategory() {
        HashMap<String, ArrayList<String>> featuresOfCategory = new HashMap();
        String categoryName = getName("Category Name");
        String s;
        System.out.println("You need to define features for you'r category.");
        System.out.println("Please enter  a feature Name.");
        while (!(s = scanner.nextLine().trim()).equalsIgnoreCase("/done")) {
            if (Pattern.matches("\\w+", s) && !featuresOfCategory.containsKey(s)) {
                String featureName = s;
                ArrayList<String> featureModes = new ArrayList<>();
                System.out.println("Please Enter different modes of feature");
                while (!(s = scanner.nextLine().trim()).equalsIgnoreCase("/done")) {
                    if (Pattern.matches("\\w+", s) && !featureModes.contains(s)) {
                        featureModes.add(s);
                        System.out.println("Enter another one or type /done to finish adding modes.");
                    } else if (featureModes.contains(s)) {
                        System.out.println("you already entered that mode.");
                    } else {
                        System.out.println("Enter In correct Form");
                    }
                }
                featuresOfCategory.put(featureName, featureModes);
                System.out.println("Please enter another feature name , or type /done to finish adding features.");
            } else if (featuresOfCategory.containsKey(s)) {
                System.out.println("you already entered that feature.");
            } else {
                System.out.println("Enter In Correct Form.you can just use alphabetical characters.");
            }
        }
        CategoryController.getInstance().addNewCategory(new Category(categoryName, featuresOfCategory));
    }

    private String getName(String nameKind) {
        String name;
        CategoryController.getInstance().updateAllCategories();
        while (true) {
            System.out.println("Enter " + nameKind);
            name = scanner.nextLine().trim();
            if (Pattern.matches("(\\w+ )*\\w+", name) && !CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                break;
            } else if (CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                System.out.println("Category with this name already exists.");
            } else {
                System.out.println(nameKind + " is Invalid");
            }
        }
        return name;
    }

}
