package Models;

import java.util.ArrayList;
import java.util.List;

public class Account {
   private String accountId;
   private String firstName;
   private String lastName;
   private String username;
   private String passWord;
   private double amount;
   private List<Receipt> allReceipts;
   private String companyName;

    public Account(String accountId, String firstName, String lastName, String username, String passWord, double amount) {
        this.amount = amount;
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passWord = passWord;
        allReceipts = new ArrayList<>();
    }


    public List<Receipt> getAllReceipts() {
        return allReceipts;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassWord() {
        return passWord;
    }

    public Account(String accountId, String companyName) {
        this.accountId = accountId;
        this.companyName = companyName;
        allReceipts = new ArrayList<>();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

