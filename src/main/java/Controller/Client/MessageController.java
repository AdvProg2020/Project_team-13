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
        if(message.equals("@Error@There is a User With this userName")){

        }else if(message.equals("@Successful@Register Successful")){

        }else if(true){

        }
    }
}
