package Models;

import Models.UserAccount.Customer;
import Models.UserAccount.UserAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DiscountCode {
    private String discountCodeID = "";
    private Date startTime;
    private Date endTime;
    private ArrayList<String> allUserAccountsThatHaveDiscount;
    private int discountPercent;
    private double maxDiscountAmount;
    private HashMap<String, Integer> maxUsingTime;
    private HashMap<String, Integer> remainingTimesForEachCustomer;

    public DiscountCode(Date startTime, Date endTime, ArrayList<String> allUserAccountsThatHaveDiscount, int discountPercent, double maxDiscountAmount, HashMap<String, Integer> maxUsingTime, HashMap<String, Integer> remainingTimesForEachCustomer) {
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

    public ArrayList<String> getAllUserAccountsThatHaveDiscount() {
        return allUserAccountsThatHaveDiscount;
    }

    public void setAllUserAccountsThatHaveDiscount(ArrayList<String> allUserAccountsThatHaveDiscount) {
        this.allUserAccountsThatHaveDiscount = allUserAccountsThatHaveDiscount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public void setDiscountCodeID(String discountCodeID) {
        if (this.discountCodeID.equals(""))
            this.discountCodeID = discountCodeID;
    }

    public double getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(double maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public HashMap<String, Integer> getMaxUsingTime() {
        return maxUsingTime;
    }

    public void setMaxUsingTime(HashMap<String, Integer> maxUsingTime) {
        this.maxUsingTime = maxUsingTime;
    }

    public HashMap<String, Integer> getRemainingTimesForEachCustomer() {
        return remainingTimesForEachCustomer;
    }

    public void setRemainingTimesForEachCustomer(HashMap<String, Integer> remainingTimesForEachCustomer) {
        this.remainingTimesForEachCustomer = remainingTimesForEachCustomer;
    }
}
