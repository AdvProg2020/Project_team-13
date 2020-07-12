package Controller.Client;

import Models.Auction;
import Models.UserAccount.Seller;
import View.MessageKind;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AuctionController {
    private ArrayList<Auction> allAuctions;
    private static AuctionController auctionController;
    private Auction currentAuction;

    private AuctionController() {
        this.allAuctions = new ArrayList<>();
    }

    public void updateAllAuctions() {
        allAuctions.clear();
        UserController.getInstance().getAllUserFromServer();
        for (Seller seller : UserController.getInstance().getAllSellers()) {
            if (seller.getAuction() != null) {
                allAuctions.add(seller.getAuction());
            }
        }
    }

    public ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public void addOfferToList(String userId, double offer) {
        currentAuction.addNewOffer(userId, offer);
        ClientController.getInstance().getCurrentMenu().showMessage("You offer been sent.", MessageKind.MessageWithoutBack);
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("editAuction", new Gson().toJson(currentAuction)));

    }

    public void setCurrentAuction(Auction currentAuction) {
        this.currentAuction = currentAuction;
    }

    public Auction getCurrentAuction() {
        return currentAuction;
    }

    public static AuctionController getInstance() {
        if (auctionController == null) {
            auctionController = new AuctionController();
        }
        return auctionController;
    }

    public void createNewAuction(Auction auction) {
        Gson gson = new Gson();
        String product0 = gson.toJson(auction);
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("AddAuction", product0));
    }
}
