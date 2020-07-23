package Controller.Server;

import Models.BuyLog;
import Models.DiscountCode;
import Models.Product.Cart;
import Models.Product.Product;
import Models.ReceivingStatus;
import Models.SellLog;
import Models.UserAccount.Customer;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CartCenter {
    private static CartCenter cartCenter;
    String lastLogId = "";
    private double wage,atLeastAmount;
    public void setLastLogId(String lastLogId) {
        this.lastLogId = lastLogId;
    }

    public double getAtLeastAmount() {
        return atLeastAmount;
    }

    public void setAtLeastAmount(double atLeastAmount) {
        this.atLeastAmount = atLeastAmount;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public static CartCenter getInstance() {
        if (cartCenter == null) {
            synchronized (CartCenter.class) {
                if (cartCenter == null) {
                    cartCenter = new CartCenter();
                }
            }
        }
        return cartCenter;
    }

    public synchronized String makeLogID() {
        lastLogId = "@l" + (Integer.parseInt(lastLogId.substring(2, 7)) + 1);
        DataBase.getInstance().replaceLogId(lastLogId);
        return lastLogId;
    }

    public synchronized void pay(Cart cart) {
        Customer customer = UserCenter.getIncstance().findCustomerWithUsername(cart.getCustomerID());
        double price = cart.getTotalPrice(), reducedPrice = 0;
        DiscountCode discountCode = cart.getDiscountCode();
        if (discountCode != null) {
            if (price * ((double) discountCode.getDiscountPercent() / 100) < discountCode.getMaxDiscountAmount()) {
                reducedPrice = price * ((double) discountCode.getDiscountPercent() / 100);
                price -= reducedPrice;
            } else {
                reducedPrice = discountCode.getMaxDiscountAmount();
                price -= reducedPrice;

            }
        }
        if (customer.getCredit() - price >= atLeastAmount) {
            customer.setCredit(customer.getCredit() - price);
            customer.setTotalBuyAmount(customer.getTotalBuyAmount() + price);
            if (customer.getTotalBuyAmount() > 1000000) {
                makeDiscountCodeForGift(customer.getUsername());
            }
            ArrayList<String> sellers = new ArrayList<>();
            for (Product product : cart.getAllproduct()) {
                ProductCenter.getInstance().findProductWithID(product.getProductId()).addToAllBuyers(UserCenter.getIncstance().findCustomerWithUsername(cart.getCustomerID()));
                UserCenter.getIncstance().findSellerWithUsername(product.getSeller()).findProductWithID(product.getProductId()).addToAllBuyers(customer);
                ProductCenter.getInstance().decreaseProductCount(product.getProductId(), cart.getCountOfEachProduct().get(product.getProductId()));
                System.out.println("in CART: " + product.getProductName() + cart.getCountOfEachProduct().get(product.getProductId()));
                if (!sellers.contains(product.getSeller())) {
                    sellers.add(product.getSeller());
                }
            }
            String sellerAndProducts = "";
            for (String seller : sellers) {
                ArrayList<Product> allProducts = new ArrayList<>();
                sellerAndProducts += seller + ": ";
                double cost = 0;
                for (Product product : cart.getAllproduct()) {
                    if (seller.equals(product.getSeller())) {
                        allProducts.add(product);
                        cost += product.getCostAfterOff();
                        sellerAndProducts += product.getProductId() + " ";
                    }
                }
                UserCenter.getIncstance().findSellerWithUsername(seller).setCredit(UserCenter.getIncstance().findSellerWithUsername(seller).getCredit() + cost);
                sellerAndProducts += "\n";
                SellLog sellLog = new SellLog(makeLogID(), new Date(), seller, customer.getUsername(), allProducts, ReceivingStatus.DeliveredToThePost, reducedPrice);
                sellLog.setPrice(price + reducedPrice);
                UserCenter.getIncstance().findSellerWithUsername(seller).addLog(sellLog);
            }
            BuyLog buyLog = new BuyLog(makeLogID(), price, new Date(), customer.getUsername(), sellerAndProducts, cart.getAllproduct(), ReceivingStatus.DeliveredToThePost, reducedPrice);
            buyLog.setPrice(price);
            customer.addLog(buyLog);
            DataBase.getInstance().updateAllSellers(new Gson().toJson(UserCenter.getIncstance().getAllSeller()));
            DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
            DataBase.getInstance().updateAllProducts(new Gson().toJson(ProductCenter.getInstance().getAllProducts()));
            DataBase.getInstance().updateAllDiscountCode(new Gson().toJson(DiscountCodeCenter.getIncstance().getAllDiscountCodes()));
            DataBase.getInstance().updateAllOffers(new Gson().toJson(OffCenter.getInstance().getAllOffers()));
        } else {
        }
    }

    public synchronized void pay(Cart cart, DataOutputStream dataOutputStream) {
        System.out.println(UserCenter.getIncstance().findCustomerWithUsername(cart.getCustomerID()));
        Customer customer = UserCenter.getIncstance().findCustomerWithUsername(cart.getCustomerID());
        double price = cart.getTotalPrice(), reducedPrice = 0;
        DiscountCode discountCode = cart.getDiscountCode();
        if (discountCode != null) {
            if (price * ((double) discountCode.getDiscountPercent() / 100) < discountCode.getMaxDiscountAmount()) {
                reducedPrice = price * ((double) discountCode.getDiscountPercent() / 100);
                price -= reducedPrice;
            } else {
                reducedPrice = discountCode.getMaxDiscountAmount();
                price -= reducedPrice;

            }
        }
        if (customer.getCredit() - price >= 0) {
            if (discountCode != null) {
                DiscountCodeCenter.getIncstance().usedDiscountCode(discountCode.getDiscountCodeID(), customer.getUsername(), dataOutputStream);
            }
            customer.setCredit(customer.getCredit() - price);
            customer.setTotalBuyAmount(customer.getTotalBuyAmount() + price);
            if (customer.getTotalBuyAmount() > 1000000) {
                makeDiscountCodeForGift(customer.getUsername());
            }
            ArrayList<String> sellers = new ArrayList<>();
            for (Product product : cart.getAllproduct()) {
                ProductCenter.getInstance().findProductWithID(product.getProductId()).addToAllBuyers(UserCenter.getIncstance().findCustomerWithUsername(cart.getCustomerID()));
                UserCenter.getIncstance().findSellerWithUsername(product.getSeller()).findProductWithID(product.getProductId()).addToAllBuyers(customer);
                ProductCenter.getInstance().decreaseProductCount(product.getProductId(), cart.getCountOfEachProduct().get(product.getProductId()));
                System.out.println("in CART: " + product.getProductName() + cart.getCountOfEachProduct().get(product.getProductId()));
                if (!sellers.contains(product.getSeller())) {
                    sellers.add(product.getSeller());
                }
            }
            String sellerAndProducts = "";
            for (String seller : sellers) {
                ArrayList<Product> allProducts = new ArrayList<>();
                sellerAndProducts += seller + ": ";
                double cost = 0;
                for (Product product : cart.getAllproduct()) {
                    if (seller.equals(product.getSeller())) {
                        allProducts.add(product);
                        cost += product.getCostAfterOff();
                        sellerAndProducts += product.getProductId() + " ";
                    }
                }
                UserCenter.getIncstance().findSellerWithUsername(seller).setCredit(UserCenter.getIncstance().findSellerWithUsername(seller).getCredit() + (cost-wage*cost/100));
                sellerAndProducts += "\n";
                SellLog sellLog = new SellLog(makeLogID(), new Date(), seller, customer.getUsername(), allProducts, ReceivingStatus.DeliveredToThePost, reducedPrice);
                sellLog.setPrice(price + reducedPrice);
                UserCenter.getIncstance().findSellerWithUsername(seller).addLog(sellLog);
            }
            BuyLog buyLog = new BuyLog(makeLogID(), price, new Date(), customer.getUsername(), sellerAndProducts, cart.getAllproduct(), ReceivingStatus.DeliveredToThePost, reducedPrice);
            buyLog.setPrice(price);
            customer.addLog(buyLog);
            DataBase.getInstance().updateAllSellers(new Gson().toJson(UserCenter.getIncstance().getAllSeller()));
            DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
            DataBase.getInstance().updateAllProducts(new Gson().toJson(ProductCenter.getInstance().getAllProducts()));
            DataBase.getInstance().updateAllDiscountCode(new Gson().toJson(DiscountCodeCenter.getIncstance().getAllDiscountCodes()));
            DataBase.getInstance().updateAllOffers(new Gson().toJson(OffCenter.getInstance().getAllOffers()));
            ServerController.getInstance().sendMessageToClient("@payed@" + new Gson().toJson(customer), dataOutputStream);
        } else {
            ServerController.getInstance().sendMessageToClient("@Error@You don't have enough credit", dataOutputStream);
        }
    }

    public synchronized void makeDiscountCodeForGift(String username) {
        Date endDate = new Date();
        endDate.setYear(60);
        ArrayList<String> alluser = new ArrayList<>();
        alluser.add(username);
        HashMap<String, Integer> maxusingTime = new HashMap<String, Integer>();
        maxusingTime.put(username, 1);
        DiscountCode discountCodeForGift = new DiscountCode(new Date(), endDate, alluser, 90, 20000, maxusingTime, maxusingTime);
        DiscountCodeCenter.getIncstance().createDiscountCodeForGift(discountCodeForGift);

    }
}
