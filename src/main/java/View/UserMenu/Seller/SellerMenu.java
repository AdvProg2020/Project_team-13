package View.UserMenu.Seller;

import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Controller.Client.ProductController;
import Models.Product.Category;
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
        System.out.println(ClientController.getInstance().getCurrentUser().viewPersonalInfo());
        if (!ClientController.getInstance().getSeller().isAccepted()) {
            System.out.println("Your registration application has not been approved by the administrator.");
            while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
                if (command.equals("view personal info")) {
                    System.out.println("You don't have access!");
                } else if (command.equals("add product")) {
                    System.out.println("You don't have access!");
                } else if (command.equals("help")) {
                    System.out.println("you can just get back!!");
                } else if (command.equalsIgnoreCase("logout")) {
                    ClientController.getInstance().setCurrentUser(null);
                    System.out.println("You Logged out!!");
                    parentMenu.execute();
                } else {
                    System.out.println("Invalid command");
                }
            }
            back();
        } else {
            while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
                if (command.equals("view personal info")) {
                    System.out.println(ClientController.getInstance().getCurrentUser().viewPersonalInfo());
                } else if (command.equals("manage products")) {
                    Menu menu = new ManageProductMenu(this).setScanner(scanner);
                    ClientController.getInstance().setCurrentMenu(menu);
                    menu.execute();
                } else if (command.equals("help")) {
                    help();
                } else if (command.equalsIgnoreCase("logout")) {
                    ClientController.getInstance().setCurrentUser(null);
                    System.out.println("You Logged out!!");
                    parentMenu.execute();
                } else {
                    System.out.println("Invalid command");
                }
            }
            back();
        }

    }

    @Override
    public void help() {
        String sellerMenuOptions = "";
        sellerMenuOptions += "view personal info\n";
        sellerMenuOptions += "add product\n";
        sellerMenuOptions += "LogOut\n";
        sellerMenuOptions += "manage products";
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
