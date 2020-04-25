package Controller.Server;

import Models.UserAccount.Customer;
import com.google.gson.Gson;

public class ServerMessageController {
    private static ServerMessageController serverMessageController;

    private ServerMessageController() {

    }

    public static ServerMessageController getIncstance() {
        if (serverMessageController == null) {
            serverMessageController = new ServerMessageController();
        }
        return serverMessageController;
    }

    void processMessage(String message) {
        if (message.startsWith("@checkIsThereAnyUsername@")) {
            message = message.substring(25, message.length());
            UserCenter.getIncstance().createNewUserAccount(message);
        }
    }
}
