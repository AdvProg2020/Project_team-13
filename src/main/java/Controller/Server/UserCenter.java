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
    private ArrayList<Customer> allCustomer=new ArrayList<>();
    private ArrayList<Seller> allSeller=new ArrayList<>();
    private ArrayList<Manager> allManager=new ArrayList<>();
    private UserCenter(){

    }
    public static UserCenter getIncstance() {
        if (userCenter == null){
            userCenter = new UserCenter();
        }
        return userCenter;
    }
    public boolean isThereUserWithThisUsername(String username){
        for (Customer customer : allCustomer) {
            if(customer.getUsername().equals(username)){
                return true;
            }
        }
        for (Seller seller : allSeller) {
            if(seller.getUsername().equals(username)){
                return true;
            }
        }
        for (Manager manager : allManager) {
            if(manager.getUsername().equals(username)){
                return true;
            }
        }
        return false;
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

    public void createNewUserAccount(String json){
        Gson gson = new Gson();
        if (json.contains("@Customer")) {
            Customer customer=gson.fromJson(json, Customer.class);
            if(!isThereUserWithThisUsername(customer.getUsername())){
                allCustomer.add(customer);
                String arrayData = gson.toJson(allCustomer);
                DataBase.getIncstance().updateAllCustomers(arrayData);
                ServerController.getIncstance().sendMessageToClient("@Successful@Register Successful");
            }else {
                ServerController.getIncstance().sendMessageToClient("@Error@There is a User With this username");
            }
        }else if (json.contains("@Seller")) {
            Seller seller=gson.fromJson(json, Seller.class);
            if(!isThereUserWithThisUsername(seller.getUsername())){
                allSeller.add(seller);
                String arrayData = gson.toJson(allSeller);
                DataBase.getIncstance().updateAllSellers(arrayData);
                Request request= RequestCenter.getIncstance().makeRequest("AcceptSellerAccount",gson.toJson(seller));
                RequestCenter.getIncstance().addRequest(request);
                ServerController.getIncstance().sendMessageToClient("@Successful@Register was sended to Manager for review");
            }else {
                ServerController.getIncstance().sendMessageToClient("@Error@There is a User With this username");
            }
        }else if (json.contains("@Manager")) {
            if(allManager.size()==0) {
                Manager manager = gson.fromJson(json, Manager.class);
                if (!isThereUserWithThisUsername(manager.getUsername())) {
                    allManager.add(manager);
                    String arrayData = gson.toJson(allManager);
                    DataBase.getIncstance().updateAllManagers(arrayData);
                    ServerController.getIncstance().sendMessageToClient("@Successful@Register Successful");
                } else {
                    ServerController.getIncstance().sendMessageToClient("@Error@There is a User With this username");
                }
            }else{
                ServerController.getIncstance().sendMessageToClient("@Error@You can not Register as Manager");
            }
        }
    }
}
