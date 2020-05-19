package View.UserMenu.Seller;

import Controller.Client.ClientController;
import Controller.Client.OffsController;
import Controller.Client.ProductController;
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
        if (seller.AnyOffer()) {
            System.out.println("There Is No Product For Offer!!!");
            back();
        }
        double amount = getTheAmount();
        if (backEntered()) {
            back();
            return;
        }
        ArrayList<String> allProducts = getAllProducts(seller);
        if (backEntered()) {
            back();
            return;
        }
        Date startDate = getTheStartDate();
        if (backEntered()) {
            back();
            return;
        }
        Date endDate = getTheEndDate(startDate);
        if (backEntered()) {
            back();
            return;
        }
       OffsController.getInstance().addOff(amount, allProducts, startDate, endDate);
        back();
    }

    private double getTheAmount() {
        String command;
        do {
            System.out.println("Enter The Off Amount(in form dd%): ");
            command = scanner.nextLine().trim();
            if (command.matches("\\d{1,2}(\\.\\d+)?%")) {
                return Double.parseDouble(command.substring(0, command.length() - 1));
            } else if (!command.equalsIgnoreCase("back")) {
                System.err.println("invalid command");
            }
        } while (!(command.equalsIgnoreCase("back")));
        setBack(true);
        return 0;
    }

    @Override
    public void help() {

    }

    private double getTheMaxDiscountAmount() {
        String command;
        do {
            System.out.println("Enter The Max Discount Amount: ");
            command = scanner.nextLine().trim();
            if (command.matches("\\d{1,2}(\\.\\d+)?%")) {
                return Double.parseDouble(command.substring(0, command.length() - 1));
            } else if (!command.equalsIgnoreCase("back")) {
                System.err.println("invalid command");
            }
        } while (!(command.equalsIgnoreCase("back")));
        setBack(true);
        return 0;
    }

    private ArrayList<String> getAllProducts(Seller seller) {
        String command;
        ArrayList<Product> products = new ArrayList<>(seller.getAllProducts());
        int productsTotallSize = products.size();
        ArrayList<String> selectedProducts = new ArrayList<>();
        while (true) {
//            "\u001B[34m"+message+"\u001B[0m"
            ArrayList<String> productsId=new ArrayList<>();
            for (Product product : products) {
                productsId.add(product.getProductId());
            }
            System.out.println("\u001B[34m" + "Enter The Product From The List Below(add [productId]):\n" + (ProductController.getInstance().getTheProductDetails(productsId)) + "\u001B[0m");
            command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("back")) {
                setBack(true);
                return null;
            } else if (command.equalsIgnoreCase("finish")) {
                if (selectedProducts.isEmpty()) {
                    System.out.println("You Must Choose A Product");
                } else {
                    return selectedProducts;
                }
            } else if (command.matches("add @p\\d+")) {
                if (!products.contains(seller.getProductByID(command.substring(4)))) {
                    System.out.println("There Is No Product With This Id In That List.");
                } else if (OffsController.getInstance().productExitsInOtherOffer(seller, command.substring(4))) {
                    System.out.println("The Product Already Exists in Other Offer.");
                } else {
                    selectedProducts.add(command.substring(4));
                    products.remove(seller.getProductByID(command.substring(4)));
                    OffsController.getInstance().setTheProductExistInOtherOffer(seller, command.substring(4), true);
                    System.out.println("Product selected");
                    if (selectedProducts.size() == productsTotallSize) {
                        return selectedProducts;
                    }
                }
            } else if (command.equalsIgnoreCase("View selected products")) {
                System.out.println("\u001B[34m" + ProductController.getInstance().getTheProductDetails(selectedProducts) + "\u001B[0m");
            } else {
                System.err.println("invalid command");
            }
        }
    }

    private Date getTheStartDate() {
        String command;
        Date date = new Date();
        Date startDate;
        do {
            System.out.println("Enter The Start Date(2xxx/xx/xx): ");
            command = scanner.nextLine().trim();
            if (command.matches("[1-9]\\d{3}/([1-9]|(1[0-2]))/([1-9]|([1-2][0-9])|30)")) {
                startDate = new Date(command);
                if (startDate.before(date)) {
                    System.out.println("The Time Is Not Acceptable");
                } else {
                    return startDate;
                }
            } else if (!command.equalsIgnoreCase("back")) {
                System.err.println("invalid command");
            }
        } while (!(command.equalsIgnoreCase("back")));
        setBack(true);
        return null;

    }

    private Date getTheEndDate(Date startDate) {
        String command;
        Date endDate;
        do {
            System.out.println("Enter The End Date(2xxx/xx/xx): ");
            command = scanner.nextLine().trim();
            if (command.matches("[1-3]\\d{3}/([1-9]|(1[0-2]))/([1-9]|([1-2][0-9])|30)")) {
                endDate = new Date(command);
                if (endDate.before(startDate)) {
                    System.out.println("The Time Is Not Acceptable");
                } else {
                    return endDate;
                }
            } else if (!command.equalsIgnoreCase("back")) {
                System.err.println("invalid command");
            }
        } while (!(command.equalsIgnoreCase("back")));
        setBack(true);
        return null;
    }

    private boolean backEntered() {
        return back;
    }

    private void setBack(boolean back) {
        this.back = back;
    }
}
