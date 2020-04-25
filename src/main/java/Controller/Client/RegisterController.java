package Controller.Client;

import Models.UserAccount.Seller;
import Models.UserAccount.UserAccount;
import com.google.gson.Gson;

public class RegisterController {
    private static RegisterController registerController;

    private RegisterController() {
    }

    public static RegisterController getInstance() {
        if(registerController==null){
            registerController=new RegisterController();
        }
        return registerController;
    }

    public void createNewUserAccount(UserAccount userAccount){
      if(userAccount.getType().equalsIgnoreCase("@Seller")) {
          userAccount=(Seller) userAccount;
      }
            String message="@Register" + new Gson().toJson(userAccount);
            ClientController.getInstance().sendMessageToServer(message);
    }

}
