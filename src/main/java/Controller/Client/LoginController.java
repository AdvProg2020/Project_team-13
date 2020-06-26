package Controller.Client;

public class LoginController {

    private static LoginController loginController;

    private LoginController() {
    }

    public static LoginController getInstance() {
        if (loginController == null) {
            loginController = new LoginController();
        }
        return loginController;
    }

    public void login(String userName, String password) {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("Login", userName + "/" + password));
    }


}

