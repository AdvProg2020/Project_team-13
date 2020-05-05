package View.UserMenu.Manager;

import Controller.Client.ClientController;
import Controller.Client.RequestController;
import View.Menu;
import View.UserMenu.LoginMenu;
import View.UserMenu.RegisterMenu;

public class ManagerMenu extends Menu {

    public ManagerMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String managerMenuOptions = "";
        managerMenuOptions += "1.Manage Requests\n";
        managerMenuOptions += "LogOut";
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
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                ClientController.getInstance().setCurrentUser(null);
                parentMenu.execute();
            } else {
                System.out.println("Invalid Command");
            }
        }
        back();

    }
}
