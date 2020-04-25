package Controller.Client;

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
        if(message.equals("@Error@")){
           message=message.substring(7, message.length());
           ClientController.getInstance().getCurrentMenu().printError(message);
        }else if(message.equals("@Successful@")){
           message=message.substring(12, message.length());
           ClientController.getInstance().getCurrentMenu().showMessage(message);
        }
    }
}
