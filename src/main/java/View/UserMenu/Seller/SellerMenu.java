package View.UserMenu.Seller;

import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Controller.Client.ProductController;
import Controller.Client.RequestController;
import Models.Product.Category;
import Models.UserAccount.Seller;
import View.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.regex.Pattern;

public class SellerMenu extends Menu {
    public SellerMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void execute() {
        String command;
        Seller seller=(Seller)ClientController.getInstance().getCurrentUser();
        System.out.println(seller.viewPersonalInfo());
        boolean isAccepted = ClientController.getInstance().getSeller().isAccepted();
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.equals("view personal info") && isAccepted) {
                Menu menu = new ViewAndEditInformationForSeller(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            } else if (command.equals("manage products")&& isAccepted) {
                RequestController.getInstance().getAllRequestsFromServer();
                Menu menu = new ManageProductMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }else if (command.equals("show categories")&& isAccepted) {
                CategoryController.getInstance().printAllCategories();
            }else if(command.equals("view offs")) {
                Menu menu=new ViewOffsMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }else if(command.equals("view sales history")) {
                showMessage(((Seller)ClientController.getInstance().getCurrentUser()).viewSalesHistory());
            }else if (command.equals("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                ClientController.getInstance().setCurrentUser(null);
                System.out.println("You Logged out!!");
                parentMenu.execute();
            } else if (command.equalsIgnoreCase("view balance")){
                showMessage(String.valueOf(seller.getCredit()));
            }else  {
                if(isAccepted) {
                    System.err.println("Invalid command");
                }else
                    System.out.println("you should wait for accept your register from a manager");
            }
        }
        back();


    }

    @Override
    public void help() {
        String sellerMenuOptions = "";
        sellerMenuOptions += "1.View Personal Info\n";
        sellerMenuOptions += "2.Show Categories\n";
        sellerMenuOptions += "3.manage products\n";
        sellerMenuOptions += "4.view offs\n";
        sellerMenuOptions += "5.view balance\n";
        sellerMenuOptions += "6.LogOut\n";
        System.out.println(sellerMenuOptions);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);

    }

    @Override
    public void printError(String error) {
        System.out.println(error);

    }

}
