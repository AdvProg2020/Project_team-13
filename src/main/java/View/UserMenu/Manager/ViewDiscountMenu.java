package View.UserMenu.Manager;

import Controller.Client.DiscountController;
import View.Menu;

public class ViewDiscountMenu extends Menu {
    public ViewDiscountMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String managerMenuOptions = "";
        managerMenuOptions += "1.view discount code [code]\n";
        System.out.println(managerMenuOptions);
    }

    @Override
    public void execute() {
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.startsWith("view discount code")) {
                DiscountController.getInstance().viewDiscountCode(command.split("\\s")[3]);
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else {
                System.out.println("Invalid Command");
            }
        }
        back();

    }
}
