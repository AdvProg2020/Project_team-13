package Controller.Server;

import Models.UserAccount.Customer;
import com.google.gson.Gson;

public class ServerMessageController {
    private static ServerMessageController serverMessageController;


    private ServerMessageController() {

    }

    public static ServerMessageController getInstance() {
        if (serverMessageController == null) {
            serverMessageController = new ServerMessageController();
        }
        return serverMessageController;
    }

    void processMessage(String message) {
        if (message.startsWith("@Register@")) {
            message = message.substring(10, message.length());
            UserCenter.getIncstance().createNewUserAccount(message);
        } else if (message.startsWith("@Login@")) {
            message = message.substring(7, message.length());
            String[] split = message.split("/");
            UserCenter.getIncstance().login(split[0], split[1]);
        } else if (message.equals("getAllRequests")) {
            ServerController.getIncstance().sendMessageToClient("@AllRequests@" + new Gson().toJson(RequestCenter.getIncstance().getAllRequests()));
        } else if (message.startsWith("@acceptRequest@")) {
            message = message.substring(15, message.length());
            RequestCenter.getIncstance().acceptRequest(message);
        }
    }
}
