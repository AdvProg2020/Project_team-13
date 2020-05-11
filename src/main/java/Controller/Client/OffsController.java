package Controller.Client;

import Models.Offer;
import Models.UserAccount.Seller;
import com.google.gson.Gson;

import java.util.ArrayList;

public class OffsController {
    private static OffsController offsController;
    private ArrayList<Offer> allOffs;

    public OffsController() {
    }

    public static OffsController getInstance(){
        if(offsController==null){
            offsController=new OffsController();
        }
        return offsController;
    }
    public void  printAllOffs(Seller seller){
        if(!seller.getAllOffer().isEmpty()) {
            String str="";
            for (Offer offer : seller.getAllOffer()) {
                str+=offer.toStringForSummery();
            }
            System.out.println(str+'\n');
        }else{
            System.out.println("There is no Offer for this seller");
        }
    }
    public void printOffById(Seller seller, String offerId){
        if(seller.offerExists(offerId)){
            String str="";
            str+=seller.getOfferById(offerId).toString();
            System.out.println(str+'\n');
        }else {
            System.out.println("There is no Offer With This Id");
        }
    }
    public void editOff(){

    }
    public void addOff(){

    }
    /*public String viewAllProducts(){

    }*/
}
