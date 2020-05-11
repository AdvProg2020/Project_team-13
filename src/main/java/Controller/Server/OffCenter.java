package Controller.Server;

import Models.Offer;
import Models.UserAccount.Seller;
import com.google.gson.Gson;

import java.util.ArrayList;

public class OffCenter {
    private ArrayList<Offer> allOffers;
    private static OffCenter offCenter;
    private String lastOffId;

    public OffCenter() {
    }
    public static OffCenter getInstance(){
        if(offCenter==null){
            offCenter=new OffCenter();
        }
        return offCenter;
    }
    public void createNewOff(String json){

    }
    public void deleteOff(){

    }
    public void editOff(){

    }

}
