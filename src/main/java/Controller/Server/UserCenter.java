package Controller.Server;

import Models.Request;
import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import Models.UserAccount.UserAccount;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UserCenter {
    private static UserCenter userCenter;
    private ArrayList<Customer> allCustomer = new ArrayList<>();
    private ArrayList<Seller> allSeller = new ArrayList<>();
    private ArrayList<Manager> allManager = new ArrayList<>();

    private UserCenter() {

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
                DataBase.getIncstance().updateAllCustomers(arrayData);
                ServerController.getIncstance().sendMessageToClient("@Successful@Register Successful");
            } else {
                ServerController.getIncstance().sendMessageToClient("@Error@There is a User With this username");
            }
        } else if (json.contains("@Seller")) {
            Seller seller = gson.fromJson(json, Seller.class);
            if (!isThereUserWithThisUsername(seller.getUsername())) {
                allSeller.add(seller);
                String arrayData = gson.toJson(allSeller);
                DataBase.getIncstance().updateAllSellers(arrayData);
                Request request = RequestCenter.getIncstance().makeRequest("AcceptSellerAccount", gson.toJson(seller));
                RequestCenter.getIncstance().addRequest(request);
                ServerController.getIncstance().sendMessageToClient("@Successful@Register was sended to Manager for review");
            } else {
                ServerController.getIncstance().sendMessageToClient("@Error@There is a User With this username");
            }
        } else if (json.contains("@Manager")) {
            if (allManager.size() == 0) {
                Manager manager = gson.fromJson(json, Manager.class);
                if (!isThereUserWithThisUsername(manager.getUsername())) {
                    allManager.add(manager);
                    String arrayData = gson.toJson(allManager);
                    DataBase.getIncstance().updateAllManagers(arrayData);
                    ServerController.getIncstance().sendMessageToClient("@Successful@Register Successful");
                } else {
                    ServerController.getIncstance().sendMessageToClient("@Error@There is a User With this username");
                }
            } else {
                ServerController.getIncstance().sendMessageToClient("@Error@You can not Register as Manager");
            }
        }
    }

    public void login(String username, String password) {
        Gson gson = new Gson();
        if (isThereUserWithThisUsername(username)) {
            UserAccount userAccount = getUserWithUsername(username);
            if (userAccount.getPassword().equals(password)) {
                if (userAccount.getType().equals("@Customer")) {
                    String user = gson.toJson((Customer) userAccount);
                    ServerController.getIncstance().sendMessageToClient("@Login as Customer@" + user);
                } else if (userAccount.getType().equals("@Seller")) {
                    String user = gson.toJson((Seller) userAccount);
                    ServerController.getIncstance().sendMessageToClient("@Login as Seller@" + user);
                } else if (userAccount.getType().equals("@Manager")) {
                    String user = gson.toJson((Manager) userAccount);
                    ServerController.getIncstance().sendMessageToClient("@Login as Manager@" + user);
                }
            } else {
                ServerController.getIncstance().sendMessageToClient("@Error@Password is incorrect");
            }
        } else {
            ServerController.getIncstance().sendMessageToClient("@Error@There is no User With this username");
        }
    }

    public boolean canAcceptSellerRegister(String username) {
        for (Seller seller : allSeller) {
            if (seller.getUsername().equals(username)) {
                seller.setAccepted(true);
                DataBase.getIncstance().updateAllSellers(new Gson().toJson(allSeller));
                return true;
            }
        }
        ServerController.getIncstance().sendMessageToClient("@Error@there is no seller with this username");
        return false;
    }
    public void removeCustomer(String username){
        for (Customer customer : allCustomer) {
            if(customer.getUsername().equals(username)){
                allCustomer.remove(customer);
                DataBase.getIncstance().updateAllCustomers(new Gson().toJson(allCustomer));
                ServerController.getIncstance().sendMessageToClient("@Successful@delete user successfully");
                return;
            }
        }
        ServerController.getIncstance().sendMessageToClient("@Error@there is no user with this username");
    }
    public void removeSeller(String username){
        for (Seller seller : allSeller) {
            if(seller.getUsername().equals(username)){
                allSeller.remove(seller);
                DataBase.getIncstance().updateAllSellers(new Gson().toJson(allSeller));
                ServerController.getIncstance().sendMessageToClient("@Successful@delete user successfully");
                return;
            }
        }
        ServerController.getIncstance().sendMessageToClient("@Error@there is no user with this username");
    }
    public void removeManager(String username){
        for (Manager manager : allManager) {
            if(manager.getUsername().equals(username)){
                allManager.remove(manager);
                DataBase.getIncstance().updateAllManagers(new Gson().toJson(allManager));
                ServerController.getIncstance().sendMessageToClient("@Successful@delete user successfully");
                return;
            }
        }
        ServerController.getIncstance().sendMessageToClient("@Error@there is no user with this username");
    }
    public void createManagerProfile(String json){
        Manager manager = new Gson().fromJson(json, Manager.class);
        if (!isThereUserWithThisUsername(manager.getUsername())) {
            allManager.add(manager);
            String arrayData = new Gson().toJson(allManager);
            DataBase.getIncstance().updateAllManagers(arrayData);
            ServerController.getIncstance().sendMessageToClient("@Successful@Register Successful");
        } else {
            ServerController.getIncstance().sendMessageToClient("@Error@There is a User With this username");
        }
    }
}
