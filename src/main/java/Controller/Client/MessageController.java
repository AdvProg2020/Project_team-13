package Controller.Client;

import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import View.UserMenu.Customer.CustomerMenu;
import View.UserMenu.Manager.ManagerMenu;
import View.UserMenu.Seller.SellerMenu;
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

        if(message.startsWith("@Error@")){
           message=message.substring(7, message.length());
           ClientController.getInstance().getCurrentMenu().printError(message);
        }else if(message.startsWith("@Successful@")){
           message=message.substring(12, message.length());
           ClientController.getInstance().getCurrentMenu().showMessage(message);
        }else if(message.startsWith("@Login as Customer@")){
            Gson gson=new Gson();
            message=message.substring(19, message.length());
            Customer customer=gson.fromJson(message, Customer.class);
            ClientController.getInstance().setCurrentUser(customer);
            ClientController.getInstance().getCurrentMenu().showMessage("login Successful");
            ClientController.getInstance().setCurrentUser(new );
        }else if(message.startsWith("@Login as Manager@")){
            Gson gson=new Gson();
            message=message.substring(18, message.length());
            Manager manager=gson.fromJson(message, Manager.class);
            ClientController.getInstance().setCurrentUser(manager);
            ClientController.getInstance().getCurrentMenu().showMessage("login Successful");
            ClientController.getInstance().setCurrentUser(new );
        }else if(message.startsWith("@Login as Seller@")){
            Gson gson=new Gson();
            message=message.substring(17, message.length());
            Seller seller=gson.fromJson(message, Seller.class);
            ClientController.getInstance().setCurrentUser(seller);
            ClientController.getInstance().getCurrentMenu().showMessage("login Successful");
            ClientController.getInstance().setCurrentUser(new SellerMenu().execute());
        }
    }
}
