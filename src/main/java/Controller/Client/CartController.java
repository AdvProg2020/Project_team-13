package Controller.Client;

import Models.Product.Cart;
import Models.Product.Product;
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

    public void payed(String json) {
        System.out.println(json);
        ClientController.getInstance().setCurrentUser(new Gson().fromJson(json, Customer.class));
        setCurrentCart(new Cart());
        //   Menu menu= new CustomerMenu(new MainMenu(null).setScanner(ClientController.getInstance().getCurrentMenu().getScanner())).setScanner(ClientController.getInstance().getCurrentMenu().getScanner());
        //   ClientController.getInstance().setCurrentMenu(menu);
        //  menu.execute();
    }

    public void setCurrentCart(Cart currentCart) {
        this.currentCart = currentCart;
    }

    public static CartController getInstance() {
        if (cartController == null) {
            cartController = new CartController();
            cartController.currentCart = new Cart();
        }
        return cartController;
    }

    public void changeCountOfProduct(Product product, boolean amount) {
        if (amount) {
            if (product.getNumberOfAvailableProducts() >= currentCart.getCountOfEachProduct().get(product.getProductId()) + 1)
                currentCart.getCountOfEachProduct().replace(product.getProductId(), currentCart.getCountOfEachProduct().get(product.getProductId()) + 1);
        } else {
            if (currentCart.getCountOfEachProduct().get(product.getProductId()) - 1 > 0) {
                currentCart.getCountOfEachProduct().replace(product.getProductId(), currentCart.getCountOfEachProduct().get(product.getProductId()) - 1);
            } else {
                for (Product product1 : currentCart.getAllproduct()) {
                    if(product.getProductId().equals(product1.getProductId())) {
                        currentCart.getAllproduct().remove(product1);
                        currentCart.getCountOfEachProduct().remove(product.getProductId());
                        break;
                    }
                }
            }
        }
    }


    public double getTotalPriceOfProduct(Product product) {
        for (String s : cartController.getCurrentCart().getCountOfEachProduct().keySet()) {
            if (s.equals(product.getProductId())) {
                return cartController.getCurrentCart().getCountOfEachProduct().get(s) * product.getCostAfterOff();
            }
        }
        return 0;
    }

    public void pay() {
        ClientController.getInstance().sendMessageToServer("@pay@" + new Gson().toJson(currentCart));
    }
}
