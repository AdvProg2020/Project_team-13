package test;

import Controller.Client.ClientController;
import Controller.Client.RegisterController;
import Models.UserAccount.Seller;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterControllerTest {
     private RegisterController registerController = RegisterController.getInstance();
     private ClientController clientController = ClientController.getInstance();
    @Test
    void createNewUserAccount() {
        Seller seller = new Seller("Mamad1342", "sinam1386", "Karim", "Mazaheri", "sina@gmail.com", "09122196547", 21.43, "karimian", true);
        registerController.createNewUserAccount(seller);
        Gson gson = new Gson();
        String string = "@Register@" + gson.toJson(seller);
        assertEquals(string, clientController.getTransactionMessage());
    }
}