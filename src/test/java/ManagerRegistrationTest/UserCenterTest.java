package ManagerRegistrationTest;

import Controller.Server.UserCenter;
import Models.UserAccount.Manager;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class UserCenterTest {

    @Test
    void getIncstance() {
        Assert.assertNotNull(UserCenter.getIncstance());
    }

    @Test
    void setAllManager() {
    }

    @Test
    void createNewUserAccount(String message) {
        Gson gson=new Gson();
        Manager manager=gson.fromJson(message, Manager.class);
        Assert.assertTrue(message.contains("@Manager"));
    }

    @Test
    void createManagerProfile() {
    }

    @Test
    void findManagerWithUsername() {
    }
}