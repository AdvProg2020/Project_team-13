package Controller.Client;

import Models.Product.Cart;
import Models.UserAccount.Customer;
import View2.MainMenu;
import View2.Menu;
import View2.UserMenu.Customer.CustomerMenu;
import com.google.gson.Gson;

public class CartController {
    private Cart currentCart;
    private static CartController cartController;

    private CartController() {
    }

    public Cart getCurrentCart() {
        return currentCart;
    }
    public void payed(String json){
        System.out.println(json);
        ClientController.getInstance().setCurrentUser(new Gson().fromJson(json, Customer.class));
        setCurrentCart(new Cart());
        Menu menu= new CustomerMenu(new MainMenu(null).setScanner(ClientController.getInstance().getCurrentMenu().getScanner())).setScanner(ClientController.getInstance().getCurrentMenu().getScanner());
        ClientController.getInstance().setCurrentMenu(menu);
        menu.execute();
    }
    public void setCurrentCart(Cart currentCart) {
        this.currentCart = currentCart;
    }

    public static CartController getInstance() {
        if (cartController == null) {
            cartController = new CartController();
        }
        return cartController;
    }
    public void pay(){
        ClientController.getInstance().sendMessageToServer("@pay@"+new Gson().toJson(currentCart));
    }
}
