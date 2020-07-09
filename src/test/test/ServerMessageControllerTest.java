package test;
import Controller.Server.ServerMessageController;
import Models.UserAccount.Manager;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerMessageControllerTest {
    private ServerMessageController serverMessageController = ServerMessageController.getInstance();

    @Test
    void processMessage() {
        Gson gson = new Gson();
        String string = gson.toJson(new Manager("ali123", "12345", "ali", "majidi", "majid@gmail.com", "09122197321", 21));
//        serverMessageController.processMessage("@Register@" + string);
        assertEquals(string, serverMessageController.getMessage());
    }
}