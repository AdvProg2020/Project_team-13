package View.UserMenu.Customer;

import Controller.Client.ClientController;
import View.Menu;
import View.UserMenu.LoginMenu;
import View.UserMenu.RegisterMenu;

public class CustomerMenu extends Menu {
    public CustomerMenu(Menu parentMenu) {
        super(parentMenu);
    }


    @Override
    public void help() {
        String userMenuOptions = "";
        userMenuOptions += "Register\n";
        userMenuOptions += "Login\n";
        userMenuOptions += "Help\n";
        userMenuOptions += "Back\n";
        userMenuOptions += "LogOut\n";
        System.out.println(userMenuOptions);

    }

    @Override
    public void execute() {
        String command;
        System.out.println(ClientController.getInstance().getCurrentUser().viewPersonalInfo());
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("Register")) {
                Menu menu = new RegisterMenu(this).setScanner(this.scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("Login")) {
                Menu menu = new LoginMenu(this).setScanner(this.scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                ClientController.getInstance().setCurrentUser(null);
                System.out.println("You Logged out!!");
                parentMenu.execute();
            } else {
                System.out.println("Invalid Command");
            }
        }
        back();
    }


}
