package Models.UserAccount;

import Models.DiscountCode;
import Models.Log;

import java.util.ArrayList;

public abstract class UserAccount {

    protected String username,password,firstName,lastName,email,phoneNumber;
    protected ArrayList<DiscountCode> allDiscountCodes;
    protected int credit;
    protected ArrayList<Log> historyOfTransaction;

    public UserAccount(String username, String password, String firstName, String lastName, String email,
                       String phoneNumber, int credit) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.credit = credit;
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

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<DiscountCode> getAllOfferCodes() {
        return allDiscountCodes;
    }

    public int getCredit() {
        return credit;
    }

    public ArrayList<Log> getHistoryOfTransaction() {
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


}
