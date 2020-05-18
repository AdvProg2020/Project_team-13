package ManagerRegistrationTest;

import Controller.Server.UserCenter;
import org.junit.jupiter.api.Test;

class ServerMessageControllerTest {

    @Test
    void getInstance() {

    }

    @Test
    void processMessage(String message) {
        message = message.substring(10);
        UserCenterTest userCenterTest=new UserCenterTest();
        userCenterTest.createNewUserAccount(message);
    }
}