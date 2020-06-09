package Controller.Client;

import Controller.Server.ServerController;
import Models.DiscountCode;
import Models.Product.Product;
import Models.UserAccount.Seller;
import Models.UserAccount.UserAccount;
import View2.MainMenu;
import View2.Menu;

import java.util.ArrayList;

public class ClientController {
    private static ClientController clientController;
    private Menu currentMenu;
    private UserAccount currentUser;
    private DiscountCode currentDiscountCode;
    private Product currentProduct;
    private ArrayList<View.Menu> menus= new ArrayList<>();

    public DiscountCode getCurrentDiscountCode() {
        return currentDiscountCode;
    }

    public void addNewMenu(View.Menu menu){
        menus.add(menu);
    }

    public void back() {
        if(menus.size()>1) {
            menus.remove(menus.size()-1);
            menus.get(menus.size()-1).execute();
        }
    }

    public View.Menu getMainMenu() {
        for (View.Menu menu : menus) {
            if(menu instanceof View.MainMenu) {
                return menu;
            }
        }
        return null;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }

    public void setCurrentDiscountCode(DiscountCode currentDiscountCode) {
        this.currentDiscountCode = currentDiscountCode;
    }

    private ClientController() {
    }


    public static ClientController getInstance() {
        if (clientController == null) {
            clientController = new ClientController();
        }
        return clientController;
    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserAccount currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentMenu(Menu menu) {
        this.currentMenu = menu;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void sendMessageToServer(String message) {
        ServerController.getInstance().getMessageFromClient(message);
    }

    public void getMessageFromServer(String message) {
        MessageController.getInstance().processMessage(message);
    }

    public Seller getSeller(){
        if(currentUser.getType().equals("@Seller")) {
            return (Seller) currentUser;
        }
        return null;
    }
}
