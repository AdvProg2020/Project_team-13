package View.UserMenu.Customer;

import Controller.Client.CartController;
import Controller.Client.ClientController;
import View.MainMenu;
import View.Menu;

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

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
        Menu menu= new CustomerMenu(new MainMenu(null));
        ClientController.getInstance().setCurrentMenu(menu);
        menu.execute();
    }
}
