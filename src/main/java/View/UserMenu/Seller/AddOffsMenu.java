package View.UserMenu.Seller;

import Controller.Client.ClientController;
import Controller.Client.OffsController;
import Models.OfferStatus;
import Models.Product.Product;
import Models.UserAccount.Seller;
import View.Menu;

import java.util.ArrayList;
import java.util.Date;

public class AddOffsMenu extends Menu {
    private boolean back;

    public AddOffsMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void printError(String error) {
        super.printError(error);
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

    @Override
    public void execute() {
        Seller seller = (Seller) ClientController.getInstance().getCurrentUser();
        if (!seller.hasAnyProduct()) {
            System.out.println("There Is No Product For Offer!!!");
            back();
            return;
        }
        System.out.println("Enter The Off Amount: ");
        double amount = getTheAmount();
        if (backEntered()) {
            back();
            return;
        }
        System.out.println("Enter The Products That Listed Here:\n" + seller.viewAllProducts());
        ArrayList<Product> allProducts = getAllProducts(seller);
        if (backEntered()) {
            back();
            return;
        }
        System.out.println("Enter The Max Discount Amount: ");
        double maxDiscountAmount = getTheAmount();
        if (backEntered()) {
            back();
            return;
        }
        System.out.println("Enter The Start Date: ");
        Date startDate = getTheStartDate();
        if (backEntered()) {
            back();
            return;
        }
        System.out.println("Enter The End Date: ");
        Date endDate = getTheEndDate(startDate);
        if (backEntered()) {
            back();
            return;
        }
        OffsController.getInstance().addOff();//need to be complete
        System.out.println("The Offer Registered for Manager's Confirmation.");
        back();
    }

    private double getTheAmount() {
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("\\d+(\\.\\d+)?")) {
                return Double.parseDouble(command);
            } else {
                System.out.println("invalid command");
            }
        }
        back = true;
        return 0;
    }

    @Override
    public void help() {

    }

    private ArrayList<Product> getAllProducts(Seller seller) {
        String command;
        ArrayList<Product> products = new ArrayList<>();
        while (true) {
            command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("back")) {
                back = true;
                return null;
            } else if (command.equalsIgnoreCase("finish")) {
                if (products.isEmpty()) {
                    System.out.println("You Must Choose A Product");
                } else {
                    return products;
                }
            } else if (command.matches("@p\\w+")) {
                if (!seller.productExists(command)) {
                    System.out.println("There Is No Product With This Id In That List. ");
                } else if (seller.productExistsInOtherOffer(command)) {
                    System.out.println("The Product Already Exists in Other Offer. ");
                } else {
                    products.add(seller.getProductByID(command));
                }
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private Date getTheStartDate() {
        String command;
        Date date=new Date();
        Date startDate;
        while(!(command=scanner.nextLine().trim()).equalsIgnoreCase("back")){
            if(command.matches("[1-9]\\d{3}/([1-9]|(1[0-2]))/([1-9]|([1-2][0-9])|30)")){
                startDate=new Date(command);
                if(startDate.before(date)){
                    System.out.println("The Time Is Not Acceptable");
                }else{
                    return startDate;
                }
            }else {
                System.out.println("invalid command");
            }
        }
        back = true;
        return null;

    }

    private Date getTheEndDate(Date startDate) {
        String command;
        Date date=new Date();
        Date endDate;
        while(!(command=scanner.nextLine().trim()).equalsIgnoreCase("back")){
            if(command.matches("[1-3]\\d{3}/([1-9]|(1[0-2]))/([1-9]|([1-2][0-9])|30)")){
               endDate=new Date(command);
               if(endDate.before(startDate)){
                   System.out.println("The Time Is Not Acceptable");
               }else{
                   return endDate;
               }
            }else {
                System.out.println("invalid command");
            }
        }
        back=true;
        return null;
    }
    private boolean backEntered() {
        return back;
    }
}
