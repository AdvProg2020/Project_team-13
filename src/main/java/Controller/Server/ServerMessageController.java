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
            boolean productCreated = false;
            for (Category category : CategoryCenter.getIncstance().getAllCategories()) {
                if (category.getName().equals(product.getProductsCategory())) {
                    product.setProductId(ProductCenter.getInstance().getProductIdForCreateInProduct());
                    product.getSeller().addProduct(product);
                    productCreated = true;
                    RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("AddProduct", message));
                    ServerController.getIncstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("productCreating", "ProductCreating Request has been sent."));
                    break;

                }
            }
            if (!productCreated) {
                ServerController.getIncstance().sendMessageToClient(MessageController.getInstance().makeMessage("Error", "There is no category with this name"));
            }
        }
    }
}
