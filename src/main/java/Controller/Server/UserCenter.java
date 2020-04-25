package Controller.Server;

import Models.UserAccount.Customer;
import Models.UserAccount.UserAccount;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UserCenter {
    private static UserCenter userCenter;
    private ArrayList<UserAccount> allUserAccount=new ArrayList<>();
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
    public void createNewUserAccount(String json){
        if (json.contains("@Customer")) {
            Gson gson = new Gson();
            Customer customer=gson.fromJson(json, Customer.class);
            if(!isThereUserWithThisUsername(customer.getUsername())){
                allUserAccount.add(customer);
                String arrayData = gson.toJson(allUserAccount);
                DataBase.getIncstance().updateAllUsers(arrayData);
            }
        }
    }
}
