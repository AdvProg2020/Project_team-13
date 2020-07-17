package Controller.Server;

import Models.Auction;
import Models.ChatMessage;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AuctionCenter {
    private static AuctionCenter auctionCenter;
    private String lastAuctionId;
    private HashMap<String, ServerSocket> auctionsChatBoxes = new HashMap<>();
    private HashMap<ServerSocket, ArrayList<DataOutputStream>> allClients = new HashMap<>();
    private ArrayList<Auction> allAuctions;

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

    public void runAuctionServerSockets() {
        allAuctions = UserCenter.getIncstance().getAllAuctions();
        for (Auction auction : allAuctions) {
            if (new Date().after(auction.getStartTime()) && !auctionsChatBoxes.containsKey(auction.getAuctionId())) {
                AuctionThread auctionThread = new AuctionThread(auction);
                auctionThread.start();
            }
        }
    }

    class AuctionThread extends Thread {
        private ServerSocket serverSocket;
        private Auction auction;

        public AuctionThread(Auction auction) {
            this.auction = auction;
        }

        @Override
        public void run() {
            serverSocket = null;
            try {
                serverSocket = new ServerSocket(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            auctionsChatBoxes.put(auction.getAuctionId(), serverSocket);
            allClients.put(serverSocket, new ArrayList<>());
            while (true) {
                Socket socket = null;
                try {
                    if (new Date().after(auction.getEndTime())) {
                        break;
                    }
                    if ((socket = serverSocket.accept())!=null) {
                        DataOutputStream dataOutputStream1 = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                        allClients.get(serverSocket).add(dataOutputStream1);
                        Socket finalSocket = socket;
                        ServerSocket finalServerSocket = serverSocket;
                        new Thread() {
                            @Override
                            public void run() {
                                DataInputStream dataInputStream = null;
                                try {
                                    dataInputStream = new DataInputStream(new BufferedInputStream(finalSocket.getInputStream()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                while (true) {
                                    if (new Date().after(auction.getEndTime())) {
                                        break;
                                    }
                                    String message = null;
                                    try {
                                        message = dataInputStream.readUTF();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    if (!message.isEmpty()) {
                                        addNewMessage(finalServerSocket, message, auction);
                                    }
                                }
                            }
                        }.start();
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }

        public synchronized void addNewMessage(ServerSocket serverSocket, String message, Auction auction) {
            ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
            UserCenter.getIncstance().editAuction(auction, chatMessage);
            for (DataOutputStream dataOutputStream : allClients.get(serverSocket)) {
                try {
                    dataOutputStream.writeUTF(new Gson().toJson(auction));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public synchronized void createNewAuctionRequest(String message, DataOutputStream dataOutputStream) {
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("AddAuction", message));
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Successful", "The Auction Registered For Manager's Confirmation"), dataOutputStream);
        System.out.println("bbbbbbbbbbbbbbbbbb123123123123123");
    }

    public synchronized void createAuction(Auction auction, DataOutputStream dataOutputStream) {
        auction.setAuctionId(getAuctionIdForCreateAuction());
        UserCenter.getIncstance().addAuction(auction, dataOutputStream);
    }

    public synchronized int getSocketPort(Auction auction) {
        return auctionsChatBoxes.get(auction.getAuctionId()).getLocalPort();
    }

    public synchronized String getAuctionIdForCreateAuction() {
        DataBase.getInstance().setLastAuctionIdFromDataBase();
        if (lastAuctionId == null) {
            lastAuctionId = "";
        }
        this.lastAuctionId = "@A" + (Integer.parseInt(lastAuctionId.substring(2)) + 1);
        DataBase.getInstance().replaceAuctionId(lastAuctionId);
        return this.lastAuctionId;
    }

    public void setLastAuctionId(String lastAuctionId) {
        this.lastAuctionId = lastAuctionId;
    }
}
