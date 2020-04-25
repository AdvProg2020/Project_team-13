package Controller.Client;

import Models.UserAccount.UserAccount;

public class RegisterController {
    private static RegisterController registerController;

    public RegisterController() {
    }

    public static RegisterController getInstance() {
        if(registerController==null){
            registerController=new RegisterController();
        }
        return registerController;
    }

    public void createNewUserAccount(String[] fields){

    }

}
