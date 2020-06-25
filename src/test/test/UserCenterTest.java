package test;

import Controller.Server.UserCenter;
import Models.UserAccount.Manager;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCenterTest {
    private UserCenter userCenter = UserCenter.getIncstance();
    @Test
    void createNewUserAccount() {
        int size = userCenter.getAllManager().size();
        Gson gson = new Gson();
        String string = gson.toJson(new Manager("ali123", "12345", "ali", "majidi", "majid@gmail.com", "09122197321", 21));
        assertEquals(0, size);
        userCenter.createNewUserAccount(string);
        assertEquals(size+1, userCenter.getAllManager().size());
    }
}