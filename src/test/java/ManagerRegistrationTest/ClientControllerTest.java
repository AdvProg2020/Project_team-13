package ManagerRegistrationTest;

import Controller.Client.ClientController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ClientControllerTest {

    @Test
    void getInstance() {
        Assert.assertNotNull(ClientController.getInstance());
    }

    @Test
    void sendMessageToServer(String message) {
        ServerControllerTest serverControllerTest=new ServerControllerTest();
        serverControllerTest.getMessageFromClient(message);
    }

    @Test
    void getMessageFromServer() {
    }
}