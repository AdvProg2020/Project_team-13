package View.UserMenu;

import Controller.Client.ClientController;
import Controller.Client.LoginController;
import Models.UserAccount.Manager;
import View.Menu;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu extends Menu {

    public LoginMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String loginMenuOptions = "";
        loginMenuOptions += "1.Login [username]\n";
        loginMenuOptions += "2.help\n";
        loginMenuOptions += "3.back";
        System.out.println(loginMenuOptions);
    }

    @Override
    public void execute() {
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (Pattern.matches("(l|L)ogin (\\w+)", command)) {
                String userName, password;
                Pattern pattern = Pattern.compile("(l|L)ogin (\\w+)");
                Matcher matcher = pattern.matcher(command);
                matcher.find();
                userName = matcher.group(2);
                System.out.println("Enter password");
                password = scanner.nextLine().trim();
                LoginController.getInstance().login(userName, password);
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else {
                System.out.println("command is invalid");
            }
        }
        back();
    }

    @Override
    public void printError(String error) {
        super.printError(error);
        System.out.println("Please Enter Login [userName] and passwrod again.");
        this.execute();
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
        ClientController.getInstance().setCurrentMenu(parentMenu);
        parentMenu.execute();
    }
}
