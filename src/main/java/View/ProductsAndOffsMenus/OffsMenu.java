package View.ProductsAndOffsMenus;

import Controller.Client.ClientController;
import Controller.Client.OffsController;
import Controller.Client.ProductController;
import View.Menu;
import View.UserMenu.Manager.ManagerMenu;
import View.UserMenu.UserMenu;

public class OffsMenu extends Menu {
    public OffsMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void printError(String error) {
        super.printError(error);
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

    @Override
    public void help() {
        String offsMenuHelp = "";
        offsMenuHelp += "1.show product [productId]\n";
        offsMenuHelp += "2.filtering\n";
        offsMenuHelp += "3.sorting\n";
        offsMenuHelp += "4.show products\n";
        offsMenuHelp += "5.help\n";
        offsMenuHelp += "6.back\n";
        offsMenuHelp += "7.logout\n";
        offsMenuHelp += "8.Login/Register";
        System.out.println(offsMenuHelp);
    }

    @Override
    public void execute() {
        ProductController.getInstance().getAllProductsFromServer();
        OffsController.getInstance().getAllOffsFromServer();
        OffsController.getInstance().showAllOffs();
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("show product @p\\d+")) {
                ClientController.getInstance().setCurrentProduct(ProductController.getInstance().findProductAfterFilterInOffer(command.split("\\s")[2]));
                if (ClientController.getInstance().getCurrentProduct() != null) {
                    Menu menu = new ProductMenu(this).setScanner(scanner);
                    ClientController.getInstance().setCurrentMenu(menu);
                    menu.execute();
                } else {
                    printError("there is no product with this ID in your Cart");
                }
            } else if (command.equalsIgnoreCase("filtering")) {
                Menu menu = new FilteringMenu(parentMenu).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("sorting")) {
                Menu menu = new SortingMenu(parentMenu).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("show products")) {
                ProductController.getInstance().showOffedProductsAfterFilterAndSort();
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
                System.out.println("invalid command");
            }
        }
        back();
    }
}
