package View.UserMenu.Customer;

import Controller.Client.CartController;
import Controller.Client.ClientController;
import Models.BuyLog;
import Models.Product.Cart;
import Models.UserAccount.Customer;
import View.MainMenu;
import View.Menu;
import com.google.gson.Gson;

public class PaymentMenu extends Menu {
    public PaymentMenu(Menu parentMenu) {
        super(parentMenu);
    }
    @Override
    public void help() {

    }

    @Override
    public void execute() {
        CartController.getInstance().pay();

    }
}
