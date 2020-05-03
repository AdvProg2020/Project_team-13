package Controller.Server;

import Controller.Client.ClientController;
import Controller.Client.MessageController;
import Models.Product.Category;
import Models.Product.Product;
import Models.UserAccount.Customer;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.PublicKey;

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

    public String makeMessage(String type, String command) {
        return ("@" + type + "@" + command);
    }

    void processMessage(String message) {
        if (message.startsWith("@Register@")) {
            message = message.substring(10, message.length());
            UserCenter.getIncstance().createNewUserAccount(message);
        } else if (message.startsWith("@Login@")) {
            message = message.substring(7, message.length());
            String[] split = message.split("/");
            UserCenter.getIncstance().login(split[0], split[1]);
        } else if (message.startsWith("@AddProduct@")) {
            message = message.substring(12, message.length());
            Gson gson = new Gson();
            Product product = gson.fromJson(message, Product.class);
            ProductCenter.getInstance().createProductRequest(product,message);
        }
    }
}
