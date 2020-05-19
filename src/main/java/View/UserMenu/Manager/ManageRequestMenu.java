package View.UserMenu.Manager;

import Controller.Client.ClientController;
import Controller.Client.RegisterController;
import Controller.Client.RequestController;
import View.Menu;
import View.UserMenu.LoginMenu;

public class ManageRequestMenu extends Menu {
    public ManageRequestMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String managerMenuOptions = "";
        managerMenuOptions += "1.details [requestId]\n";
        managerMenuOptions += "2.accept [requestId]\n";
        managerMenuOptions += "3.decline [requestId]\n";
        managerMenuOptions += "4.help\n";
        managerMenuOptions += "5.back\n";
        managerMenuOptions += "6.logout";
        System.out.println(managerMenuOptions);
    }

    @Override
    public void execute() {
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.startsWith("details")) {
                if (command.matches("details @r\\d+")) {
                    RequestController.getInstance().viewRequestDetail(command.split("\\s")[1]);
                } else System.out.println("this ID isn't a requestId");
            } else if (command.startsWith("accept")) {
                if (command.matches("accept @r\\d+")) {
                    RequestController.getInstance().acceptRequest(command.split("\\s")[1]);
                } else System.out.println("this ID isn't a requestId");
            } else if(command.matches("decline @r\\d+")){
                RequestController.getInstance().declineRequest(command.substring(8));
            }else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                ClientController.getInstance().setCurrentUser(null);
                System.out.println("You Logged out!!");
                parentMenu.getParentMenu().execute();
            }else {
                System.err.println("invalid command");
            }
        }
        back();

    }
}
