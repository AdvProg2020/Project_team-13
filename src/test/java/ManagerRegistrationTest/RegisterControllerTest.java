package ManagerRegistrationTest;
import Controller.Client.RegisterController;
import Models.UserAccount.Manager;
import Models.UserAccount.UserAccount;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class RegisterControllerTest {
    private RegisterController registerController=new RegisterController();

    @Test
    void createNewUserAccount() {
        /// must Database Added.
        UserAccount userAccount=new Manager("WolfOfWallStreet", "12234","majid","kori","majid.kori@gmail.com", "09182831", 12);
        ClientControllerTest clientControllerTest=new ClientControllerTest();
        String message="@Register@"+new Gson().toJson(userAccount);
        clientControllerTest.sendMessageToServer(message);
    }
}