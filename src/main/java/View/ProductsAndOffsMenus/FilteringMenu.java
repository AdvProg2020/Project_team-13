package View.ProductsAndOffsMenus;

import Controller.Client.CategoryController;
import Models.Product.Category;
import View.Menu;

import java.util.regex.Pattern;

public class FilteringMenu extends Menu {

    public FilteringMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {

    }

    @Override
    public void execute() {
        String command;

        while ((command=scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if(command.equalsIgnoreCase("filter")) {
                System.out.println("First you need to pick a category to filter.please enter a category");
                Category category=getCategoryName("category name","");

            }else if(Pattern.matches("disable filter (\\w+ )*\\w+",command)) {

            }else if(command.equalsIgnoreCase("current filters")) {

            }else if(command.equalsIgnoreCase("show available filters")) {

            }else if(command.equalsIgnoreCase("help")) {

            }
        }
        System.out.println("First you need to pick a category to filter.please enter a category");
        Category category=getCategoryName("category name","");


    }
    private Category getCategoryName(String nameKind, String firstName) {
        String name = firstName;
        CategoryController.getInstance().updateAllCategories();
        while (true) {
            if (Pattern.matches("(\\w+ )*\\w+", name) && CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                return CategoryController.getInstance().getCategoryWithName(name);
            } else if (!CategoryController.getInstance().isThereCategoryWithThisName(name)) {
                System.out.println("Category with this name doesn't exists.");
            } else {
                System.out.println(nameKind + " is Invalid");
            }
            System.out.println("Enter " + nameKind);
            name = scanner.nextLine().trim();
        }
    }


}
