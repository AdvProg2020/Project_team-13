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
    void getMessageFromServer() {
    }
}