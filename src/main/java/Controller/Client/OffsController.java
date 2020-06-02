package Controller.Client;

import Models.Offer;
import Models.OfferStatus;
import Models.Product.Product;
import Models.UserAccount.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.SortedMap;

public class OffsController {
    private static OffsController offsController;
    private ArrayList<Offer> allOffs;

    public OffsController() {
    }


    public static OffsController getInstance() {
        if (offsController == null) {
            offsController = new OffsController();
        }
        return offsController;
    }

    public void getAllOffersFromServer() {
        ClientController.getInstance().sendMessageToServer("@getAllOffers@");
    }

    public void updateAllOffer(String message) {
        Type productListType = new TypeToken<ArrayList<Offer>>() {
        }.getType();
        allOffs = new Gson().fromJson(message, productListType);
    }

    public void printAllOffs(Seller seller) {
        if (seller.getAllOffer() != null && !seller.getAllOffer().isEmpty()) {
            String str = "";
            for (Offer offer : seller.getAllOffer()) {
                str += offer.toStringForSummery();
                str += "\n\n";
            }
            ClientController.getInstance().getCurrentMenu().showMessage(str);
        } else {
            ClientController.getInstance().getCurrentMenu().printError("There is no Offer for this seller");
        }
    }

    public void printOffById(Seller seller, String offerId) {
        if (seller.offerExists(offerId)) {
            String str = "";
            str += seller.getOfferById(offerId).toString();
            System.out.println(str + "\n");
        } else {
            System.out.println("There is no Offer With This Id");
        }
    }

    public void editOff(Offer offer) {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("editOffer", new Gson().toJson(offer)));
    }

    public void addOff(double amount, ArrayList<String> allProducts, Date startDate, Date endDate) {
        Gson gson = new Gson();
        Offer offer = new Offer(amount, ClientController.getInstance().getCurrentUser().getUsername(), allProducts, startDate, endDate);
        String offerJson = gson.toJson(offer);
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("AddOffer", offerJson));
    }

    public void viewAllProducts(Offer offer) {
        ArrayList<Product> allProducts = new ArrayList<>();
        for (String product : offer.getProducts()) {
            allProducts.add(ProductController.getInstance().getProductWithId(product));
        }
        for (Product product : allProducts) {
            System.out.println(product.productInfoFor());
        }
    }

    public boolean productExitsInOtherOffer(Seller seller, String productId) {
        return seller.getProductByID(productId).isExistInOfferRegistered();
    }

    public void setTheProductExistInOtherOffer(Seller seller, String productId, boolean state) {
        seller.getProductByID(productId).setExistInOfferRegistered(state);
    }

    public void getAllOffsFromServer() {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("getAllOffsForManager", null));
    }

    public String showAllOffs() {
        getAllOffsFromServer();
        if (allOffs != null) {
            String allOffs = "";
            int i = 1;
            for (Offer offer : this.allOffs) {
                allOffs += i + "." + offer.toString() + "\n";
            }
            return allOffs;
        } else return "there is no offs";
    }

    public boolean getTheProductById(String productId) {
        for (Offer off : allOffs) {
            for (String product : off.getProducts()) {
                if (product.equals(productId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isThereAnyProductWithThisId(String productId) {
        return getTheProductById(productId);
    }
}
