package View.ProductsAndOffsMenus;

import Controller.Client.ProductController;
import Models.Product.Product;
import View.Menu;

import java.util.regex.Pattern;

public class SortingMenu extends Menu {
    public SortingMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String sortingMenuHelp = "1.Sort by (price|score|newest) (ascending|descending)" +
                "\n2.Show sorts\n3.current sort\n4.disable sort\n5.help\n6.back";
        System.out.println(sortingMenuHelp);

    }

    @Override
    public void execute() {
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (Pattern.matches("Sort by (price|score|newest) (ascending|descending)", command)) {
                sort(command);
                System.out.println("sort is activated");
            } else if (command.equalsIgnoreCase("Show sorts")) {
                String sorts = "1.Sort by price ascending\n2.Sort by price descending\n3.Sort by score ascending" +
                        "\n4.Sort by score descending\n5.Sort by newest ascending\n6.Sort by newest descending";
                System.out.println(sorts);
            } else if (command.equalsIgnoreCase("current sort")) {
                System.out.println(ProductController.getInstance().getCurrentSort());
            } else if (command.equalsIgnoreCase("disable sort")) {
                System.out.println(ProductController.getInstance().disableSort());
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else {
                System.out.println("command is invalid.");
            }
        }

    }

    private void sort(String command) {
        if (command.equalsIgnoreCase("Sort by price ascending")) {
            ProductController.getInstance().setCurrentSort("price", true);
        } else if (command.equalsIgnoreCase("Sort by price descending")) {
            ProductController.getInstance().setCurrentSort("price", false);
        } else if (command.equalsIgnoreCase("Sort by score ascending")) {
            ProductController.getInstance().setCurrentSort("score", true);
        } else if (command.equalsIgnoreCase("Sort by score descending")) {
            ProductController.getInstance().setCurrentSort("score", false);
        } else if (command.equalsIgnoreCase("Sort by newest ascending")) {
            ProductController.getInstance().setCurrentSort("newest", true);
        } else if (command.equalsIgnoreCase("Sort by newest descending")) {
            ProductController.getInstance().setCurrentSort("newest", false);
        }
    }

}
