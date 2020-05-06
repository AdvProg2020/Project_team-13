package Controller.Server;

import Controller.Client.ClientController;

import java.io.IOException;

public class ServerController {
    private static ServerController serverController;

    private ServerController() {

    }

    public void runServer() {
        getAllUsersForStart();
    }

    public void getAllUsersForStart() {
        DataBase.getIncstance().setAllUsersListFromDateBase();
        DataBase.getIncstance().setAllRequestsListFromDateBase();
        DataBase.getIncstance().setLastRequestId();
    }

    public static ServerController getIncstance() {
        if (serverController == null) {
            serverController = new ServerController();
        }
        return serverController;
    }

    public void getMessageFromClient(String message)  {
        ServerMessageController.getInstance().processMessage(message);
    }

    public void sendMessageToClient(String message) {
        ClientController.getInstance().getMessageFromServer(message);
    }
}
