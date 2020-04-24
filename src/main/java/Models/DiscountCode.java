package Models;

import Models.UserAccount.Customer;
import Models.UserAccount.UserAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DiscountCode {
    private String discountCodeID;
    private Date startTime;
    private Date endTime;
    private ArrayList<UserAccount> allUserAccountsThatHaveDiscount;
    private int discountPercent;
    private double maxDiscountAmount;
    private HashMap<Customer,Integer> maxUsingTime;
    private HashMap<Customer,Integer> remainingTimesForEachCustomer;

    public DiscountCode(String discountCodeID, Date startTime, Date endTime, ArrayList<UserAccount> allUserAccountsThatHaveDiscount, int discountPercent, double maxDiscountAmount, HashMap<Customer, Integer> maxUsingTime, HashMap<Customer, Integer> remainingTimesForEachCustomer) {
        this.discountCodeID = discountCodeID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.allUserAccountsThatHaveDiscount = allUserAccountsThatHaveDiscount;
        this.discountPercent = discountPercent;
        this.maxDiscountAmount = maxDiscountAmount;
        this.maxUsingTime = maxUsingTime;
        this.remainingTimesForEachCustomer = remainingTimesForEachCustomer;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ArrayList<UserAccount> getAllUserAccountsThatHaveDiscount() {
        return allUserAccountsThatHaveDiscount;
    }

    public void setAllUserAccountsThatHaveDiscount(ArrayList<UserAccount> allUserAccountsThatHaveDiscount) {
        this.allUserAccountsThatHaveDiscount = allUserAccountsThatHaveDiscount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(double maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public HashMap<Customer, Integer> getMaxUsingTime() {
        return maxUsingTime;
    }

    public void setMaxUsingTime(HashMap<Customer, Integer> maxUsingTime) {
        this.maxUsingTime = maxUsingTime;
    }

    public HashMap<Customer, Integer> getRemainingTimesForEachCustomer() {
        return remainingTimesForEachCustomer;
    }

    public void setRemainingTimesForEachCustomer(HashMap<Customer, Integer> remainingTimesForEachCustomer) {
        this.remainingTimesForEachCustomer = remainingTimesForEachCustomer;
    }
}
