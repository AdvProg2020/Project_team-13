package View.UserMenu.Seller;

import Controller.Client.ClientController;
import Controller.Client.OffsController;
import Controller.Client.ProductController;
import Models.Offer;
import Models.Product.Product;
import Models.UserAccount.Seller;
import View.Menu;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.SortedMap;

public class EditOffsMenu extends Menu {
    private Offer offer;
    private boolean back;
    private ArrayList<Product> allProducts;

    public EditOffsMenu(Menu parentMenu) {
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
    public void help() {
        String EditOffsMenusHelp = "";
        EditOffsMenusHelp += "1. Edit Offer's Amount\n";
        EditOffsMenusHelp += "2. Edit Offer's MaxDiscountAmount\n";
        EditOffsMenusHelp += "3. Edit Exact Start Time\n";
        EditOffsMenusHelp += "4. Edit Offer's MaxDiscountAmount\n";
        EditOffsMenusHelp += "5. Edit Exact End Time\n";
        EditOffsMenusHelp += "6. Edit Products\n";
        EditOffsMenusHelp += "7. Help\n";
        EditOffsMenusHelp += "8. LogOut\n";
        EditOffsMenusHelp += "9. Back\n";
        EditOffsMenusHelp += "10.Finish\n";
        System.out.println(EditOffsMenusHelp);
    }

    @Override
    public void execute() {
        ViewOffsMenu viewOffsMenu = (ViewOffsMenu) parentMenu;
        Seller seller = (Seller) ClientController.getInstance().getCurrentUser();
        if (!seller.offerExists(viewOffsMenu.getTheIdForEdit())) {
            System.out.println("There Is No Offer With This Id");
            back();
            return;
        }
        offer = seller.getOfferById(viewOffsMenu.getTheIdForEdit());
        OffsController.getInstance().printOffById((Seller) ClientController.getInstance().getCurrentUser(), viewOffsMenu.getTheIdForEdit());
        String command;
        do {
            System.out.println("Enter The Feature That You Want To Edit You Can Use The Help For Your Commands: ");
            command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("Edit Offer's Amount")) {
                double amount = getTheAmount();
                if (isBack()) {
                    continue;
                }
                offer.setAmount(amount);
            } else if (command.equalsIgnoreCase("Edit Offer's MaxDiscountAmount")) {
                double maxDiscountAmount = getTheMaxDiscountAmount();
                if (isBack()) {
                    continue;
                }
            } else if (command.equalsIgnoreCase("Edit Exact Start Time")) {
                Date startDate = getTheStartDate(offer, null);
                if (isBack()) {
                    continue;
                }
                offer.setStartTime(startDate);
            } else if (command.equalsIgnoreCase("Edit Exact End Time")) {
                Date endDate = getTheEndDate(offer, null);
                if (isBack()) {
                    continue;
                }
                offer.setEndTime(endDate);
            } else if (command.equalsIgnoreCase("Edit Products")) {
                editProduct();
                if (isBack()) {
                    continue;
                }
                ArrayList<String> allProductsID=new ArrayList<>();
                for (Product product : allProducts) {
                    allProductsID.add(product.getProductId());
                }
                offer.setProducts(allProductsID);
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.equalsIgnoreCase("finish")) {
                OffsController.getInstance().editOff(offer);
                break;
            } else if (!command.equalsIgnoreCase("back")) {
                System.out.println("invalid command");
            }
        } while (!(command.equalsIgnoreCase("back")));
        back();
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    private double getTheAmount() {
        String amount;
        while (true) {
            System.out.println("Enter The Amount: ");
            amount = scanner.nextLine().trim();
            if (amount.matches("\\d+(\\.\\d+)?%")) {
                return Double.parseDouble(amount.substring(0, amount.length() - 1));
            } else if (amount.equalsIgnoreCase("back")) {
                break;
            } else {
                System.out.println("Please Enter A Correct Number");
            }
        }
        setBack(true);
        return 0;
    }

    private double getTheMaxDiscountAmount() {
        String maxDiscountAmount;
        while (true) {
            System.out.println("Enter The MaxDiscountAmount: ");
            maxDiscountAmount = scanner.nextLine().trim();
            if (maxDiscountAmount.matches("\\d+(\\.\\d+)?%")) {
                return Double.parseDouble(maxDiscountAmount.substring(0, maxDiscountAmount.length() - 1));
            } else if (maxDiscountAmount.equalsIgnoreCase("back")) {
                break;
            } else {
                System.out.println("Please Enter A Correct Number");
            }
        }
        setBack(true);
        return 0;
    }

    private Date getTheStartDate(Offer offer, Date endDate) {
        String startDate;
        Date date = new Date();
        Date start;
        if (endDate != null) {
            while (true) {
                System.out.println("You Should Change The Start Date Too To Exit From This Path");
                startDate = scanner.nextLine().trim();
                if (startDate.matches("[1-9]\\d{3}/([1-9]|(1[0-2]))/([1-9]|([1-2][0-9])|30)")) {
                    start = new Date(startDate);
                    if (start.after(endDate)) {
                        return start;
                    } else {
                        System.out.println("You Should Enter The Correct Time");
                    }
                } else {
                    System.out.println("You Should Enter A Correct Date");
                }
            }
        }
        while (true) {
            System.out.println("Enter The Start Date (yy/mm/dd): ");
            startDate = scanner.nextLine().trim();
            if (startDate.matches("[1-9]\\d{3}/([1-9]|(1[0-2]))/([1-9]|([1-2][0-9])|30)")) {
                start = new Date(startDate);
                if (start.before(date)) {
                    System.out.println("You Can't Choose The Time Before Now");
                } else if (start.after(offer.getEndTime())) {
                    Date endDateTime = getTheEndDate(offer, start);
                    offer.setEndTime(endDate);
                    return start;
                } else {
                    return start;
                }
            } else if (startDate.equalsIgnoreCase("back")) {
                break;
            } else {
                System.out.println("Please Enter A Correct Date");
            }
        }
        setBack(true);
        return null;
    }

    private Date getTheEndDate(Offer offer, Date start) {
        String endDate;
        Date started = new Date();
        Date end;
        if (start != null) {
            while (true) {
                System.out.println("You Should Change The End Date Too To Exit From This Path");
                endDate = scanner.nextLine().trim();
                if (endDate.matches("[1-9]\\d{3}/([1-9]|(1[0-2]))/([1-9]|([1-2][0-9])|30)")) {
                    end = new Date(endDate);
                    if (end.after(start)) {
                        return end;
                    } else {
                        System.out.println("You Should Enter The Correct Time");
                    }
                } else if (endDate.equalsIgnoreCase("back")) {
                    break;
                } else {
                    System.out.println("Please Enter A Correct Date");
                }
            }
        }
        do {
            System.out.println("Enter The End Date:");
            endDate = scanner.nextLine().trim();
            if (endDate.matches("[1-9]\\d{3}/([1-9]|(1[0-2]))/([1-9]|([1-2][0-9])|30)")) {
                end = new Date(endDate);
                if (end.before(started)) {
                    System.out.println("You Can't Choose The Time Before Now");
                } else if (end.before(offer.getStartTime())) {
                    Date startDate = getTheEndDate(offer, end);
                    offer.setStartTime(startDate);
                    return end;
                } else {
                    return end;
                }
            } else if (!endDate.equalsIgnoreCase("back")) {
                System.out.println("Please Enter A Correct Date");
            }
        } while (!(endDate.equalsIgnoreCase("back")));
        setBack(true);
        return null;
    }

    private void removeProduct() {
        Seller seller = (Seller) ClientController.getInstance().getCurrentUser();
        String command;
        OffsController.getInstance().viewAllProducts(offer);
        do {
            System.out.println("remove [product id]");
            command = scanner.nextLine().trim();
            if (command.matches("remove @p\\d+")) {
                if (offer.getProducts().contains(offer.getProductByIdInOfferList(command.substring(7)))) {
                    offer.getProducts().remove(offer.getProductByIdInOfferList(command.substring(7)));
                    offer.getProducts().trimToSize();
                    OffsController.getInstance().setTheProductExistInOtherOffer(seller, command.substring(7), false);
                    System.out.println("The Product Successfully Removed");
                } else {
                    System.out.println("The Product Isn't In The List");
                }
            } else if (!command.equalsIgnoreCase("back")) {
                System.out.println("invalid command");
            }
        } while (!(command.equalsIgnoreCase("back")));
    }

    private void addProductFromList() {
        Seller seller = (Seller) ClientController.getInstance().getCurrentUser();
        String command;
        System.out.println(seller.viewAllProducts());
        do {
            System.out.println("add [product id]");
            command = scanner.nextLine().trim();
            if (command.matches("add @p\\d+")) {
                if (!seller.productExists(command.substring(4))) {
                    System.out.println("The Product Isn't in Your List.");
                } else if (OffsController.getInstance().productExitsInOtherOffer(seller, command.substring(4))) {
                    System.out.println("The Product Already Exists In Other Offer.");
                } else {
                    allProducts.add(seller.getProductByID(command.substring(4)));
                    OffsController.getInstance().setTheProductExistInOtherOffer(seller, command.substring(4), true);
                    System.out.println("The product added.");
                }
            } else if (!command.equalsIgnoreCase("back")) {
                System.out.println("invalid command");
            }
        } while (!(command.equalsIgnoreCase("back")));
    }

    private void editProduct() {
        String command;
        allProducts = new ArrayList<>();
        do {
            System.out.println("1. Add Product\n2. Remove Product\n3. finish");
            command = scanner.nextLine().trim();
            if (command.equals("finish")) {
                if (!allProducts.isEmpty()) {
                    return;
                }
                System.out.println("You Should Choose To Add Or Edit Product OtherWise Press Back.");
            } else if (command.equals("Add Product")) {
                addProductFromList();
            } else if (command.equals("Remove Product")) {
                removeProduct();
            } else if (!command.equalsIgnoreCase("back")) {
                System.out.println("invalid command");
            }
        } while (!(command.equalsIgnoreCase("back")));
        setBack(true);
    }
}
