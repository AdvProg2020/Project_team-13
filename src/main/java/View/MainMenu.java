package View;

import Controller.Client.ClientController;
import Controller.Server.UserCenter;
import Models.Product.Product;
import View.ProductsAndOffsMenus.OffsMenu;
import View.ProductsAndOffsMenus.ProductsMenu;
import View.UserMenu.UserMenu;

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
        mainMenuOptions += "4.help";
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
            } else {
                System.out.println("Invalid Command");
            }
        }

    }
}
