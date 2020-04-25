package View.UserMenu;

import Controller.Client.ClientController;
import View.MainMenu;
import View.Menu;

public class UserMenu extends Menu {
    public UserMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String userMenuOptions="";
        userMenuOptions+="1.Register\n";
        userMenuOptions+="2.Login\n";
        userMenuOptions+="3.help";
        System.out.println(userMenuOptions);

    }

    @Override
    public void execute() {
        String command;
        while (!(command=scanner.nextLine()).equalsIgnoreCase("back")) {
            if(command.equalsIgnoreCase("Register")) {

            }else if(command.equalsIgnoreCase("Login")) {

            }else if (command.equalsIgnoreCase("help")) {
                help();
            }else {
                System.out.println("Invalid Command");
            }
        }
    }

    @Override
    public void showMessage(String message){
        System.out.println(message);
        new RegisterMenu(this).setScanner(this.scanner).execute();

    }

    @Override
    public void printError(String error){
        System.out.println(error);
    }
}
