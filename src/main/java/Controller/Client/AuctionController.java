package Controller.Client;

import Models.Auction;
import Models.ChatMessage;
import Models.UserAccount.Seller;
import View.AuctionPage;
import View.MessageKind;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class AuctionController {
    private ArrayList<Auction> allAuctions;
    private static AuctionController auctionController;
    private Auction currentAuction;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;
    private AuctionPage auctionPage;

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
        if(currentAuction!=null) {
            for (Auction auction : allAuctions) {
                if(auction.getAuctionId().equals(currentAuction.getAuctionId())) {
                    currentAuction = auction;
                }
            }
        }
    }

    public ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public void getAuctionServerPort(){
        System.out.println(currentAuction==null);
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("gSPOA",new Gson().toJson(currentAuction)));
    }

    public void sendMessageToAuctionChat(String message) {
        try {
            dataOutputStream.writeUTF(new Gson().toJson(
                    new ChatMessage(ClientController.getInstance().getCurrentUser().getUsername(),"",message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAuctionPage(AuctionPage auctionPage) {
        this.auctionPage = auctionPage;
    }

    public AuctionPage getAuctionPage() {
        return auctionPage;
    }

    public void connectChatInAuctionPage(int serverPort) {
        try {
            socket = new Socket("127.0.0.1",serverPort);
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            System.out.println(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            while (true) {
                System.out.println("aaaaaaaa");
                String string = null;
                try {
                    string = dataInputStream.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!string.isEmpty()) {
                    System.out.println("bbbbbbbbb");
                    Auction auction = new Gson().fromJson(string,Auction.class);
                    auctionPage.setNewMessage(auction.getChatMessages());
                }
            }
        }).start();

    }

    public void addOfferToList(String userId, double offer) {
        currentAuction.addNewOffer(userId, offer);
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
