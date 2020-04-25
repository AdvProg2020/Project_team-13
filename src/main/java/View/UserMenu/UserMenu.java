package View.UserMenu;

import Controller.Client.ClientController;
import View.Menu;

public class UserMenu extends Menu {
    public UserMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String userMenuOptions = "";
        userMenuOptions += "1.Register\n";
        userMenuOptions += "2.Login\n";
        userMenuOptions += "3.help";
        System.out.println(userMenuOptions);

    }

    @Override
    public void execute() {
        while (true) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("Register")) {

            } else if (command.equalsIgnoreCase("Login")) {

            } else if (command.equalsIgnoreCase("help")) {

            } else if (command.equalsIgnoreCase("back")) {
                ClientController.getInstance().setCurrentMenu(this.parentMenu);
                this.parentMenu.execute();
            } else {
                System.out.println("Invalid Command");
            }
        }
    }

}

