package View2.UserMenu.Customer;

import Controller.Client.CartController;
import Controller.Client.ClientController;
import Models.Product.Cart;
import View2.Menu;
import View2.ProductsAndOffsMenus.ProductMenu;
import View2.UserMenu.UserMenu;

public class CartMenu extends Menu {
    public CartMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String userMenuOptions = "";
        userMenuOptions += "1.Show Products\n";
        userMenuOptions += "2.View Product [productID]\n";
        userMenuOptions += "3.Increase [productID]\n";
        userMenuOptions += "4.Decrease [productID]\n";
        userMenuOptions += "5.Show Total Price\n";
        userMenuOptions += "6.Purchase\n";
        userMenuOptions += "7.Help\n";
        userMenuOptions += "4.Back\n";
        userMenuOptions += "5.LogOut\n";
        System.out.println(userMenuOptions);

    }

    @Override
    public void execute() {
        Cart cart = CartController.getInstance().getCurrentCart();
        if (cart == null) {
            CartController.getInstance().setCurrentCart(new Cart());
            cart = CartController.getInstance().getCurrentCart();
        }
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("Show Products")) {
                cart.showProducts();
            } else if (command.matches("view product \\S+")) {
                if (command.matches("view product @p\\d+")) {
                    ClientController.getInstance().setCurrentProduct(cart.findProductWithID(command.split("\\s")[2]));
                    if (ClientController.getInstance().getCurrentProduct() != null) {
                        Menu menu = new ProductMenu(this).setScanner(scanner);
                        //   ClientController.getInstance().setCurrentMenu(menu);
                        menu.execute();
                    } else {
                        printError("there is no product with this ID in your Cart");
                    }
                } else {
                    printError("this ID isn't a productID");
                }
            } else if (command.matches("increase \\S+")) {
                if (command.matches("increase @p\\d+")) {
                    System.out.println("How many do you want to increase it?");
                    int count = scanner.nextInt();
                    cart.changeCountOfProduct(command.split("\\s")[1], count);
                } else {
                    printError("this ID isn't a productID");
                }
            } else if (command.matches("decrease \\S+")) {
                if (command.matches("decrease @p\\d+")) {
                    System.out.println("How many do you want to decrease it?");
                    int count = scanner.nextInt();
                    cart.changeCountOfProduct(command.split("\\s")[1], -count);
                } else {
                    printError("this ID isn't a productID");
                }
            } else if (command.equalsIgnoreCase("show total price")) {
                System.out.println(cart.getTotalPrice());
            } else if (command.equalsIgnoreCase("purchase")) {
                if (cart.getTotalPrice() > 0) {
                    if (ClientController.getInstance().getCurrentUser() != null) {
                        Menu menu = new CustomerInfoForPurchaseMenu(this).setScanner(scanner);
                        //ClientController.getInstance().setCurrentMenu(menu);
                        menu.execute();
                    } else {
                        printError("you should Login or Register before purchase");
                        Menu menu = new UserMenu(this).setScanner(scanner);
                        //ClientController.getInstance().setCurrentMenu(menu);
                        menu.execute();
                    }
                } else {
                    printError("you haven't any product in your cart");
                }
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                ClientController.getInstance().setCurrentUser(null);
                System.out.println("You Logged out!!");
                parentMenu.execute();
            } else {
                System.err.println("invalid command");
            }
        }
        back();
    }

}
