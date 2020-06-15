package Controller.Client;

import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import View.MessageKind;
import com.google.gson.Gson;

public class MessageController {

    private static MessageController messageController;

    private MessageController() {

    }

    public static MessageController getInstance() {
        if (messageController == null) {
            messageController = new MessageController();
        }
        return messageController;
    }

    public String makeMessage(String messageType, String command) {
        return "@" + messageType + "@" + command;

    }

    public void processMessage(String message) {
        if (message.startsWith("@Error@")) {
            message = message.substring(7);
            ClientController.getInstance().getCurrentMenu().showMessage(message, MessageKind.ErrorWithoutBack);
        } else if (message.startsWith("@Successfulrc@")) {
            message = message.substring(14, message.length());
            ClientController.getInstance().setCurrentUser(new Gson().fromJson(message, Customer.class));
            ClientController.getInstance().getCurrentMenu().showMessage("Register Successful", MessageKind.MessageWithBack);
        } else if (message.startsWith("@Successful@")) {
            message = message.substring(12, message.length());
            ClientController.getInstance().getCurrentMenu().showMessage(message, MessageKind.MessageWithBack);
        } else if (message.startsWith("@payed@")) {
            message = message.substring(7, message.length());
            CartController.getInstance().payed(message);
            ClientController.getInstance().getCurrentMenu().showMessage("Successfully purchase", MessageKind.MessageWithBack);
        } else if (message.startsWith("@Login as Customer@")) {
            Gson gson = new Gson();
            message = message.substring(19, message.length());
            Customer customer = gson.fromJson(message, Customer.class);
            ClientController.getInstance().setCurrentUser(customer);
            ClientController.getInstance().getCurrentMenu().showMessage("Login successful", MessageKind.MessageWithBack);
        } else if (message.startsWith("@Login as Manager@")) {
            Gson gson = new Gson();
            message = message.substring(18, message.length());
            Manager manager = gson.fromJson(message, Manager.class);
            ClientController.getInstance().setCurrentUser(manager);
            ClientController.getInstance().getCurrentMenu().showMessage("Login successful", MessageKind.MessageWithBack);
        } else if (message.startsWith("@Login as Seller@")) {
            Gson gson = new Gson();
            message = message.substring(17, message.length());
            Seller seller = gson.fromJson(message, Seller.class);
            ClientController.getInstance().setCurrentUser(seller);
            ClientController.getInstance().getCurrentMenu().showMessage("Login successful", MessageKind.MessageWithBack);
        } else if (message.startsWith("@productCreating@")) {
            ClientController.getInstance().getCurrentMenu().showMessage(message.substring(17, message.length()), MessageKind.MessageWithBack);
        } else if (message.startsWith("@removedSuccessful@")) {
            ClientController.getInstance().getCurrentMenu().showMessage(message.substring(19, message.length()), MessageKind.MessageWithoutBack);
        } else if (message.startsWith("@AllRequests@")) {
            message = message.substring(13, message.length());
            RequestController.getInstance().printAllRequests(message);
        } else if (message.startsWith("@AllDiscountCodes@")) {
            message = message.substring(18, message.length());
            DiscountController.getInstance().printAllDiscountCodes(message);
        } else if (message.startsWith("@allCustomers@")) {
            message = message.substring(14, message.length());
            ManagerController.getInstance().setAllCustomers(message);
        } else if (message.startsWith("@allSellers@")) {
            message = message.substring(12, message.length());
            ManagerController.getInstance().setAllSellers(message);
        } else if (message.startsWith("@allManagers@")) {
            message = message.substring(13);
            ManagerController.getInstance().setAllManagers(message);
        } else if (message.startsWith("@getAllProductsForManager@")) {
            message = message.substring(26);
            ProductController.getInstance().updateAllProducts(message);
        } else if (message.startsWith("@setAllCategories@")) {
            message = message.substring(18);
            CategoryController.getInstance().setAllCategories(message);
        } else if (message.startsWith("@category added@")) {
            ClientController.getInstance().getCurrentMenu().showMessage("Category created", MessageKind.MessageWithBack);
        } else if (message.startsWith("@productRemoved@")) {
            ClientController.getInstance().getCurrentMenu().showMessage("Category removed successfully", MessageKind.MessageWithoutBack);
        } else if (message.startsWith("getAllOffers")) {
            OffsController.getInstance().updateAllOffer(message);
        }
    }


}
