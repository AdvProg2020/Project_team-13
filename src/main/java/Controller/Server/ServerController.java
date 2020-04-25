package Controller.Server;

public class ServerController {
    public static ServerController serverController;
    private ServerController() {

    }

    public static ServerController getIncstance() {
        if (serverController == null){
            serverController = new ServerController();
        }
        return serverController;
    }
    public void getMessageFromClient(String message){
        ServerMessageController.getIncstance().processMessage(message);
    }
}
