package View.UserMenu.Customer;

import Controller.Client.CartController;
import Controller.Client.ClientController;
import View.Menu;
import View.UserMenu.Manager.CreateDiscountCodeMenu;

import java.util.regex.Pattern;

public class CustomerInfoForPurchaseMenu extends Menu {
    public CustomerInfoForPurchaseMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {

    }

    @Override
    public void execute() {
        String recevingInfo = "";
        recevingInfo += getAddress() + "\n";
        recevingInfo += getPhoneNumber();
        CartController.getInstance().getCurrentCart().setReceivingInformation(recevingInfo);
        CartController.getInstance().getCurrentCart().setCustomerID(ClientController.getInstance().getCurrentUser().getUsername());
        Menu menu = new CustomerDiscountInfoMenu(this).setScanner(scanner);
        ClientController.getInstance().setCurrentMenu(menu);
        menu.execute();


    }


    private String getAddress() {
        String address;
        System.out.println("Enter your address");
        address = scanner.nextLine().trim();
        return address;
    }

    private String getPhoneNumber() {
        String phoneNumber;
        while (true) {
            System.out.println("Enter PhoneNumber");
            phoneNumber = scanner.nextLine().trim();
            if (phoneNumber.length() == 8) {
                break;
            }  else {
                System.out.println("Please enter a valid phoneNumber.");
            }
        }
        return phoneNumber;
    }

}
