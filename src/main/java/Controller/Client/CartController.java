package Controller.Client;

import Models.Product.Cart;
import com.google.gson.Gson;

public class CartController {
    private Cart currentCart;
    private static CartController cartController;

    private CartController() {
    }

    public Cart getCurrentCart() {
        return currentCart;
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
