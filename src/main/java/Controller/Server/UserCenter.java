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
    private ArrayList<UserAccount> allUserAccount=new ArrayList<>();
    private boolean isThereAnyManager=false;
    private boolean isThisUserManager=false;
    private UserCenter(){

    }
    public static UserCenter getIncstance() {
        if (userCenter == null){
            userCenter = new UserCenter();
        }
        return userCenter;
    }
    public boolean isThereUserWithThisUsername(String username){
        for (UserAccount userAccount : allUserAccount) {
            if(userAccount.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public void setAllUserAccount(ArrayList<UserAccount> allUserAccount) {
        this.allUserAccount = allUserAccount;
    }

    public void createNewUserAccount(String json){
        Gson gson = new Gson();
        if (json.contains("@Customer")) {
            Customer customer=gson.fromJson(json, Customer.class);
            if(!isThereUserWithThisUsername(customer.getUsername())){
                allUserAccount.add(customer);
                String arrayData = gson.toJson(allUserAccount);
                DataBase.getIncstance().updateAllUsers(arrayData);
                ServerController.getIncstance().sendMessageToClient("@Successful@Register Successful");
            }else {
                ServerController.getIncstance().sendMessageToClient("@Error@There is a User With this username");
            }
        }else if (json.contains("@Seller")) {
            Seller seller=gson.fromJson(json, Seller.class);
            if(!isThereUserWithThisUsername(seller.getUsername())){
                allUserAccount.add(seller);
                String arrayData = gson.toJson(allUserAccount);
                DataBase.getIncstance().updateAllUsers(arrayData);
                Request request= RequestCenter.getIncstance().makeRequest("AcceptSellerAccount",gson.toJson(seller));
                RequestCenter.getIncstance().addRequest(request);
                ServerController.getIncstance().sendMessageToClient("@Successful@Register Successful");
            }else {
                ServerController.getIncstance().sendMessageToClient("@Error@There is a User With this username");
            }
        }else if (json.contains("@Manager")) {
            if(!isThereAnyManager||isThisUserManager) {
                Manager manager = gson.fromJson(json, Manager.class);
                if (!isThereUserWithThisUsername(manager.getUsername())) {
                    isThereAnyManager=true;
                    allUserAccount.add(manager);
                    String arrayData = gson.toJson(allUserAccount);
                    DataBase.getIncstance().updateAllUsers(arrayData);
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
