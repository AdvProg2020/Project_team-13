package Controller.Client;

import Models.UserAccount.Manager;
import Models.UserAccount.UserAccount;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegisterControllerTest {

    @Test
    void createNewUserAccount() {
        UserAccount userAccount=new Manager("karimMajid","123Karim","Sina","Mazaheri","sinamazaheri@gmail.com","09126940943", 12);
//        ClientController clientController= mock(ClientController.class);
        RegisterController registerController=new RegisterController();
        registerController.createNewUserAccount(userAccount);
        Gson gson=new Gson();
//        verify(clientController, times(1)).sendMessageToServer(gson.toJson(userAccount));
    }
}