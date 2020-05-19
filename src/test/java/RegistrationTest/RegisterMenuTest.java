package RegistrationTest;

import Controller.Client.ClientController;
import Controller.Client.RegisterController;
import Controller.Server.DataBase;
import Models.UserAccount.Manager;
import Models.UserAccount.UserAccount;
import View.UserMenu.RegisterMenu;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterMenuTest {
    @BeforeClass
    void runTheDataBase(){
        DataBase.getInstance().setAllUsersListFromDateBase();
    }

    @Test
    void execute() {
        ClientController.getInstance().setCurrentMenu(new RegisterMenu(null));
        RegisterController.getInstance().createNewUserAccount(new Manager("Sinamaz","sotyt", "Sina","Mazaheri","sinamazaheri@gmail.com","0912214453",12));
        //ve
    }
}