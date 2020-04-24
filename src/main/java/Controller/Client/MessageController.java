package Controller.Client;

public class MessageController {
    private static MessageController messageController;

    public MessageController() {
    }

    public void processMessage(String message){

    }

    public static MessageController getInstance(){
       if(messageController==null){
           messageController= new MessageController();
       }
       return messageController;
    }
}
