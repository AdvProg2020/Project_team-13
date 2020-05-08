package Controller.Client;

import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ManagerController {
    private static ManagerController managerController;
    private ArrayList<Seller> allSellers = new ArrayList<>();
    private ArrayList<Customer> allCustomers = new ArrayList<>();
    private ArrayList<Manager> allManagers = new ArrayList<>();

    private ManagerController() {
    }

    public static ManagerController getInstance() {
        if (managerController == null) {
            managerController = new ManagerController();
        }
        return managerController;
    }

    public void getAllUserFromServer() {
        ClientController.getInstance().sendMessageToServer("@getAllUsers@");
    }

    public void setAllSellers(String json) {
        Type userListType = new TypeToken<ArrayList<Seller>>() {
        }.getType();
        ArrayList<Seller> sellers = new Gson().fromJson(json, userListType);
        this.allSellers = sellers;
        printAllSellers();
    }

    public void setAllCustomers(String json) {
        Type userListType = new TypeToken<ArrayList<Customer>>() {
        }.getType();
        ArrayList<Customer> customers = new Gson().fromJson(json, userListType);
        this.allCustomers = customers;
        printAllCustomers();
    }

    public void setAllManagers(String json) {
        Type userListType = new TypeToken<ArrayList<Manager>>() {
        }.getType();
        ArrayList<Manager> managers = new Gson().fromJson(json, userListType);
        this.allManagers = managers;
        printAllManagers();
    }

    public void printAllSellers() {
        String showAllSellers = "";
        for (Seller seller : allSellers) {
            showAllSellers += seller.getUsername() + " " + seller.getType() + "\n";
        }
        ClientController.getInstance().getCurrentMenu().showMessage(showAllSellers);
    }

    public void printAllCustomers() {
        String showAllCustomers = "";
        for (Customer customer : allCustomers) {
            showAllCustomers += customer.getUsername() + " " + customer.getType() + "\n";
        }
        ClientController.getInstance().getCurrentMenu().showMessage(showAllCustomers);
    }

    public void printAllManagers() {
        String showAllManagers = "";
        for (Manager manager : allManagers) {
            showAllManagers += manager.getUsername() + " " + manager.getType() + "\n";
        }
        ClientController.getInstance().getCurrentMenu().showMessage(showAllManagers);
    }

    public void viewUser(String username) {
        String viewDetail = "";
        for (Customer customer : allCustomers) {
            if (customer.getUsername().equals(username)) {
                viewDetail = username + " " + customer.getType() + " " + customer.viewPersonalInfo();
                ClientController.getInstance().getCurrentMenu().showMessage(viewDetail);
                return;
            }
        }
        for (Seller seller : allSellers) {
            if (seller.getUsername().equals(username)) {
                viewDetail = username + " " + seller.getType() + " " + seller.viewPersonalInfo();
                ClientController.getInstance().getCurrentMenu().showMessage(viewDetail);
                return;
            }
        }
        for (Manager manager : allManagers) {
            if (manager.getUsername().equals(username)) {
                viewDetail = username + " " + manager.getType() + " " + manager.viewPersonalInfo();
                ClientController.getInstance().getCurrentMenu().showMessage(viewDetail);
                return;
            }
        }
        ClientController.getInstance().getCurrentMenu().printError("there is no user with this username");
    }
    public void deleteUser(String username) {
        if(ClientController.getInstance().getCurrentUser().getUsername().equals(username)){
            ClientController.getInstance().getCurrentMenu().printError("you cannot delete yourself");
            return;
        }
        for (Customer customer : allCustomers) {
            if (customer.getUsername().equals(username)) {
                ClientController.getInstance().sendMessageToServer("@deleteCustomer@"+username);
                allCustomers.remove(customer);
                return;
            }
        }
        for (Seller seller : allSellers) {
            if (seller.getUsername().equals(username)) {
                ClientController.getInstance().sendMessageToServer("@deleteSeller@"+username);
                allSellers.remove(seller);
                return;
            }
        }
        for (Manager manager : allManagers) {
            if (manager.getUsername().equals(username)) {
                ClientController.getInstance().sendMessageToServer("@deleteManager@"+username);
                allManagers.remove(manager);
                return;
            }
        }
        ClientController.getInstance().getCurrentMenu().printError("there is no user with this username");
    }
    public void createManagerProfile(Manager manager){
        ClientController.getInstance().sendMessageToServer("@createManagerProfile@"+new Gson().toJson(manager));
    }
}
