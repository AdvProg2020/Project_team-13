package View2.UserMenu.Customer;

import Controller.Client.CartController;
import Controller.Client.ClientController;
import Models.DiscountCode;
import Models.UserAccount.Customer;
import View2.Menu;

public class CustomerDiscountInfoMenu extends Menu {
    public CustomerDiscountInfoMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {

    }

    @Override
    public void execute() {
        while (true) {
            System.out.println("Do you have any discount code for this payment? (YES/no)");
            String haveDiscount = scanner.next();
            if (haveDiscount.equals("YES")) {
                CartController.getInstance().getCurrentCart().setDiscountCode(getDiscountCode());
                Menu menu = new PaymentMenu(this).setScanner(scanner);
          //      ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (haveDiscount.equals("no")) {
                ClientController.getInstance().setCurrentDiscountCode(null);
                Menu menu = new PaymentMenu(this).setScanner(scanner);
        //        ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }else {
                printError("Invaild command");
            }
        }


    }


    private DiscountCode getDiscountCode() {
        String discountCode = "";
        while (true) {
            System.out.println("Enter your discount code or type SKIP");
            discountCode = scanner.nextLine().trim();
            if (discountCode.equals("SKIP")) {
                return null;
            } else if (!discountCode.equals("")) {
                DiscountCode discount = ((Customer) ClientController.getInstance().getCurrentUser()).findDiscountCodeWithCode(discountCode);
                if (discount == null) {
                    printError("you don't have any discount code with this code");
                } else {
                    return discount;
                }
            }
        }
    }

}
