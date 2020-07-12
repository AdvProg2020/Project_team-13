package Controller.Server;

import Models.ChatMessage;
import Models.Offer;
import Models.Product.Product;
import Models.Request;
import Models.UserAccount.*;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class UserCenter {
    private static UserCenter userCenter;
    private ArrayList<Customer> allCustomer = new ArrayList<>();
    private ArrayList<Seller> allSeller = new ArrayList<>();
    private ArrayList<Manager> allManager = new ArrayList<>();
    private ArrayList<Supporter> allSupporter = new ArrayList<>();

    private UserCenter() {

    }

    public synchronized void addCommercial(Product product, DataOutputStream dataOutputStream) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equalsIgnoreCase(product.getSeller())) {
                seller.setCommercializedProduct(product.getProductId());
                ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@" + "Request accepted successfully", dataOutputStream);
                break;
            }
        }
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
    }

    public static UserCenter getIncstance() {
        if (userCenter == null) {
            synchronized (UserCenter.class) {
                if (userCenter == null) {
                    userCenter = new UserCenter();
                }
            }
        }
        return userCenter;
    }

    public synchronized void reduceSellerCreditForAnAdd(Product product) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(product.getSeller())) {
                seller.setCredit(seller.getCredit() - 50);
                DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
            }
        }
    }

    public synchronized void increaseSellerCreditForAnAdd(Product product) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(product.getSeller())) {
                seller.setCredit(seller.getCredit() + 50);
                DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
            }
        }
    }

    public boolean isThereUserWithThisUsername(String username) {
        for (Customer customer : allCustomer) {
            if (customer.getUsername().equals(username)) {
                return true;
            }
        }
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(username)) {
                return true;
            }
        }
        for (Manager manager : allManager) {
            if (manager.getUsername().equals(username)) {
                return true;
            }
        }
        for (Supporter supporter : allSupporter) {
            if (supporter.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Seller> getAllSeller() {
        return allSeller;
    }

    public UserAccount getUserWithUsername(String username) {
        for (Customer customer : allCustomer) {
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(username)) {
                return seller;
            }
        }
        for (Manager manager : allManager) {
            if (manager.getUsername().equals(username)) {
                return manager;
            }
        }
        for (Supporter supporter : allSupporter) {
            if (supporter.getUsername().equals(username)) {
                return supporter;
            }
        }
        return null;
    }

    public synchronized void setAllCustomer(ArrayList<Customer> allCustomer) {
        this.allCustomer = allCustomer;
    }

    public synchronized void setAllSeller(ArrayList<Seller> allSeller) {
        this.allSeller = allSeller;
    }

    public synchronized void setAllManager(ArrayList<Manager> allManager) {
        this.allManager = allManager;
    }

    public void setAllSupporter(ArrayList<Supporter> allSupporter) {
        this.allSupporter = allSupporter;
    }

    public synchronized void createNewUserAccount(String json, DataOutputStream dataOutputStream) {
        Gson gson = new Gson();
        if (json.contains("@Customer")) {
            Customer customer = gson.fromJson(json, Customer.class);
            if (!isThereUserWithThisUsername(customer.getUsername())) {
                allCustomer.add(customer);
                String arrayData = gson.toJson(allCustomer);
                DataBase.getInstance().updateAllCustomers(arrayData);
                ServerController.getInstance().sendMessageToClient("@Successfulrc@" + new Gson().toJson(customer), dataOutputStream);
            } else {
                ServerController.getInstance().sendMessageToClient("@Error@There is a User With this username", dataOutputStream);
            }
        } else if (json.contains("@Seller")) {
            Seller seller = gson.fromJson(json, Seller.class);
            if (!isThereUserWithThisUsername(seller.getUsername())) {
                allSeller.add(seller);
                String arrayData = gson.toJson(allSeller);
                DataBase.getInstance().updateAllSellers(arrayData);
                Request request = RequestCenter.getIncstance().makeRequest("AcceptSellerAccount", gson.toJson(seller));
                RequestCenter.getIncstance().addRequest(request);
                ServerController.getInstance().sendMessageToClient("@Successful@Register was sent to Manager for review", dataOutputStream);
            } else {
                ServerController.getInstance().sendMessageToClient("@Error@There is a User With this username", dataOutputStream);
            }
        } else if (json.contains("@Manager")) {
            if (allManager.size() == 0) {
                Manager manager = gson.fromJson(json, Manager.class);
                if (!isThereUserWithThisUsername(manager.getUsername())) {
                    allManager.add(manager);
                    String arrayData = gson.toJson(allManager);
                    DataBase.getInstance().updateAllManagers(arrayData);
                    ServerController.getInstance().sendMessageToClient("@Successful@Register Successful", dataOutputStream);
                } else {
                    ServerController.getInstance().sendMessageToClient("@Error@ is a User With this username", dataOutputStream);
                }
            } else {
                ServerController.getInstance().sendMessageToClient("@Error@You can not Register as Manager", dataOutputStream);
            }
        }
    }

//    public void sendChat(String json , DataOutputStream dataOutputStream1) {
//        System.out.println("inja");
//        ChatMessage chatMessage= new Gson().fromJson(json,ChatMessage.class);
//        DataOutputStream dataOutputStream=ServerController.getInstance().findDataStreamWithUsername(chatMessage.getReceiverUsername());
//        if(dataOutputStream!=null){
//            ServerController.getInstance().sendMessageToClient("@getChatMessage@"+new Gson().toJson(chatMessage),dataOutputStream);
//        }
//        ServerController.getInstance().sendMessageToClient("@successfulChat@",dataOutputStream1);
//    }

    public void login(String username, String password, DataOutputStream dataOutputStream) {
        Gson gson = new Gson();
        if (isThereUserWithThisUsername(username)) {
            UserAccount userAccount = getUserWithUsername(username);
            if (userAccount.getPassword().equals(password)) {
             //   if (ServerController.getInstance().findDataStreamWithUsername(userAccount.getUsername()) == null) {
             //       ServerController.getInstance().getOnlineUsers().put(dataOutputStream, userAccount);
            //    }
                switch (userAccount.getType()) {
                    case "@Customer": {
                        String user = gson.toJson(userAccount);
                        ServerController.getInstance().sendMessageToClient("@Login as Customer@" + user, dataOutputStream);
                        break;
                    }
                    case "@Seller": {
                        String user = gson.toJson(userAccount);
                        if (((Seller) userAccount).isAccepted())
                            ServerController.getInstance().sendMessageToClient("@Login as Seller@" + user, dataOutputStream);
                        else
                            ServerController.getInstance().sendMessageToClient("@Error@" + "Seller registration request hasn't been accepted", dataOutputStream);
                        break;
                    }
                    case "@Manager": {
                        String user = gson.toJson(userAccount);
                        ServerController.getInstance().sendMessageToClient("@Login as Manager@" + user, dataOutputStream);
                        break;
                    }
                    case "@Supporter": {
                        String user = gson.toJson(userAccount);
                        ServerController.getInstance().sendMessageToClient("@Login as Supporter@" + user, dataOutputStream);
                        break;
                    }
                }
            } else {
                ServerController.getInstance().sendMessageToClient("@Error@Password is incorrect", dataOutputStream);
            }
        } else {
            ServerController.getInstance().sendMessageToClient("@Error@There is no User With this username", dataOutputStream);
        }
    }

    public synchronized void addProductToSeller(Product product) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(product.getSeller())) {
                seller.addProduct(product);
                break;
            }
        }
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
    }

    public synchronized void addOfferToSeller(Offer offer) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(offer.getSeller())) {
                seller.addOffer(offer);
                break;
            }
        }
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
    }

    public synchronized void updateProductOfferInSeller(Product product) {
        firstTag:
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(product.getSeller())) {
                for (Product product1 : seller.getAllProducts()) {
                    if (product1.getProductId().equals(product.getProductId())) {
                        seller.getAllProducts().set(seller.getAllProducts().indexOf(product1), product);
                        break firstTag;
                    }
                }
                break;
            }
        }
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
    }

    public synchronized void editOfferForSeller(Offer newOffer) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(newOffer.getSeller())) {
                seller.editOffer(newOffer);
                break;
            }
        }
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
    }

    public synchronized void removeOfferForSeller(Offer offer) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(offer.getSeller())) {
                seller.removeOffer(offer);
                break;
            }
        }
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
    }

    public synchronized boolean canAcceptSellerRegister(String username, DataOutputStream dataOutputStream) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(username)) {
                seller.setAccepted(true);
                DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
                return true;
            }
        }
        ServerController.getInstance().sendMessageToClient("@Error@there is no seller with this username", dataOutputStream);
        return false;
    }

    public synchronized void removeCustomer(String username, DataOutputStream dataOutputStream) {
        for (Customer customer : allCustomer) {
            if (customer.getUsername().equals(username)) {
                allCustomer.remove(customer);
                DataBase.getInstance().updateAllCustomers(new Gson().toJson(allCustomer));
                ServerController.getInstance().sendMessageToClient("@Successful@delete user successfully", dataOutputStream);
                return;
            }
        }
        ServerController.getInstance().sendMessageToClient("@Error@there is no user with this username", dataOutputStream);
    }

    public void removeProductFromSellerProductList(Product product) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(product.getSeller())) {
                seller.removeProduct(product.getProductId());
                if (product.getOffer() != null) {
                    seller.getOfferById(product.getOffer().getOfferId()).getProducts().remove(product.getProductId());
                }
            }
        }
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
    }

    public synchronized void editProductInSeller(Product product) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(product.getSeller())) {
                seller.editProduct(product);
            }
        }
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
    }

    public synchronized void removeSeller(String username, DataOutputStream dataOutputStream) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(username)) {
                allSeller.remove(seller);
                for (Product product : seller.getAllProducts()) {
                    ProductCenter.getInstance().removeProduct(ProductCenter.getInstance().findProductWithID(product.getProductId()), dataOutputStream);
                }
                DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
                DataBase.getInstance().updateAllProducts(new Gson().toJson(ProductCenter.getInstance().getAllProducts()));
                ServerController.getInstance().sendMessageToClient("@Successful@delete user successfully", dataOutputStream);
                return;
            }
        }
        ServerController.getInstance().sendMessageToClient("@Error@there is no user with this username", dataOutputStream);
    }

    public synchronized void removeManager(String username, DataOutputStream dataOutputStream) {
        for (Manager manager : allManager) {
            if (manager.getUsername().equals(username)) {
                allManager.remove(manager);
                DataBase.getInstance().updateAllManagers(new Gson().toJson(allManager));
                ServerController.getInstance().sendMessageToClient("@Successful@delete user successfully", dataOutputStream);
                return;
            }
        }
        ServerController.getInstance().sendMessageToClient("@Error@there is no user with this username", dataOutputStream);
    }

    public synchronized void createManagerProfile(String json, DataOutputStream dataOutputStream) {
        Manager manager = new Gson().fromJson(json, Manager.class);
        if (!isThereUserWithThisUsername(manager.getUsername())) {
            allManager.add(manager);
            String arrayData = new Gson().toJson(allManager);
            DataBase.getInstance().updateAllManagers(arrayData);
            ServerController.getInstance().sendMessageToClient("@Successful@Register Successful", dataOutputStream);
        } else {
            ServerController.getInstance().sendMessageToClient("@Error@There is a User With this username", dataOutputStream);
        }
    }

    public synchronized void createSupporterProfile(String json, DataOutputStream dataOutputStream) {
        Supporter supporter = new Gson().fromJson(json, Supporter.class);
        if (!isThereUserWithThisUsername(supporter.getUsername())) {
            allSupporter.add(supporter);
            String arrayData = new Gson().toJson(allSupporter);
            DataBase.getInstance().updateAllSupporter(arrayData);
            ServerController.getInstance().sendMessageToClient("@Successful@Register Successful", dataOutputStream);
        } else {
            ServerController.getInstance().sendMessageToClient("@Error@There is a User With this username", dataOutputStream);
        }
    }

    public ArrayList<Customer> getAllCustomer() {
        return allCustomer;
    }

    public synchronized void editManager(Manager manager, DataOutputStream dataOutputStream) {
        int index = allManager.indexOf(findManagerWithUsername(manager.getUsername()));
        allManager.set(index, manager);
        DataBase.getInstance().updateAllManagers(new Gson().toJson(allManager));
        ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@user successfully edited", dataOutputStream);
    }

    public synchronized void editCustomer(Customer customer, DataOutputStream dataOutputStream) {
        int index = allCustomer.indexOf(findCustomerWithUsername(customer.getUsername()));
        allCustomer.set(index, customer);
        DataBase.getInstance().updateAllCustomers(new Gson().toJson(allCustomer));
        ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@user successfully edited", dataOutputStream);
    }

    public synchronized void editSeller(Seller seller, DataOutputStream dataOutputStream) {
        int index = allSeller.indexOf(findSellerWithUsername(seller.getUsername()));
        allSeller.set(index, seller);
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
        ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@user successfully edited", dataOutputStream);
    }

    public Seller findSellerWithUsername(String username) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(username)) {
                return seller;
            }
        }
        return null;
    }

    public Customer findCustomerWithUsername(String username) {
        for (Customer customer : allCustomer) {
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }
        return null;
    }

    public Manager findManagerWithUsername(String username) {
        for (Manager manager : allManager) {
            if (manager.getUsername().equals(username)) {
                return manager;
            }
        }
        return null;
    }

    public ArrayList<Manager> getAllManager() {
        return allManager;
    }

}
