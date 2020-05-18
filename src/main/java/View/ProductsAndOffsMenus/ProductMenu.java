package View.ProductsAndOffsMenus;

import Controller.Client.CartController;
import Controller.Client.ClientController;
import Controller.Client.ProductController;
import Models.Product.Cart;
import Models.Product.Product;
import View.Menu;
import View.UserMenu.UserMenu;

public class ProductMenu extends Menu {
    public ProductMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String userMenuOptions = "";
        userMenuOptions += "1.Digest\n";
        userMenuOptions += "2.Attributes\n";
        userMenuOptions += "3.Help\n";
        userMenuOptions += "4.Back\n";
        userMenuOptions += "5.Compare [product id]\n";
        userMenuOptions += "6.Comments\n";
        userMenuOptions += "7.LogOut\n";
        System.out.println(userMenuOptions);

    }

    @Override
    public void execute() {
        if (CartController.getInstance().getCurrentCart() == null) {
            CartController.getInstance().setCurrentCart(new Cart());
        }
        Product product = ClientController.getInstance().getCurrentProduct();
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("digest")) {
                Menu menu = new Digest(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("attributes")) {
                product.showAttributes();
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.matches("compare @p\\d+")) {
                if (command.substring(8).equals(product.getProductId())) {
                    System.out.println("You Entered The Current Product Id.");
                } else {
                    ProductController.getInstance().compareWithProduct(command.substring(8));
                }
            } else if (command.equalsIgnoreCase("logout")) {
                if (ClientController.getInstance().getCurrentUser() != null) {
                    ClientController.getInstance().setCurrentUser(null);
                    System.out.println("You Logged out!!");
                    parentMenu.execute();
                } else {
                    printError("you are not signed yet!!");
                }
            } else if (command.equalsIgnoreCase("comments")) {
                Menu menu = new CommentMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("login")) {
                Menu menu = new UserMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else {
                System.out.println("Invalid Command");
            }
        }
    }

}
