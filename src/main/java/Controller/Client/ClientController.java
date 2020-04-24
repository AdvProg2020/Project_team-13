package Controller.Client;

import Controller.Server.ServerController;
import View.Menu;

public class ClientController {
    private static ClientController clientController;
    private Menu currentMenu;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (clientController == null) {
            clientController = new ClientController();
        }
        return clientController;
    }

    public void setCurrentMenu(Menu menu) {
        this.currentMenu=menu;
    }

    public void getMessageFromServer(String message){
       MessageController.getInstance().processMessage(message);
    }
}
