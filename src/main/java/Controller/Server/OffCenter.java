package Controller.Server;

import Models.Offer;
import Models.OfferStatus;
import Models.UserAccount.Seller;
import com.google.gson.Gson;

import java.util.ArrayList;

public class OffCenter {
    private ArrayList<Offer> allOffers;
    private static OffCenter offCenter;
    private String lastOffId;

    public OffCenter() {
    }

    public static OffCenter getInstance() {
        if (offCenter == null) {
            offCenter = new OffCenter();
        }
        return offCenter;
    }

    public void createNewOff(String json) {

    }

    public void createOfferRequest(String message) {
        Gson gson = new Gson();
        Offer offer = gson.fromJson(message, Offer.class);
        offer.setOfferId(getOfferIdForCreateInOffer());
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("AddOffer", message));
        offer.setOfferStatus(OfferStatus.onReviewForCreate);
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("CreatingOffer", "The Offer Registered For Manager's Confirmation"));
    }

    public String getOfferIdForCreateInOffer() {
        DataBase.getInstance().setLastOfferIdFromDataBase();
        this.lastOffId = "@o" + (Integer.parseInt(lastOffId.substring(2, lastOffId.length())) + 1);
        DataBase.getInstance().replaceOfferId(lastOffId);
        return this.lastOffId;
    }

    public void setLastOffId(String lastOffId) {
        this.lastOffId = lastOffId;
    }

    public void deleteOff() {

    }

    public void editOff() {

    }

}
