package Models.UserAccount;

import Controller.Client.ClientController;
import Models.DiscountCode;
import Models.Log;
import Models.Product.Cart;
import Models.Product.Product;
import Models.Request;

import java.util.ArrayList;

public class Customer extends UserAccount {

    private double totalBuyAmount = 0;
    private ArrayList<Request> allRequests;

    public Customer(String username, String password, String firstName, String lastName, String email, String phoneNumber, double credit) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.allDiscountCodes = new ArrayList<>();
        this.type = "@Customer";
    }


    public ArrayList<Request> getAllRequests() {
        return allRequests;
    }


    @Override
    public String viewPersonalInfo() {
        String personalInfo = "Customer\n";
        personalInfo += "Username: " + this.username + "\n";
        personalInfo += "First name: " + this.firstName + "\n";
        personalInfo += "Last name: " + this.lastName + "\n";
        personalInfo += "Email: " + this.email + "\n";
        personalInfo += "Phone Number: " + this.phoneNumber + "\n";
        personalInfo += "Credit: " + this.credit + "$" + "\n";
        personalInfo += "-----";
        return personalInfo;
    }

    public void addDiscountCode(DiscountCode discountCode) {
        allDiscountCodes.add(discountCode);
    }

    public double getTotalBuyAmount() {
        return totalBuyAmount;
    }

    public void setTotalBuyAmount(double totalBuyAmount) {
        this.totalBuyAmount = totalBuyAmount;
    }

    public void removeDiscountCode(String code) {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.getDiscountCodeID().equals(code)) {
                allDiscountCodes.remove(discountCode);
                return;
            }
        }
    }

    public void useDiscountCode(String code) {
        if (findDiscountCodeWithCode(code).getRemainingTimesForEachCustomer().get(username) > 1) {
            findDiscountCodeWithCode(code).usedOneTime(username);
        } else {
            removeDiscountCode(code);
        }
    }

    public String viewOrders() {
        StringBuilder orders = new StringBuilder();
        for (Log buylog : this.historyOfTransaction) {
            orders.append(buylog.getId()).append(" ");
            orders.append(buylog.getOtherSideUserName()).append(" ");
            orders.append("\n");
        }
        return orders.toString();
    }

    public Log findOrderWithId(String logID) {
        for (Log log : historyOfTransaction) {
            if (log.getId().equals(logID)) {
                return log;
            }
        }
        return null;
    }

    public Product findProductWithId(String productID) {
        for (Log log : historyOfTransaction) {
            for (Product product : log.getAllProducts()) {
                if (product.getProductId().equals(productID)) {
                    return product;
                }
            }
        }
        return null;
    }

    public DiscountCode findDiscountCodeWithCode(String code) {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.getDiscountCodeID().equals(code)) {
                return discountCode;
            }
        }
        return null;
    }

    public void printAllDiscountCodes() {
        String showAllDiscountCodes = "";
        for (DiscountCode discountCode : allDiscountCodes) {
            showAllDiscountCodes += discountCode.getDiscountCodeID() + " " + discountCode.getDiscountPercent() + "% " + discountCode.getMaxDiscountAmount() + " (" + discountCode.getStartTime() + ") TO (" + discountCode.getEndTime() + ")\n";
        }
     //   ClientController.getInstance().getCurrentMenu().showMessage(showAllDiscountCodes);
    }
}
