package ManagerRegistrationTest;

import Controller.Client.MessageController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MessageControllerTest {

    @Test
    void getInstance() {
        Assert.assertNotNull(MessageController.getInstance());
    }

    @Test
    void processMessage() {

    }
}