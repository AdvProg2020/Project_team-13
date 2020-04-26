package View.UserMenu.Manager;

import Controller.Client.ClientController;
import View.Menu;

public class ManagerMenu extends Menu{

    public ManagerMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String managerMenuOptions = "";
        System.out.println(managerMenuOptions);
    }

    @Override
    public void execute() {
        ClientController.getInstance().getCurrentUser().viewPersonalInfo();
        String command;
        while (!(command=scanner.nextLine().trim()).equalsIgnoreCase("Back")) {
            if(command.equalsIgnoreCase("help")){
                help();
            }
        }

    }
}
