package View.UserMenu.Manager;

import Controller.Client.ManagerController;
import View.Menu;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu parentMenu) {
        super(parentMenu);
    }
    @Override
    public void help() {
        String managerMenuOptions = "";
        managerMenuOptions += "1.view [username]\n";
        System.out.println(managerMenuOptions);
    }

    @Override
    public void execute() {
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches("view \\S+")) {
                    ManagerController.getInstance().viewUser(command.split("\\s")[1]);
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else {
                System.out.println("Invalid Command");
            }
        }
        back();

    }
}
