package View2.UserMenu.Customer;

import Controller.Client.CartController;
import Controller.Client.ClientController;
import Models.UserAccount.Customer;
import View2.Menu;

public class CustomerMenu extends Menu {
    public CustomerMenu(Menu parentMenu) {
        super(parentMenu);
    }


    @Override
    public void help() {
        String userMenuOptions = "";
        userMenuOptions += "1.View Discount Codes\n";
        userMenuOptions += "2.View Cart\n";
        userMenuOptions += "3.View Balance\n";
        userMenuOptions += "4.View Orders\n";
        userMenuOptions += "5.View personal info\n";
        userMenuOptions += "6.Help\n";
        userMenuOptions += "7.Back\n";
        userMenuOptions += "8.LogOut\n";
        System.out.println(userMenuOptions);

    }

    @Override
    public void execute() {
        String command;
        System.out.println(ClientController.getInstance().getCurrentUser().viewPersonalInfo());
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("view discount codes")) {
                ((Customer)ClientController.getInstance().getCurrentUser()).printAllDiscountCodes();
            }else if (command.equalsIgnoreCase("view personal info")) {
                Menu menu = new ViewAndEditInformationForCustomer(this).setScanner(scanner);
          //      ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }else if (command.equalsIgnoreCase("view Cart")) {
                Menu menu = new CartMenu(this).setScanner(scanner);
           //     ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }else if (command.equalsIgnoreCase("view balance")) {
                showMessage(String.valueOf(ClientController.getInstance().getCurrentUser().getCredit()));
            }else if (command.equalsIgnoreCase("view orders")) {
                Menu menu = new OrdersMenu(this).setScanner(scanner);
         //       ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
                showMessage(((Customer)ClientController.getInstance().getCurrentUser()).viewOrders());
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
