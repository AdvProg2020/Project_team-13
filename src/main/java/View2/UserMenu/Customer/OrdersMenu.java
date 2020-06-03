package View2.UserMenu.Customer;

import Controller.Client.CartController;
import Controller.Client.ClientController;
import Controller.Client.ProductController;
import Models.UserAccount.Customer;
import View2.Menu;

public class OrdersMenu extends Menu {
    public OrdersMenu(Menu parentMenu) {
        super(parentMenu);
    }
    @Override
    public void help() {
        String userMenuOptions = "";
        userMenuOptions += "1.Show Order [orderID]\n";
        userMenuOptions += "2.rate [productID] [1-5]\n";
        userMenuOptions += "3.View Balance\n";
        userMenuOptions += "4.Help\n";
        userMenuOptions += "5.Back\n";
        userMenuOptions += "6.LogOut\n";
        System.out.println(userMenuOptions);

    }

    @Override
    public void execute() {
        showMessage(((Customer) ClientController.getInstance().getCurrentUser()).viewOrders());
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("view discount codes")) {
                ((Customer)ClientController.getInstance().getCurrentUser()).printAllDiscountCodes();
            }else if (command.equalsIgnoreCase("view personal info")) {
                Menu menu = new ViewAndEditInformationForCustomer(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }else if (command.equalsIgnoreCase("view Cart")) {
                Menu menu = new CartMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }else if (command.matches("rate @p\\d+ \\d")) {
                ProductController.getInstance().rating(command.split("\\s")[1],Integer.parseInt(command.split("\\s")[2]));
            }else if (command.matches("show order @l\\d+")) {
                showMessage(((Customer)ClientController.getInstance().getCurrentUser()).findOrderWithId(command.split("\\s")[2]).viewOrders());
            }  else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                ClientController.getInstance().setCurrentUser(null);
                CartController.getInstance().setCurrentCart(null);
                System.out.println("You Logged out!!");
                parentMenu.execute();
            } else {
                System.err.println("invalid command");
            }
        }
        back();
    }


}
