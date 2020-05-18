package Controller.Server;

import Models.DiscountCode;
import Models.Offer;
import Models.OfferStatus;
import Models.Product.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

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

    public void createOfferRequest(String message) {
        Gson gson = new Gson();
        Offer offer = gson.fromJson(message, Offer.class);
        offer.setOfferStatus(OfferStatus.onReviewForCreate);
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("AddOffer", gson.toJson(offer)));
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("CreatingOffer", "The Offer Registered For Manager's Confirmation"));
    }

    public String getOfferIdForCreateInOffer() {
        DataBase.getInstance().setLastOfferIdFromDataBase();
        this.lastOffId = "@o" + (Integer.parseInt(lastOffId.substring(2)) + 1);
        DataBase.getInstance().replaceOfferId(lastOffId);
        return this.lastOffId;
    }

    public void setLastOffId(String lastOffId) {
        this.lastOffId = lastOffId;
    }

    public void createEditOfferRequest(Offer offer) {
        offer.setOfferStatus(OfferStatus.onReviewForEdit);
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("EditOffer", new Gson().toJson(offer)));
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
        DataBase.getInstance().getAllOffersFromDataBase();
        for (Offer offer : allOffers) {
            offer.getProducts().removeIf(product -> product.equals(productID));
        }
        DataBase.getInstance().updateAllOffers(new Gson().toJson(allOffers));

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
            UserCenter.getIncstance().updateProductOfferInSeller(ProductCenter.getInstance().getProductWithId(product));
        }
        DataBase.getInstance().updateAllOffers(new Gson().toJson(allOffers));
    }

    public void editOffer(Offer newOffer) {
        Offer oldOffer = getOfferByOfferId(newOffer.getOfferId());
        newOffer.setOfferStatus(OfferStatus.accepted);
        allOffers.set(allOffers.indexOf(oldOffer), newOffer);
        for (String product : oldOffer.getProducts()) {
            ProductCenter.getInstance().addOfferToProduct(product, null);
            CategoryCenter.getIncstance().updateProductInCategory(ProductCenter.getInstance().getProductWithId(product));
            UserCenter.getIncstance().updateProductOfferInSeller(ProductCenter.getInstance().getProductWithId(product));
        }
        for (String product : newOffer.getProducts()) {
            ProductCenter.getInstance().addOfferToProduct(product, newOffer);
            CategoryCenter.getIncstance().updateProductInCategory(ProductCenter.getInstance().getProductWithId(product));
            UserCenter.getIncstance().updateProductOfferInSeller(ProductCenter.getInstance().getProductWithId(product));
        }
        UserCenter.getIncstance().editOfferForSeller(newOffer);
        DataBase.getInstance().updateAllOffers(new Gson().toJson(allOffers));
    }
    public void deleteOffer(Offer offer) {
        allOffers.remove(offer);
        for (String product : offer.getProducts()) {
            ProductCenter.getInstance().addOfferToProduct(product, null);
            CategoryCenter.getIncstance().updateProductInCategory(ProductCenter.getInstance().getProductWithId(product));
            UserCenter.getIncstance().updateProductOfferInSeller(ProductCenter.getInstance().getProductWithId(product));
        }
        UserCenter.getIncstance().removeOfferForSeller(offer);
        DataBase.getInstance().updateAllOffers(new Gson().toJson(allOffers));
    }

    public void setProductStatusForOffer(Offer offer) {
        for (String product : offer.getProducts()) {
            ProductCenter.getInstance().getProductWithId(product).setExistInOfferRegistered(false);
        }
    }

    public Offer getOfferByOfferId(String offerId) {
        for (Offer offer : allOffers) {
            if (offer.getOfferId().equals(offerId)) {
                return offer;
            }
        }
        return null;
    }

    public void passTime() {
            Calendar calendar = Calendar.getInstance();
            DataBase.getInstance().setAllOffersFromDatabase();
        for (Offer offer : allOffers) {
         if(offer.getEndTime().before(calendar.getTime())) {

         }
        }
    }
}
