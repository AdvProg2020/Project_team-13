package Controller.Server;

public class ServerMessageController {
    private static ServerMessageController serverMessageController;
    private ServerMessageController(){

    }
    public static ServerMessageController getIncstance() {
        if (serverMessageController == null){
            serverMessageController = new ServerMessageController();
        }
        return serverMessageController;
    }
    void processMessage(String message){

    }
}
