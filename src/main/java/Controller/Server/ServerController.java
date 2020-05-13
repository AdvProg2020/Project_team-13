package Controller.Server;

import Controller.Client.ClientController;

public class ServerController {
    private static ServerController serverController;

    private ServerController() {

    }

    public void runServer() {
        getAllInformationForStart();
    }

    public void getAllInformationForStart() {
        DataBase.getInstance().setAllUsersListFromDateBase();
        DataBase.getInstance().setAllRequestsListFromDateBase();
        DataBase.getInstance().setAllDiscountCodesListFromDateBase();
        DataBase.getInstance().setLastRequestId();
        DataBase.getInstance().setLastDiscountCodeId();
        DataBase.getInstance().setAllProductsFormDataBase();
        DataBase.getInstance().setAllCategoriesFormDataBase();
    }

    public static ServerController getInstance() {
        if (serverController == null) {
            serverController = new ServerController();
        }
        return serverController;
    }

    public void getMessageFromClient(String message)  {
        ServerMessageController.getInstance().processMessage(message);
    }

    public void sendMessageToClient(String message) {
        ClientController.getInstance().getMessageFromServer(message);
    }
}
