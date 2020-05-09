package View.UserMenu.Manager;

import Controller.Client.ClientController;
import Controller.Client.RequestController;
import View.Menu;

import java.util.regex.Pattern;

public class ManageCategoryMenu extends Menu {

    public ManageCategoryMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String manageCategoryMenu = "";
        manageCategoryMenu += "1.Add [category]\n";
        manageCategoryMenu += "2.Edit [category]\n";
        manageCategoryMenu += "3.Remove [category]\n";
        manageCategoryMenu += "4.Help\n";
        manageCategoryMenu += "5.Back";
        System.out.println(manageCategoryMenu);
    }

    @Override
    public void execute() {
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (Pattern.matches("add \\w+", command)) {
                Menu menu = new AddCategoryMenu(this).setScanner(this.scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (Pattern.matches("remove \\w+", command)) {

            } else if (Pattern.matches("edit \\w+", command)) {

            } else if (command.equalsIgnoreCase("help")) {
            }
        }
        back();
    }
}
