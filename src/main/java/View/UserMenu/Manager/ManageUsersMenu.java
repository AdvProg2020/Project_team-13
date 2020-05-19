package View.UserMenu.Manager;

import Controller.Client.ClientController;
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
        managerMenuOptions += "2.delete user [username]\n";
        managerMenuOptions += "3.help\n";
        managerMenuOptions += "4.back\n";
        managerMenuOptions += "5.logout\n";
        System.out.println(managerMenuOptions);
    }

    @Override
    public void execute() {
        ManagerController.getInstance().printAllCustomers();
        ManagerController.getInstance().printAllSellers();
        ManagerController.getInstance().printAllManagers();
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches("view \\S+")) {
                    ManagerController.getInstance().viewUser(command.split("\\s")[1]);
            }else if (command.matches("delete user \\S+")) {
                ManagerController.getInstance().deleteUser(command.split("\\s")[2]);
            } else if (command.equalsIgnoreCase("help")) {
                help();
            }  else if (command.equalsIgnoreCase("logout")) {
                ClientController.getInstance().setCurrentUser(null);
                System.out.println("You Logged out!!");
                parentMenu.getParentMenu().execute();
            }else {
                System.err.println("invalid command");
            }
        }
        back();

    }
}
