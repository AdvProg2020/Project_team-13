package Models;

import Models.Product.Product;
import Models.UserAccount.Seller;

import java.util.ArrayList;
import java.util.Date;

public class Offer {
    private String offerId;
    private double amount;
    private String seller;
    private ArrayList<String> productsId;
    private Date startTime;
    private Date endTime;
    private OfferStatus offerStatus;

    public Offer(double amount, String seller, ArrayList<String> products, Date startTime, Date endTime) {
        this.amount = amount;
        this.seller = seller;
        this.productsId = products;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getSeller() {
        return seller;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ArrayList<String> getProducts() {
        return productsId;
    }

    public void setProducts(ArrayList<String> products) {
        this.productsId = products;
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

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public double getAmount() {
        return amount;
    }

    public String toStringForSummery() {
        return "offerId : " + offerId + "\n" +
                "amount : " + amount +'%'+ "\n"+
                "startTime : " + startTime + "\n"+
                "endTime : " + endTime + "\n"+
                "offerStatus : " + offerStatus + "\n"
                ;
    }

    @Override
    public String toString() {
        String listOfProducts="";
        for (String s : productsId) {
            listOfProducts+=s+"\t";
        }
        return "offerId :" + offerId + "\n" +
                "amount : " + amount + '%'+"\n"+
                "products :" + listOfProducts +"\n"+
                "startTime : " + startTime +"\n"+
                "endTime : "  + endTime +"\n"+
                "offerStatus : " + offerStatus +"\n"
                ;
    }



    public String getProductByIdInOfferList(String id){
        for (String productId : productsId) {
            if(productId.equalsIgnoreCase(id)){
                return productId;
            }
        }
        return null;
    }
}
