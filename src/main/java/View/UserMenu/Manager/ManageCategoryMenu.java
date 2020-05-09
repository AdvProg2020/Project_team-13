package View.UserMenu.Manager;

import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Controller.Client.RequestController;
import Models.Product.Category;
import View.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SplittableRandom;
import java.util.regex.Pattern;

public class ManageCategoryMenu extends Menu {

    public ManageCategoryMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String manageCategoryMenu = "";
        manageCategoryMenu += "1.Add [category]\n";
        manageCategoryMenu += "2.Edit [category]\n";
        manageCategoryMenu += "3.Remove [category]\n";
        manageCategoryMenu += "4.Help\n";
        manageCategoryMenu += "5.Back";
        System.out.println(manageCategoryMenu);
    }

    @Override
    public void execute() {
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (Pattern.matches("add (\\w+ )*\\w+", command)) {
                addCategory(command.substring(4));
            } else if (Pattern.matches("remove (\\w+ )*\\w+", command)) {

            } else if (Pattern.matches("edit (\\w+ )*\\w+", command)) {

            } else if (command.equalsIgnoreCase("help")) {
                help();
            }
        }
        back();
    }

    private void addCategory(String name) {
        HashMap<String, ArrayList<String>> featuresOfCategory = new HashMap();
        String categoryName = getName("Category Name",name);
        String s;
        System.out.println("You need to define features for you'r category.");
        System.out.println("Please enter  a feature Name.");
        while (!(s = scanner.nextLine().trim()).equalsIgnoreCase("/done")) {
            if (Pattern.matches("(\\w+ )*\\w+", s) && !featuresOfCategory.containsKey(s)) {
                String featureName = s;
                ArrayList<String> featureModes = new ArrayList<>();
                System.out.println("Please Enter different modes of feature");
                while (!(s = scanner.nextLine().trim()).equalsIgnoreCase("/done")) {
                    if (Pattern.matches("(\\w+ )*\\w+", s) && !featureModes.contains(s)) {
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

    private String getName(String nameKind,String firstName) {
        String name=firstName;
        CategoryController.getInstance().updateAllCategories();
        while (true) {
            if (Pattern.matches("(\\w+ )*\\w+", name) && !CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                break;
            } else if (CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                System.out.println("Category with this name already exists.");
            } else {
                System.out.println(nameKind + " is Invalid");
            }
            System.out.println("Enter " + nameKind);
            name = scanner.nextLine().trim();
        }
        return name;
    }


}
