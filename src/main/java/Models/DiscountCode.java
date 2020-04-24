package Models;

import Models.UserAccount.Customer;
import Models.UserAccount.UserAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DiscountCode {
   private String discountCode;
   private Date exactStartTime;
   private Date exactEndTime;
   private ArrayList<UserAccount> allUserAccountsThatHaveDiscount;
   private int discountPercent;
   private double maxDiscountAmount;
   private int MaximumUsingTime;
   private HashMap<Customer, Integer> remainingTimesForEachCustomer;

    public DiscountCode(String discountCode, Date exactStartTime, Date exactEndTime, ArrayList<UserAccount> allUserAccountsThatHaveDiscount, int discountPercent, double maxDiscountAmount) {
        this.discountCode = discountCode;
        this.exactStartTime = exactStartTime;
        this.exactEndTime = exactEndTime;
        this.allUserAccountsThatHaveDiscount = allUserAccountsThatHaveDiscount;
        this.discountPercent = discountPercent;
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Date getExactStartTime() {
        return exactStartTime;
    }

    public void setExactStartTime(Date exactStartTime) {
        this.exactStartTime = exactStartTime;
    }

    public Date getExactEndTime() {
        return exactEndTime;
    }

    public void setExactEndTime(Date exactEndTime) {
        this.exactEndTime = exactEndTime;
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

    public int getMaximumUsingTime() {
        return MaximumUsingTime;
    }

    public void setMaximumUsingTime(int maximumUsingTime) {
        MaximumUsingTime = maximumUsingTime;
    }

    public HashMap<Customer, Integer> getRemainingTimesForEachCustomer() {
        return remainingTimesForEachCustomer;
    }

    public void setRemainingTimesForEachCustomer(HashMap<Customer, Integer> remainingTimesForEachCustomer) {
        this.remainingTimesForEachCustomer = remainingTimesForEachCustomer;
    }

    @Override
    public String toString() {
        return "DiscountCode{" +
                "discountCode='" + discountCode + '\'' +
                ", exactStartTime=" + exactStartTime +
                ", exactEndTime=" + exactEndTime +
                ", discountPercent=" + discountPercent +
                ", maxDiscountAmount=" + maxDiscountAmount +
                ", MaximumUsingTime=" + MaximumUsingTime +
                '}';
    }
}
