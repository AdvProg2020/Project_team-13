package View.UserMenu.Manager;

import Controller.Client.ClientController;
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
        managerMenuOptions += "4.Add Category\n";
        managerMenuOptions += "5.LogOut\n";
        managerMenuOptions += "6.Help\n";
        managerMenuOptions += "7.Back";
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
                parentMenu.execute();
            } else if (command.equalsIgnoreCase("manage categories")) {
                Menu menu = new ManageCategoryMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else {
                System.out.println("Invalid Command");
            }
        }
        back();

    }
}
