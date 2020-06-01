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

    public String getDiscountCodeID() {
        return discountCodeID;
    }

    public ArrayList<String> getAllUserAccountsThatHaveDiscount() {
        return allUserAccountsThatHaveDiscount;
    }

    public void setAllUserAccountsThatHaveDiscount(ArrayList<String> allUserAccountsThatHaveDiscount) {
        this.allUserAccountsThatHaveDiscount = allUserAccountsThatHaveDiscount;
    }

    public String view() {
        String data = "";
        data += "\u001B[34mCode: \u001B[0m" + discountCodeID + "\n";
        data += "\u001B[34mPercent: \u001B[0m" + discountPercent + "%\n";
        data += "\u001B[34mMax Amount: \u001B[0m" + maxDiscountAmount + "\n";
        data += "\u001B[34mStart time: \u001B[0m" + startTime + "\n";
        data += "\u001B[34mEnd time: \u001B[0m" + endTime + "\n";
        for (String userame : allUserAccountsThatHaveDiscount) {
            data += "\u001B[34mUsername: \u001B[0m"+userame + " \u001B[34mRemaining time: \u001B[0m " + remainingTimesForEachCustomer.get(userame) + "\u001B[34m/\u001B[0m" + maxUsingTime.get(userame) + "\n";
        }
        return data;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void usedOneTime(String username) {
        remainingTimesForEachCustomer.replace(username, remainingTimesForEachCustomer.get(username) - 1);

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
