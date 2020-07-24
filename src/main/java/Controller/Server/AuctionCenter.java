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
    private HashMap<String, ArrayList<DataOutputStream>> allClients = new HashMap<>();
    private ArrayList<Auction> allAuctions;
    private ServerSocket serverSocket;

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
        System.out.println(allAuctions.size());
        for (Auction auction : allAuctions) {
            if (new Date().after(auction.getStartTime()) && !allClients.containsKey(auction.getAuctionId())) {
                allClients.put(auction.getAuctionId(), new ArrayList<>());
                System.out.println("auction controlling: " + 1);
                if (-new Date().getTime() + auction.getEndTime().getTime() > 0)
                    new Thread(() -> {
                        try {
                            Thread.sleep(-new Date().getTime() + auction.getEndTime().getTime());
                            if (allAuctions.contains(auction)) {
                                allAuctions.remove(auction);
                            }
                            UserCenter.getIncstance().passTime();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
            }
        }
        AuctionThread auctionThread = new AuctionThread();
        auctionThread.start();
    }

    class AuctionThread extends Thread {
        private ServerSocket serverSocket;

        public AuctionThread() {
        }

        @Override
        public void run() {
            serverSocket = null;
            try {
                serverSocket = new ServerSocket(12000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                Socket socket = null;
                try {
                    UserCenter.getIncstance().passTime();
                    socket = serverSocket.accept();
                    System.out.println("auctionSide: clientConnected");
                    DataOutputStream dataOutputStream1 = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    Socket finalSocket = socket;
                    Socket finalSocket1 = socket;
                    new Thread(() -> {
                        DataInputStream dataInputStream = null;
                        try {
                            dataInputStream = new DataInputStream(new BufferedInputStream(finalSocket.getInputStream()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String message = null;
                        try {
                            message = dataInputStream.readUTF();
                            System.out.println("auctionSide: messageReceived: " + message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        DataOutputStream dataOutputStream11 = null;
                        try {
                            dataOutputStream11 = new DataOutputStream(new BufferedOutputStream(finalSocket1.getOutputStream()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(message);
                        if (!message.isEmpty() && message.startsWith("@Auction@")) {
                            DataInputStream finalDataInputStream = dataInputStream;
                            DataOutputStream finalDataOutputStream1 = dataOutputStream11;
                            try {
                                dataOutputStream11.writeUTF(new Gson().toJson(getAuctionWithId(message.substring(9))));
                                dataOutputStream11.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            allAuctions = UserCenter.getIncstance().getAllAuctions();
                            new Thread(() -> {
                                while (true) {
                                    try {
                                        String message1 = finalDataInputStream.readUTF();
                                        addNewMessage(message1);
                                        Auction auction = getAuctionWithId(new Gson().fromJson(message1, ChatMessage.class).getObjectId());

                                        if (!allClients.get(auction.getAuctionId()).contains(finalDataOutputStream1)) {
                                            allClients.get(auction.getAuctionId()).add(finalDataOutputStream1);
                                        }
                                        for (DataOutputStream dataOutputStream : allClients.get(auction.getAuctionId())) {
                                            try {
                                                System.out.println("auctionSide: message has been processed " + auction.getChatMessages().size());
                                                dataOutputStream.writeUTF(new Gson().toJson(auction));
                                                dataOutputStream.flush();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else if (!message.isEmpty() && !new Gson().fromJson(message, ChatMessage.class).getContent().startsWith("@Auction@")) {

                        }

                    }).start();
                } catch (IOException e) {
                    break;
                }
            }
        }

        public synchronized void addNewMessage(String message) {
            ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
            Auction auction = getAuctionWithId(chatMessage.getObjectId());
            UserCenter.getIncstance().editAuction(auction, chatMessage);
        }

    }

    private Auction getAuctionWithId(String objectId) {
        for (Auction auction : allAuctions) {
            if (auction.getAuctionId().equals(objectId)) {
                return auction;
            }
        }
        return null;
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
        return 123124;
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
