package View2.UserMenu.Seller;

import Controller.Client.ClientController;
import Controller.Client.OffsController;
import Controller.Client.ProductController;
import Models.UserAccount.Seller;
import View2.Menu;

public class ViewOffsMenu extends Menu {
    private String commandForEdit;

    public ViewOffsMenu(Menu parentMenu) {
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
        String ViewOffsMenuHelp = "";
        ViewOffsMenuHelp += "1. view [offId]\n";
        ViewOffsMenuHelp += "2. edit [offId]\n";
        ViewOffsMenuHelp += "3. add off\n";
        ViewOffsMenuHelp += "4. help\n";
        ViewOffsMenuHelp += "5. back\n";
        ViewOffsMenuHelp += "6 logout";
        System.out.println(ViewOffsMenuHelp);
    }

    @Override
    public void execute() {
        ProductController.getInstance().getAllProductsFromServer();
        OffsController.getInstance().getAllOffsFromServer();
        String command;
        OffsController.getInstance().printAllOffs((Seller) ClientController.getInstance().getCurrentUser());
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("view @o\\d+")) {
                OffsController.getInstance().printOffById((Seller) ClientController.getInstance().getCurrentUser(), getTheOffId(command));
            } else if (command.matches("edit @o\\d+")) {
                commandForEdit = command;
                Menu menu = new EditOffsMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("add off")) {
                Menu menu = new AddOffsMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equalsIgnoreCase("help")) {
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

    private String getTheOffId(String command) {
        return command.substring(5, command.length());
    }

    public String getTheIdForEdit() {
        return getTheOffId(commandForEdit);
    }
}
