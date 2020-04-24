package View.UserMenu;

import View.Menu;

import java.util.Scanner;

public class RegisterMenu extends Menu {
    public RegisterMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {

    }

    @Override
    public void execute() {
        System.out.println("Enter UserType");
        String userType=scanner.nextLine().trim();
        if(userType.equalsIgnoreCase("Manager")) {

        }

    }
}
