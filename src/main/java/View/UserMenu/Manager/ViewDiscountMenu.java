package View.UserMenu.Manager;

import Controller.Client.ClientController;
import Controller.Client.DiscountController;
import View.Menu;

public class ViewDiscountMenu extends Menu {
    public ViewDiscountMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String managerMenuOptions = "";
        managerMenuOptions += "1.View discount code [code]\n";
        managerMenuOptions += "2.Edit discount code [code]\n";
        managerMenuOptions += "3.remove discount code [code]\n";
        managerMenuOptions += "4.help\n";
        managerMenuOptions += "5.back\n";
        managerMenuOptions += "6.logout\n";
        System.out.println(managerMenuOptions);
    }

    @Override
    public void execute() {
        DiscountController.getInstance().getAllDiscountCodesFromServer();
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.startsWith("view discount code")) {
                DiscountController.getInstance().viewDiscountCode(command.split("\\s")[3]);
            } else if (command.matches("remove discount code \\S+")) {
                DiscountController.getInstance().deleteDiscountCode(command.split("\\s")[3]);
            } else if (command.matches("edit discount code \\S+")) {
                ClientController.getInstance().setCurrentDiscountCode(DiscountController.getInstance().findDiscountCodeWithThisId(command.split("\\s")[3]));
                if (ClientController.getInstance().getCurrentDiscountCode() != null) {
                    Menu menu = new EditDiscountCodeMenu(this).setScanner(this.scanner);
                    ClientController.getInstance().setCurrentMenu(menu);
                    menu.execute();
                } else {
                    ClientController.getInstance().getCurrentMenu().printError("there is no discount code with this code");
                }
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                ClientController.getInstance().setCurrentUser(null);
                System.out.println("You Logged out!!");
                parentMenu.getParentMenu().execute();
            } else {
                System.out.println("Invalid Command");
            }
        }
        back();

    }
}
