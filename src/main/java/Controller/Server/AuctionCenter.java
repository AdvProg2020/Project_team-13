package Controller.Server;

import Models.Auction;

import java.io.DataOutputStream;
import java.util.ArrayList;

public class AuctionCenter {
    private static AuctionCenter auctionCenter;
    private String lastAuctionId;

    public AuctionCenter() {
    }

    public static AuctionCenter getInstance() {
        if (auctionCenter == null) {
            synchronized (OffCenter.class) {
                if (auctionCenter == null) {
                    auctionCenter = new AuctionCenter();
                }
            }
        }
        return auctionCenter;
    }

    public synchronized void createNewAuctionRequest(String message, DataOutputStream dataOutputStream) {
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("AddAuction", message));
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Successful", "The Auction Registered For Manager's Confirmation"), dataOutputStream);
    }

    public synchronized void createAuction(Auction auction, DataOutputStream dataOutputStream) {
        auction.setAuctionId(getAuctionIdForCreateAuction());
        UserCenter.getIncstance().addAuction(auction, dataOutputStream);
    }

    public synchronized String getAuctionIdForCreateAuction() {
        DataBase.getInstance().setLastAuctionIdFromDataBase();
        if(lastAuctionId==null) {
            lastAuctionId="";
        }
        this.lastAuctionId = "@A" + (Integer.parseInt(lastAuctionId.substring(2)) + 1);
        DataBase.getInstance().replaceAuctionId(lastAuctionId);
        return this.lastAuctionId;
    }

    public void setLastAuctionId(String lastAuctionId) {
        this.lastAuctionId = lastAuctionId;
    }
}
