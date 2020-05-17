package Models.UserAccount;

import Controller.Client.ClientController;
import Models.DiscountCode;
import Models.Log;
import Models.Product.Cart;
import Models.Request;

import java.util.ArrayList;

public class Customer extends UserAccount{

    private ArrayList<Request> allRequests;

    public Customer(String username, String password, String firstName, String lastName, String email, String phoneNumber, double credit) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.allDiscountCodes=new ArrayList<>();
        this.type="@Customer";
    }


    public ArrayList<Request> getAllRequests() {
        return allRequests;
    }


    @Override
    public String viewPersonalInfo() {
        String personalInfo = "";
        personalInfo += this.username + "\n";
        personalInfo += this.firstName + "\n";
        personalInfo += this.lastName + "\n";
        personalInfo += this.email + "\n";
        personalInfo += this.phoneNumber + "\n";
        personalInfo += this.credit ;
        return personalInfo;
    }
    public void addDiscountCode(DiscountCode discountCode){
        allDiscountCodes.add(discountCode);
    }
    public void removeDiscountCode(String code){
        for (DiscountCode discountCode : allDiscountCodes) {
            if(discountCode.getDiscountCodeID().equals(code)){
                allDiscountCodes.remove(discountCode);
                return;
            }
        }
    }
    public void useDiscountCode(String code){
        if(findDiscountCodeWithCode(code).getRemainingTimesForEachCustomer().get(username)>1) {
            findDiscountCodeWithCode(code).usedOneTime(username);
        }else{
            removeDiscountCode(code);
        }
    }
    public DiscountCode findDiscountCodeWithCode(String code){
        for (DiscountCode discountCode : allDiscountCodes) {
            if(discountCode.getDiscountCodeID().equals(code)){
                return discountCode;
            }
        }
        return null;
    }

    public void printAllDiscountCodes() {
        String showAllDiscountCodes = "";
        for (DiscountCode discountCode :allDiscountCodes ) {
            showAllDiscountCodes += discountCode.getDiscountCodeID() + " " +discountCode.getDiscountPercent()+"% "+discountCode.getMaxDiscountAmount()+" ("+ discountCode.getStartTime()+") TO ("+discountCode.getEndTime() + ")\n";
        }
        ClientController.getInstance().getCurrentMenu().showMessage(showAllDiscountCodes);
    }
}
