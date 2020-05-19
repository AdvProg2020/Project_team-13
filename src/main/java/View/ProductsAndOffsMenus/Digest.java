package View.ProductsAndOffsMenus;

import Controller.Client.CartController;
import Controller.Client.ClientController;
import Models.Product.Product;
import Models.UserAccount.Customer;
import View.Menu;
import View.UserMenu.UserMenu;

public class Digest extends Menu {
    public Digest(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String userMenuOptions = "";
        userMenuOptions += "1.Add to Cart\n";
        userMenuOptions += "7.Help\n";
        userMenuOptions += "4.Back\n";
        userMenuOptions += "5.LogOut\n";
        userMenuOptions += "6.Login/Register\n";
        System.out.println(userMenuOptions);
    }

    @Override
    public void execute() {
        Product product = ClientController.getInstance().getCurrentProduct();
        product.showDigest();
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("Add to cart")) {
                CartController.getInstance().getCurrentCart().addProduct(product);
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                ClientController.getInstance().setCurrentUser(null);
                System.out.println("You Logged out!!");
                parentMenu.execute();
            } else if (command.equalsIgnoreCase("Login/Register")) {
                if (ClientController.getInstance().getCurrentUser() != null) {
                    System.out.println("you already logged in");
                } else {
                    Menu menu = new UserMenu(this).setScanner(scanner);
                    ClientController.getInstance().setCurrentMenu(menu);
                    menu.execute();
                }

            } else {
                System.err.println("invalid command");
            }
        }
        back();
    }

}
