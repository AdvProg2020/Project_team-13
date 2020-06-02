package View.ProductsAndOffsMenus;

import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Controller.Client.ProductController;
import Controller.Server.ProductCenter;
import Models.Product.Category;
import Models.Product.Product;
import View.Menu;
import View.UserMenu.Customer.CartMenu;
import View.UserMenu.Seller.SellerMenu;
import View.UserMenu.UserMenu;

import java.util.regex.Pattern;

public class ProductsMenu extends Menu {
    public ProductsMenu(Menu parentMenu) {
        super(parentMenu);
        ProductController.getInstance().getAllProductsFromServer();
        ProductController.getInstance().setAllFiltersNull();
    }

    @Override
    public void help() {
        String productsMenuHelp = "1.view categories\n";
        productsMenuHelp += "2.filtering\n3.sorting\n4.show products\n5.show product [productId]\n6.help\n7.back\n8.View Cart\n9.logout\n10.Login/Register";
        System.out.println(productsMenuHelp);

    }

    @Override
    public void execute() {
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("view Categories")) {
                CategoryController.getInstance().printAllCategories();
            } else if (command.equalsIgnoreCase("filtering")) {
                Menu menu = new FilteringMenu(parentMenu).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("sorting")) {
                Menu menu = new SortingMenu(parentMenu).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("show products")) {
                ProductController.getInstance().showProductsAfterFilterAndSort();
            } else if (Pattern.matches("show product @p\\d+", command)) {
                ClientController.getInstance().setCurrentProduct(ProductController.getInstance().findProductAfterFilter(command.split("\\s")[2]));
                if (ClientController.getInstance().getCurrentProduct() != null) {
                    Menu menu = new ProductMenu(this).setScanner(scanner);
                    ClientController.getInstance().setCurrentMenu(menu);
                    menu.execute();
                } else {
                    printError("there is no product with this ID");
                }
            } else if (command.equalsIgnoreCase("view Cart")) {
                Menu menu = new CartMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                if (ClientController.getInstance().getCurrentUser() != null) {
                    ClientController.getInstance().setCurrentUser(null);
                    System.out.println("You Logged out!!");
                    parentMenu.execute();
                } else {
                    printError("you are not signed yet!!");
                }
            } else if (command.equalsIgnoreCase("Login/Register")) {
                if (ClientController.getInstance().getCurrentUser() != null) {
                    System.out.println("you already logged in");
                } else {
                    Menu menu = new UserMenu(this).setScanner(scanner);
                    ClientController.getInstance().setCurrentMenu(menu);
                    menu.execute();
                }
            } else {
                System.out.println("command is invalid");
            }
        }

    }

    public void showAllProducts() {
    }
}
