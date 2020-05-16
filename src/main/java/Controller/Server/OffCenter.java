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

    public void createOfferRequest(Offer offer) {
        Gson gson = new Gson();
        offer.setOfferStatus(OfferStatus.onReviewForCreate);
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("AddOffer", gson.toJson(offer)));
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Successful", "The Offer Registered For Manager's Confirmation"));
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

    public void createEditOfferRequest(Offer offer) {
       offer.setOfferStatus(OfferStatus.onReviewForEdit);
       RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("EditOffer", new Gson().toJson(offer)));
       ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Successful","The Offer's Edition Is Saved For Manager's Confirmation "));
    }

    public void createNewOff(Offer offer){
        offer.setOfferId(getOfferIdForCreateInOffer());
        offer.setOfferStatus(OfferStatus.accepted);
        if (allOffers == null) {
            allOffers = new ArrayList<>();
        }
        allOffers.add(offer);
        UserCenter.getIncstance().addOfferToSeller(offer);
        DataBase.getInstance().updateAllOffers(new Gson().toJson(allOffers));
    }

    public void editOffer(Offer newOffer){
        Offer oldOffer=findOfferWithThisId(newOffer.getOfferId());
        newOffer.setOfferStatus(OfferStatus.accepted);
        allOffers.set(allOffers.indexOf(oldOffer), newOffer);
        UserCenter.getIncstance().editOfferForSeller(oldOffer, newOffer);
        DataBase.getInstance().updateAllOffers(new Gson().toJson(allOffers));
    }

    public Offer findOfferWithThisId(String offerId){
        for (Offer allOffer : allOffers) {
            if(allOffer.getOfferId().equals(offerId)){
                return allOffer;
            }
        }
        return null;
    }

    public void setAllOffers(ArrayList<Offer> allOffers) {
        this.allOffers = allOffers;
    }
}
