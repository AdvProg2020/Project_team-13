package ManagerRegistrationTest;

import Controller.Server.ServerController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ServerControllerTest {
    private ServerController serverController=new ServerController();
    @Test
    void runServer() {

    }

    @Test
    void getAllInformationForStart() {

    }

    @Test
    void getInstance() {
        Assert.assertNotNull(ServerController.getInstance());
    }



    @Test
    void sendMessageToClient() {
    }
}