package View2.UserMenu.Manager;

import Controller.Client.ClientController;
import Controller.Client.ProductController;
import View2.Menu;

public class ManageProductsMenu extends Menu {
    public ManageProductsMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void printError(String error) {
        super.printError(error);
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

    @Override
    public void help() {
        String ManageProductMenuHelp = "";
        ManageProductMenuHelp += "1.remove [productId]\n";
        ManageProductMenuHelp += "2.help\n";
        ManageProductMenuHelp += "3.back\n";
        ManageProductMenuHelp += "4.logout\n";
        System.out.println(ManageProductMenuHelp);
    }

    @Override
    public void execute() {
        String command;
        ProductController.getInstance().printAllProducts();
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("remove @p\\w+")) {
                ProductController.getInstance().removeProductForManager(getTheProductId(command));
            } else if (command.equals("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                ClientController.getInstance().setCurrentUser(null);
                System.out.println("You Logged out!!");
                parentMenu.getParentMenu().execute();
            } else {
                System.err.println("invalid command");
            }
        }
        back();
    }

    private String getTheProductId(String command) {
        return command.substring(7, command.length());
    }
}
