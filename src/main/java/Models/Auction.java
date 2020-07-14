package Models;

import Models.Product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Auction {
    private Date startTime;
    private Date endTime;
    private Product product;
    private String auctionId;
    private String sellerId;
    private HashMap<String, Double> offers = new HashMap<>();
    private ArrayList<ChatMessage> chatMessages= new ArrayList<>();


    public Auction(Date startTime, Date endTime, Product product, String sellerId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.product = product;
        this.sellerId = sellerId;
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void putNewMessage(ChatMessage chatMessage) {
        if(chatMessages==null) {
            chatMessages = new ArrayList<>();
        }
        chatMessages.add(chatMessage);
    }

    public Product getProduct() {
        return product;
    }

    public String getBestOfferUser() {
        if (offers == null) {
            offers = new HashMap<>();
        }
        if (offers.isEmpty()) {
            return "";
        } else {
            String user1="" ;
            double maxAmount = 0;
            for (String user : offers.keySet()) {
                if (offers.get(user) > maxAmount) {
                    user1=user;
                    maxAmount = offers.get(user);
                }
            }
            return user1;
        }
    }

    public double getBestOffer() {
        if (offers == null) {
            offers = new HashMap<>();
        }
        if (offers.isEmpty()) {
            return product.getProductCost();
        } else {
            double maxAmount = 0;
            for (String user : offers.keySet()) {
                if (offers.get(user) > maxAmount) {
                    maxAmount = offers.get(user);
                }
            }
            return maxAmount;
        }
    }

    public HashMap<String, Double> getOffers() {
        return offers;
    }

    public void addNewOffer(String userId, double offer) {
        if (offers == null) {
            offers = new HashMap<>();
        }
        if (offers.keySet().contains(userId)) {
            offers.replace(userId, offer);
        } else {
            offers.put(userId, offer);
        }
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
