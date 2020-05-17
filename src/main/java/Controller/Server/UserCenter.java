package Controller.Server;

import Models.Offer;
import Models.Product.Product;
import Models.Request;
import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import Models.UserAccount.UserAccount;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class UserCenter {
    private static UserCenter userCenter;
    private ArrayList<Customer> allCustomer = new ArrayList<>();
    private ArrayList<Seller> allSeller = new ArrayList<>();
    private ArrayList<Manager> allManager = new ArrayList<>();

    private UserCenter() {

    }
public void decreaseProductCount(String productID,String username){
    for (Seller seller : allSeller) {
        if(seller.getUsername().equals(username)){

        }
    }
}
    public static UserCenter getIncstance() {
        if (userCenter == null) {
            userCenter = new UserCenter();
        }
        return userCenter;
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
        return null;
    }

    public void setAllCustomer(ArrayList<Customer> allCustomer) {
        this.allCustomer = allCustomer;
    }

    public void setAllSeller(ArrayList<Seller> allSeller) {
        this.allSeller = allSeller;
    }

    public void setAllManager(ArrayList<Manager> allManager) {
        this.allManager = allManager;
    }

    public void createNewUserAccount(String json) {
        Gson gson = new Gson();
        if (json.contains("@Customer")) {
            Customer customer = gson.fromJson(json, Customer.class);
            if (!isThereUserWithThisUsername(customer.getUsername())) {
                allCustomer.add(customer);
                String arrayData = gson.toJson(allCustomer);
                DataBase.getInstance().updateAllCustomers(arrayData);
                ServerController.getInstance().sendMessageToClient("@Successful@Register Successful");
            } else {
                ServerController.getInstance().sendMessageToClient("@Error@There is a User With this username");
            }
        } else if (json.contains("@Seller")) {
            Seller seller = gson.fromJson(json, Seller.class);
            if (!isThereUserWithThisUsername(seller.getUsername())) {
                allSeller.add(seller);
                String arrayData = gson.toJson(allSeller);
                DataBase.getInstance().updateAllSellers(arrayData);
                Request request = RequestCenter.getIncstance().makeRequest("AcceptSellerAccount", gson.toJson(seller));
                RequestCenter.getIncstance().addRequest(request);
                ServerController.getInstance().sendMessageToClient("@Successful@Register was sent to Manager for review");
            } else {
                ServerController.getInstance().sendMessageToClient("@Error@There is a User With this username");
            }
        } else if (json.contains("@Manager")) {
            if (allManager.size() == 0) {
                Manager manager = gson.fromJson(json, Manager.class);
                if (!isThereUserWithThisUsername(manager.getUsername())) {
                    allManager.add(manager);
                    String arrayData = gson.toJson(allManager);
                    DataBase.getInstance().updateAllManagers(arrayData);
                    ServerController.getInstance().sendMessageToClient("@Successful@Register Successful");
                } else {
                    ServerController.getInstance().sendMessageToClient("@Error@There is a User With this username");
                }
            } else {
                ServerController.getInstance().sendMessageToClient("@Error@You can not Register as Manager");
            }
        }
    }

    public void login(String username, String password) {
        Gson gson = new Gson();
        if (isThereUserWithThisUsername(username)) {
            UserAccount userAccount = getUserWithUsername(username);
            if (userAccount.getPassword().equals(password)) {
                switch (userAccount.getType()) {
                    case "@Customer": {
                        String user = gson.toJson((Customer) userAccount);
                        ServerController.getInstance().sendMessageToClient("@Login as Customer@" + user);
                        break;
                    }
                    case "@Seller": {
                        String user = gson.toJson((Seller) userAccount);
                        ServerController.getInstance().sendMessageToClient("@Login as Seller@" + user);
                        break;
                    }
                    case "@Manager": {
                        String user = gson.toJson((Manager) userAccount);
                        ServerController.getInstance().sendMessageToClient("@Login as Manager@" + user);
                        break;
                    }
                }
            } else {
                ServerController.getInstance().sendMessageToClient("@Error@Password is incorrect");
            }
        } else {
            ServerController.getInstance().sendMessageToClient("@Error@There is no User With this username");
        }
    }

    public void addProductToSeller(Product product) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(product.getSeller())) {
                seller.addProduct(product);
                break;
            }
        }
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
    }

    public void addOfferToSeller(Offer offer){
        for (Seller seller : allSeller) {
            if(seller.getUsername().equals(offer.getSeller())){
                seller.addOffer(offer);
                break;
            }
        }
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
    }

    public void editOfferForSeller(Offer newOffer){
        for (Seller seller : allSeller) {
            if(seller.getUsername().equals(newOffer.getSeller())){
                seller.editOffer(newOffer);
                break;
            }
        }
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
    }

    public boolean canAcceptSellerRegister(String username) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(username)) {
                seller.setAccepted(true);
                DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
                return true;
            }
        }
        ServerController.getInstance().sendMessageToClient("@Error@there is no seller with this username");
        return false;
    }

    public void removeCustomer(String username) {
        for (Customer customer : allCustomer) {
            if (customer.getUsername().equals(username)) {
                allCustomer.remove(customer);
                DataBase.getInstance().updateAllCustomers(new Gson().toJson(allCustomer));
                ServerController.getInstance().sendMessageToClient("@Successful@delete user successfully");
                return;
            }
        }
        ServerController.getInstance().sendMessageToClient("@Error@there is no user with this username");
    }

    public void removeProductFromSellerProductList(Product product) {
        for (Seller seller : allSeller) {
            if(seller.getUsername().equals(product.getSeller())) {
                seller.removeProduct(product.getProductId());
            }
        }
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
    }

    public void removeSeller(String username) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(username)) {
                allSeller.remove(seller);
                DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
                ServerController.getInstance().sendMessageToClient("@Successful@delete user successfully");
                return;
            }
        }
        ServerController.getInstance().sendMessageToClient("@Error@there is no user with this username");
    }

    public void removeManager(String username) {
        for (Manager manager : allManager) {
            if (manager.getUsername().equals(username)) {
                allManager.remove(manager);
                DataBase.getInstance().updateAllManagers(new Gson().toJson(allManager));
                ServerController.getInstance().sendMessageToClient("@Successful@delete user successfully");
                return;
            }
        }
        ServerController.getInstance().sendMessageToClient("@Error@there is no user with this username");
    }

    public void createManagerProfile(String json) {
        Manager manager = new Gson().fromJson(json, Manager.class);
        if (!isThereUserWithThisUsername(manager.getUsername())) {
            allManager.add(manager);
            String arrayData = new Gson().toJson(allManager);
            DataBase.getInstance().updateAllManagers(arrayData);
            ServerController.getInstance().sendMessageToClient("@Successful@Register Successful");
        } else {
            ServerController.getInstance().sendMessageToClient("@Error@There is a User With this username");
        }
    }

    public ArrayList<Customer> getAllCustomer() {
        return allCustomer;
    }

    public void editManager(Manager manager){
        int index=allManager.indexOf(findManagerWithUsername(manager.getUsername()));
        allManager.remove(findManagerWithUsername(manager.getUsername()));
        allManager.add(index,manager);
        DataBase.getInstance().updateAllManagers(new Gson().toJson(allManager));
        ServerController.getInstance().sendMessageToClient("@Successful@user successfully edited");
    }
    public void editCustomer(Customer customer){
        int index=allCustomer.indexOf(findCustomerWithUsername(customer.getUsername()));
        allCustomer.remove(findCustomerWithUsername(customer.getUsername()));
        allCustomer.add(index,customer);
        DataBase.getInstance().updateAllCustomers(new Gson().toJson(allCustomer));
        ServerController.getInstance().sendMessageToClient("@Successful@user successfully edited");
    }
    public void editSeller(Seller seller){
        int index=allSeller.indexOf(findSellerWithUsername(seller.getUsername()));
        allSeller.remove(findCustomerWithUsername(seller.getUsername()));
        allSeller.add(index,seller);
        DataBase.getInstance().updateAllSellers(new Gson().toJson(allSeller));
        ServerController.getInstance().sendMessageToClient("@Successful@user successfully edited");
    }
    public Seller findSellerWithUsername(String username){
        for (Seller seller : allSeller) {
            if(seller.getUsername().equals(username)){
                return seller;
            }
        }
        return null;
    }
    public Customer findCustomerWithUsername(String username){
        for (Customer customer : allCustomer) {
            if(customer.getUsername().equals(username)){
                return customer;
            }
        }
        return null;
    }
    public Manager findManagerWithUsername(String username){
        for (Manager manager : allManager) {
            if(manager.getUsername().equals(username)){
                return manager;
            }
        }
        return null;
    }
}
