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

    private void getAllAuctionsFromServer() {
        ClientController.getInstance().sendMessageToServer("@getAllAuctions@");
    }

    public void updateAllAuctions() {
        allAuctions.clear();
        getAllAuctionsFromServer();
//        for (Seller seller : UserController.getInstance().getAllSellers()) {
//            if (seller.getAuction() != null) {
//                allAuctions.add(seller.getAuction());
//            }
//        }
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
        System.out.println("mamad is here1");
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("gSPOA",new Gson().toJson(currentAuction)));
        System.out.println("mamad is here2");
    }

    public void sendMessageToAuctionChat(String message) {
        try {
            System.out.println(dataInputStream==null);
            dataOutputStream.writeUTF(new Gson().toJson(
                    new ChatMessage(ClientController.getInstance().getCurrentUser().getUsername(),"",message,currentAuction.getAuctionId())));
            dataOutputStream.flush();
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

    public void connectChatInAuctionPage() {
        try {
            socket = new Socket("localhost",12000);
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("@Auction@" + currentAuction.getAuctionId());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            while (true) {
                String string = null;
                try {
                    string = dataInputStream.readUTF();
                    System.out.println("message received: " + string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!string.isEmpty()) {
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
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    public void setAllAuctions(ArrayList<Auction> allAuctions) {
        this.allAuctions = allAuctions;
    }
}
