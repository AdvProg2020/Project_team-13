package test;

import Controller.Client.ClientController;
import Controller.Client.RegisterController;
import Models.UserAccount.Manager;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterControllerTest {
     private RegisterController registerController = RegisterController.getInstance();
     private ClientController clientController = ClientController.getInstance();
    @Test
    void createNewUserAccount() {
        Manager manager = new Manager("Mamad1342", "sinam1386", "Karim", "Mazaheri", "sina@gmail.com", "09122196547", 21.43);
        registerController.createNewUserAccount(manager);
        Gson gson = new Gson();
        String string = "@Register@" + gson.toJson(manager);
        assertEquals(string, clientController.getTransactionMessage());
    }
}