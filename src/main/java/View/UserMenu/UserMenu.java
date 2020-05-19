package View.UserMenu;

import Controller.Client.ClientController;
import View.MainMenu;
import View.Menu;
import View.UserMenu.Customer.CustomerMenu;
import View.UserMenu.Manager.ManagerMenu;
import View.UserMenu.Seller.SellerMenu;

public class UserMenu extends Menu {
    public UserMenu(Menu parentMenu) {
        super(parentMenu);
    }


    @Override
    public void help() {
        String userMenuOptions = "";
        userMenuOptions += "1. Register\n";
        userMenuOptions += "2. Login\n";
        userMenuOptions += "3. Help\n";
        userMenuOptions += "4. Back\n";
        System.out.println(userMenuOptions);

    }

    @Override
    public void execute() {
        if (ClientController.getInstance().getCurrentUser() == null) {
            String command;
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
                } else {
                    printError("Invalid Command");
                }
            }
        } else if (ClientController.getInstance().getCurrentUser() != null && parentMenu instanceof MainMenu) {
            if (ClientController.getInstance().getCurrentUser().getType().equalsIgnoreCase("@Seller")) {
                Menu menu = new SellerMenu(parentMenu).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (ClientController.getInstance().getCurrentUser().getType().equalsIgnoreCase("@Customer")) {
                Menu menu = new CustomerMenu(parentMenu).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (ClientController.getInstance().getCurrentUser().getType().equalsIgnoreCase("@Manager")) {
                Menu menu = new ManagerMenu(parentMenu).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }
            return;
        }
        if(!(parentMenu instanceof MainMenu)){
            back();
        }
    }



}
