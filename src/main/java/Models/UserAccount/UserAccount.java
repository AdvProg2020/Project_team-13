package Models.UserAccount;

import Models.DiscountCode;
import Models.Log;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;

public abstract class UserAccount {

    protected String username,password,firstName,lastName,email,phoneNumber,type;
    protected ArrayList<DiscountCode> allDiscountCodes;
    protected double credit;
    protected ArrayList<Log> historyOfTransaction;
    protected String imagePath="";
    protected ImageView imageView;

    public UserAccount(String username, String password, String firstName, String lastName, String email,
                       String phoneNumber, double credit) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.historyOfTransaction=new ArrayList<>();
        this.phoneNumber = phoneNumber;
        this.credit = credit;

    }

    public String getType() {
        return type;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        if(imagePath==null||imagePath.isEmpty()||imagePath.length()<4) {
            return "file:src/user_icon.png";
        }
        return imagePath;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }



    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setHistoryOfTransaction(ArrayList<Log> historyOfTransaction) {
        this.historyOfTransaction = historyOfTransaction;
    }

    public String getEmail() {
        return email;
    }
    public void addLog(Log log){
        historyOfTransaction.add(log);
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }

    public double getCredit() {
        return credit;
    }

    public ArrayList<Log> getHistoryOfTransaction() {
        if(historyOfTransaction==null) {
            historyOfTransaction = new ArrayList<>();
        }
        return historyOfTransaction;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void addDiscountCodeCode(DiscountCode discountCode) {
        this.allDiscountCodes.add(discountCode);
    }

    public void deleteDiscountCodeCode(DiscountCode discountCode) {
        if (allDiscountCodes.contains(discountCode)) this.allDiscountCodes.remove(discountCode);
    }

    public void increaseCredit(int credit) {
        this.credit += credit;
    }

    public void reduceCredit(int credit) {
        this.credit -= credit;
    }

    public void addHistoryOfTransaction(Log log) {
        this.historyOfTransaction.add(log);
    }

    public abstract String viewPersonalInfo();

    public void setAllDiscountCodes(ArrayList<DiscountCode> allDiscountCodes) {
        this.allDiscountCodes = allDiscountCodes;
    }
}
