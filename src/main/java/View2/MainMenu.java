package View2;

import Controller.Client.ClientController;
import View2.ProductsAndOffsMenus.OffsMenu;
import View2.ProductsAndOffsMenus.ProductsMenu;
import View2.UserMenu.UserMenu;

public class MainMenu extends Menu {

    public MainMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String mainMenuOptions = "";
        mainMenuOptions += "1.EnterUserMenu\n";
        mainMenuOptions += "2.products\n";
        mainMenuOptions += "3.offs\n";
        mainMenuOptions += "4.help\n";
        mainMenuOptions += "5.logout\n";
        System.out.println(mainMenuOptions);

    }

    @Override
    public void execute() {
        ClientController.getInstance().setCurrentMenu(this);

        while (true) {
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("products")) {
                Menu menu = new ProductsMenu(this).setScanner(this.scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("offs")) {
                Menu menu = new OffsMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("EnterUserMenu")) {
                Menu menu = new UserMenu(this).setScanner(this.scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                if (ClientController.getInstance().getCurrentUser() != null) {
                    ClientController.getInstance().setCurrentUser(null);
                    System.out.println("You Logged out!!");
                    parentMenu.getParentMenu().execute();
                } else {
                    printError("you are not signed yet!!");
                }
            }  else {
                System.out.println("Invalid Command");
            }
        }

    }
}