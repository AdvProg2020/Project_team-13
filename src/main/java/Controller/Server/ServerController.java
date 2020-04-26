package Controller.Server;

import Controller.Client.ClientController;

public class ServerController {
    public static ServerController serverController;
    private ServerController() {

    }

    public void runServer(){
        getAllUsersForStart();
    }
    public void getAllUsersForStart(){
        DataBase.getIncstance().setAllUsersListFromDateBase();
    }
    public static ServerController getIncstance() {
        if (serverController == null){
            serverController = new ServerController();
        }
        return serverController;
    }
    public void getMessageFromClient(String message){
        ServerMessageController.getInstance().processMessage(message);
    }
    public void sendMessageToClient(String message){
        ClientController.getInstance().getMessageFromServer(message);
    }
}
