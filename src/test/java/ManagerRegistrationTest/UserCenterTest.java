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
    void createManagerProfile() {
    }

    @Test
    void findManagerWithUsername() {
    }

    @Test
    void updateAllSellers() {
        Assert.assertNotNull(UserCenter.getIncstance().getAllSeller().getClass());
    }
}