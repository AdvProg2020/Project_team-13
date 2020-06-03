package View2.UserMenu.Customer;

import Controller.Client.CartController;
import View2.Menu;

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
