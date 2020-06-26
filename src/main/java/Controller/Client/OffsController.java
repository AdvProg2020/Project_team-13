package Controller.Client;

import Models.Offer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class OffsController {
    private static OffsController offsController;
    private ArrayList<Offer> allOffs;
    private Offer currentOffer;

    public OffsController() {
    }


    public static OffsController getInstance() {
        if (offsController == null) {
            offsController = new OffsController();
        }
        return offsController;
    }

    public void setCurrentOffer(Offer currentOffer) {
        this.currentOffer = currentOffer;
    }

    public Offer getCurrentOffer() {
        return currentOffer;
    }

    public void updateAllOffer(String message) {
        Type productListType = new TypeToken<ArrayList<Offer>>() {
        }.getType();
        allOffs = new Gson().fromJson(message, productListType);
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
}
