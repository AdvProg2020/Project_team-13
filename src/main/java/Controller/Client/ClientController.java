package Controller.Client;

import Controller.Server.ServerController;
import Models.DiscountCode;
import Models.Product.Product;
import Models.UserAccount.Seller;
import Models.UserAccount.UserAccount;
import View.MainMenu;
import View.Menu;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;

public class ClientController {
    private static ClientController clientController;
    private View.Menu currentMenu;
    private UserAccount currentUser;
    private DiscountCode currentDiscountCode;
    private Product currentProduct;
    private ArrayList<View.Menu> menus = new ArrayList<>();
    private MediaPlayer mediaPlayer;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public DiscountCode getCurrentDiscountCode() {
        return currentDiscountCode;
    }

    public void addNewMenu(View.Menu menu) {
        menus.add(menu);
    }

    public void resetMenuArray() {
        Menu menu = menus.get(0);
        menus.clear();
        new MainMenu(menu.getStage()).execute();
    }

    public void back() {
        if (menus.size() > 1) {
            System.out.println(menus.size());
            menus.remove(menus.size() - 1);
            menus.get(menus.size() - 1).execute();
        }
    }

    public View.Menu getMainMenu() {
        return new MainMenu(menus.get(0).getStage());
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


    public Menu getCurrentMenu() {
        return menus.get(menus.size() - 1);
    }

    public void sendMessageToServer(String message) {
        ServerController.getInstance().getMessageFromClient(message);
    }

    public void getMessageFromServer(String message) {
        MessageController.getInstance().processMessage(message);
    }

    public Seller getSeller() {
        if (currentUser.getType().equals("@Seller")) {
            return (Seller) currentUser;
        }
        return null;
    }
}
