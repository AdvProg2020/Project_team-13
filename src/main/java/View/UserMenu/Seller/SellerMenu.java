package View.UserMenu.Seller;

import Controller.Client.ClientController;
import Controller.Client.ProductController;
import Models.Product.Product;
import Models.UserAccount.Seller;
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
        if (!ClientController.getInstance().getSeller().isAccepted()) {
            System.out.println("Your registration application has not been approved by the administrator.");
            while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
                if (command.equals("view personal info")) {
                    System.out.println("You don't have access!");
                } else if (command.equals("add product")) {
                    System.out.println("You don't have access!");
                } else if (command.equals("help")) {
                    System.out.println("you can just get back!!");
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

    }

    private void addProduct() {
        ArrayList<String> fieldsOfProduct = new ArrayList<>();
        fieldsOfProduct.add(getName("Company Name"));
        fieldsOfProduct.add(getName("Product Name"));
        System.out.println("Enter Products  description");
        String s = "", description = "";
        while (!(s = scanner.nextLine().trim()).equalsIgnoreCase("finish")) {
            description += s;
            description += "\\n";
        }
        fieldsOfProduct.add(description);
        fieldsOfProduct.add(getNumber("Products Cost"));
        fieldsOfProduct.add(getNumber("Available number of product"));
        String category = getName("Products Category");
        ArrayList<String> categoryFeatures = new ArrayList<>();
        while ((s = scanner.nextLine().trim()).equalsIgnoreCase("finish")) {
            if (Pattern.matches("\\w+/\\w+", s)) {
                categoryFeatures.add(s);
            } else {
                System.out.println("Enter In Correct Form");
            }
        }
        ProductController.getInstance().addProduct(fieldsOfProduct, categoryFeatures, category);
    }

    private String getName(String nameKind) {
        String name;
        while (true) {
            System.out.println("Enter " + nameKind);
            name = scanner.nextLine().trim();
            if (Pattern.matches("(\\w+ )*\\w+", name)) {
                break;
            } else {
                System.out.println(nameKind + " is Invalid");
            }
        }
        return name;
    }

    private String getNumber(String numberKind) {
        String number;
        while (true) {
            System.out.println("Enter " + numberKind);
            number = scanner.nextLine().trim();
            if (Pattern.matches("\\d+", number)) {
                break;
            } else {
                System.out.println(numberKind + "is Invalid");
            }
        }
        return number;
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
        System.out.println(message);

    }

    @Override
    public void printError(String error) {
        System.out.println(error);

    }

}
