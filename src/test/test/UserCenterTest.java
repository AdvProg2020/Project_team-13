package test;
import Controller.Server.DataBase;
import Controller.Server.UserCenter;
import Models.UserAccount.Manager;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UserCenterTest {
    private UserCenter userCenter = UserCenter.getIncstance();

    @BeforeEach
    void setTheDataBase() {
        DataBase.getInstance().setAllUsersListFromDateBase();
    }



    @Test
    void createNewUserAccount() {
        int size = userCenter.getAllManager().size();
        Gson gson = new Gson();
        String string = gson.toJson(new Manager("karimi12", "12345", "ali", "majidi", "majid@gmail.com", "09122197321", 21));
        assertEquals(0, size);
        userCenter.createNewUserAccount(string);
        assertEquals(size+1, userCenter.getAllManager().size());
    }




    @AfterEach
    void createManagerProfile(){
        int size = userCenter.getAllManager().size();
        Gson gson = new Gson();
        String string = gson.toJson(new Manager("asghar1324", "12345", "ali", "majidi", "majid@gmail.com", "09122197321", 21));
        String anotherManager = gson.toJson(new Manager("karimi12", "12345", "ali", "majidi", "majid@gmail.com", "09122197321", 21));
        userCenter.createManagerProfile(string);
        assertEquals(size+1, userCenter.getAllManager().size());
        userCenter.createManagerProfile(anotherManager);
        assertEquals(size + 1, userCenter.getAllManager().size());
    }
}