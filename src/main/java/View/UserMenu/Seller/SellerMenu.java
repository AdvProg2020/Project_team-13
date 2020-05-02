package View.UserMenu.Seller;

import Controller.Client.ClientController;
import Controller.Client.ProductController;
import Models.Product.Product;
import View.Menu;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SellerMenu extends Menu {
    public SellerMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void execute() {
        String command;
        System.out.println(ClientController.getInstance().getCurrentUser().viewPersonalInfo());
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.equals("view personal info")) {
                System.out.println("Big Like");
            } else if (command.equals("add product")) {
                addProduct();
            } else if (command.equals("help")) {
                help();
            } else {
                System.out.println("Invalid command");
            }
        }
        back();
    }

    private void addProduct() {
        ArrayList<String> fieldsOfProduct = new ArrayList<>();
        System.out.println("Enter Company name");
        fieldsOfProduct.add(scanner.nextLine().trim());
        System.out.println("Enter Products Name");
        fieldsOfProduct.add(scanner.nextLine().trim());
        System.out.println("Enter Products  describtion");
        String s = "", discribtion = "";
        while ((s = scanner.nextLine().trim()).equalsIgnoreCase("finish")) {
            discribtion += s;
            discribtion += "\\n";
        }
        fieldsOfProduct.add(discribtion);
        System.out.println("Enter Products Cost");
        fieldsOfProduct.add(scanner.nextLine().trim());
        System.out.println("Enter Available number of product");
        fieldsOfProduct.add(scanner.nextLine().trim());
        System.out.println("Enter Products Category");
        String category=scanner.nextLine().trim();
        ArrayList<String> categoryFeatures=new ArrayList<>();
        while ((s = scanner.nextLine().trim()).equalsIgnoreCase("finish")) {
            if(Pattern.matches("\\w+/.+",s)) {
                categoryFeatures.add(s);
            } else {
                System.out.println("Enter In Correct Form");
            }
        }
        ProductController.getInstance().addProduct(fieldsOfProduct,categoryFeatures,category);
    }

    @Override
    public void help() {
        String sellerMenuOptions = "";
        sellerMenuOptions += "view personal info\n";
        sellerMenuOptions += "add product\n";
        sellerMenuOptions += "view Sales history\n";
        sellerMenuOptions += "view Sales History\n";
        sellerMenuOptions += "view Sales History\n";
        sellerMenuOptions += "view Sales History\n";
        sellerMenuOptions += "view Sales History\n";
        sellerMenuOptions += "view Sales History\n";
        sellerMenuOptions += "view Sales History\n";
        sellerMenuOptions += "view Sales History\n";
        System.out.println(sellerMenuOptions);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void printError(String error) {

    }

}
