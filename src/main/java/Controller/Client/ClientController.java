package Controller.Client;

import Controller.Server.ServerController;
import Models.UserAccount.Seller;
import Models.UserAccount.UserAccount;
import View.Menu;

import java.io.IOException;

public class ClientController {
    private static ClientController clientController;
    private Menu currentMenu;
    private UserAccount currentUser;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (clientController == null) {
            clientController = new ClientController();
        }
        return clientController;
    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserAccount currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentMenu(Menu menu) {
        this.currentMenu = menu;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void sendMessageToServer(String message) {
        ServerController.getIncstance().getMessageFromClient(message);
    }

    public void getMessageFromServer(String message) {
        MessageController.getInstance().processMessage(message);
    }

    public Seller getSeller(){
        if(currentUser.getType().equals("@Seller")) {
            return (Seller) currentUser;
        }
        return null;
    }
}
