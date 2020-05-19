package View.UserMenu.Manager;

import Controller.Client.ClientController;
import Controller.Client.DiscountController;
import Controller.Client.ManagerController;
import Controller.Client.RequestController;
import Models.UserAccount.Manager;
import View.Menu;
import View.UserMenu.LoginMenu;
import View.UserMenu.RegisterMenu;

import java.util.regex.Pattern;

public class ManagerMenu extends Menu {

    public ManagerMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String managerMenuOptions = "";
        managerMenuOptions += "1.Manage Requests\n";
        managerMenuOptions += "2.Manage Users\n";
        managerMenuOptions += "3.Create Manager Profile\n";
        managerMenuOptions += "4.Manage Categories\n";
        managerMenuOptions += "5.Create Discount Code\n";
        managerMenuOptions += "6.View Discount Codes\n";
        managerMenuOptions += "7.View Personal Info\n";
        managerMenuOptions += "8.manage all products\n";
        managerMenuOptions += "9.LogOut\n";
        managerMenuOptions += "10.Help\n";
        managerMenuOptions += "11.Back";
        System.out.println(managerMenuOptions);
    }

    @Override
    public void execute() {
        System.out.println(ClientController.getInstance().getCurrentUser().viewPersonalInfo());
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("manage requests")) {
                Menu menu = new ManageRequestMenu(this).setScanner(this.scanner);
                RequestController.getInstance().getAllRequestsFromServer();
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("manage users")) {
                Menu menu = new ManageUsersMenu(this).setScanner(this.scanner);
                ManagerController.getInstance().getAllUserFromServer();
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("create manager profile")) {
                Menu menu = new CreateManagerMenu(this).setScanner(this.scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                ClientController.getInstance().setCurrentUser(null);
                System.out.println("You Logged out!!");
                parentMenu.execute();
            } else if (command.equalsIgnoreCase("manage categories")) {
                Menu menu = new ManageCategoryMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }else if (command.equalsIgnoreCase("create discount code")) {
                Menu menu = new CreateDiscountCodeMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }else if (command.equalsIgnoreCase("view discount codes")) {
                Menu menu = new ViewDiscountMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }else if (command.equalsIgnoreCase("view personal info")) {
                Menu menu = new ViewAndEditInformationForManager(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("manage all products")) {
                Menu menu = new ManageProductsMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else {
                System.err.println("invalid command");
            }
        }
        back();

    }
}
