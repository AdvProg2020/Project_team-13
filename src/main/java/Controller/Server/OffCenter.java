package Controller.Server;

import Controller.Client.CategoryController;
import Models.Offer;
import Models.OfferStatus;
import Models.Product.Category;
import Models.Product.Product;
import Models.UserAccount.Seller;
import com.google.gson.Gson;

import java.util.ArrayList;

public class OffCenter {
    private ArrayList<Offer> allOffers = new ArrayList<>();
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
        offer.setOfferStatus(OfferStatus.onReviewForCreate);
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("AddOffer", gson.toJson(offer)));
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

    public void createEditOfferRequest(Offer offer) {
        offer.setOfferStatus(OfferStatus.onReviewForEdit);
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("editOffer", new Gson().toJson(offer)));
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("editOffer", "The Offer's Edition Is Saved For Manager's Confirmation "));
    }

    public void setAllOffers(ArrayList<Offer> allOffers) {
        this.allOffers = allOffers;
    }

    public Product findProductWithID(String productID) {
        for (Offer offer : allOffers) {
            for (String product : offer.getProducts()) {
                if (product.equals(productID)) {
                    ProductCenter.getInstance().getProductWithId(productID);
                }
            }
        }
        return null;
    }

    public ArrayList<Offer> getAllOffers() {
        return allOffers;
    }

    public void removeProduct(String productID) {
        for (Offer offer : allOffers) {
            for (String product : offer.getProducts()) {
                if (product.equals(productID)) {
                    offer.getProducts().remove(product);
                }
            }
        }
    }

    public void createNewOff(Offer offer) {
        offer.setOfferId(getOfferIdForCreateInOffer());
        offer.setOfferStatus(OfferStatus.accepted);
        if (allOffers == null) {
            allOffers = new ArrayList<>();
        }
        allOffers.add(offer);
        UserCenter.getIncstance().addOfferToSeller(offer);
        for (String product : offer.getProducts()) {
            ProductCenter.getInstance().addOfferToProduct(product, offer);
            CategoryCenter.getIncstance().updateProductInCategory(ProductCenter.getInstance().getProductWithId(product));
        }
        DataBase.getInstance().updateAllOffers(new Gson().toJson(allOffers));
    }

    public void editOffer(Offer offer) {
        offer.setOfferStatus(OfferStatus.accepted);
        //must be handled
    }

}
