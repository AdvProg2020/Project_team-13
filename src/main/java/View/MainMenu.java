package View;

import Controller.Client.ClientController;
import Models.Product.Product;
import View.UserMenu.UserMenu;

public class MainMenu extends Menu {

    public MainMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String mainMenuOptions="";
        mainMenuOptions+="1.EnterUserMenu\n";
        mainMenuOptions+="2.products\n";
        mainMenuOptions+="3.offs\n";
        mainMenuOptions+="4.help";
        System.out.println(mainMenuOptions);

    }

    @Override
    public void execute() {
        while (true) {
            String command=scanner.nextLine().trim();
            if (command.equalsIgnoreCase("products")) {

            } else if(command.equalsIgnoreCase("offs")) {

            }else if(command.equalsIgnoreCase("EnterUserMenu")) {
                Menu menu=new UserMenu(this).setScanner(this.scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }else if (command.equalsIgnoreCase("help")) {
                help();
            }else {
                System.out.println("Invalid Command");
            }
        }

    }
}
