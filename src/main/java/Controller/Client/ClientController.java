package Controller.Client;

import Controller.Server.ServerController;
import Controller.Server.ServerMessageController;
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

    public void sendMessageToServer(String message) {
        ServerController.getIncstance().getMessageFromClient(message);
    }
}
