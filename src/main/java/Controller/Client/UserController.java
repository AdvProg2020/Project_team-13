package Controller.Client;

import Models.Product.Product;
import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import View.MessageKind;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserController {
    private static UserController managerController;
    private ArrayList<Seller> allSellers = new ArrayList<>();
    private ArrayList<Customer> allCustomers = new ArrayList<>();
    private ArrayList<Manager> allManagers = new ArrayList<>();

    private UserController() {
    }

    public static UserController getInstance() {
        if (managerController == null) {
            managerController = new UserController();
        }
        return managerController;
    }

    public void getAllUserFromServer() {
        ClientController.getInstance().sendMessageToServer("@getAllUsers@");
    }

    public ArrayList<String> getAllCommercializedProducts() {
        ArrayList<String> commercializedProduct = new ArrayList<>();
        for (Seller seller : getAllSellers()) {
            if (seller.getCommercializedProduct() != null && !seller.getCommercializedProduct().isEmpty()) {
                commercializedProduct.add(seller.getCommercializedProduct());
            }
        }
        return commercializedProduct;
    }

    public void reduceSellerCreditForAnAdd(Product product) {
        for (Seller seller : allSellers) {
            if (seller.getUsername().equals(product.getSeller())) {
                seller.setCredit(seller.getCredit() - 50);
            }
        }
    }

    public ArrayList<Seller> getAllSellers() {
        return allSellers;
    }

    public ArrayList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public ArrayList<Manager> getAllManagers() {
        return allManagers;
    }

    public void setAllSellers(String json) {
        Type userListType = new TypeToken<ArrayList<Seller>>() {
        }.getType();
        ArrayList<Seller> sellers = new Gson().fromJson(json, userListType);
        this.allSellers = sellers;

    }

    public void setAllCustomers(String json) {
        Type userListType = new TypeToken<ArrayList<Customer>>() {
        }.getType();
        ArrayList<Customer> customers = new Gson().fromJson(json, userListType);
        this.allCustomers = customers;

    }

    public void setAllManagers(String json) {
        Type userListType = new TypeToken<ArrayList<Manager>>() {
        }.getType();
        ArrayList<Manager> managers = new Gson().fromJson(json, userListType);
        this.allManagers = managers;

    }

    public void printAllSellers() {
        String showAllSellers = "";
        for (Seller seller : allSellers) {
            showAllSellers += seller.getUsername() + " " + seller.getType() + "\n";
        }
        //  ClientController.getInstance().getCurrentMenu().showMessage(showAllSellers);
    }

    public void printAllCustomers() {
        String showAllCustomers = "";
        for (Customer customer : allCustomers) {
            showAllCustomers += customer.getUsername() + " " + customer.getType() + "\n";
        }
        //   ClientController.getInstance().getCurrentMenu().showMessage(showAllCustomers);
    }

    public void printAllManagers() {
        String showAllManagers = "";
        for (Manager manager : allManagers) {
            showAllManagers += manager.getUsername() + " " + manager.getType() + "\n";
        }
        //   ClientController.getInstance().getCurrentMenu().showMessage(showAllManagers);
    }

    public void viewUser(String username) {
        String viewDetail = "";
        for (Customer customer : allCustomers) {
            if (customer.getUsername().equals(username)) {
                viewDetail = username + " " + customer.getType() + " " + customer.viewPersonalInfo();
                //        ClientController.getInstance().getCurrentMenu().showMessage(viewDetail);
                return;
            }
        }
        for (Seller seller : allSellers) {
            if (seller.getUsername().equals(username)) {
                viewDetail = username + " " + seller.getType() + " " + seller.viewPersonalInfo();
                //    ClientController.getInstance().getCurrentMenu().showMessage(viewDetail);
                return;
            }
        }
        for (Manager manager : allManagers) {
            if (manager.getUsername().equals(username)) {
                viewDetail = username + " " + manager.getType() + " " + manager.viewPersonalInfo();
                //     ClientController.getInstance().getCurrentMenu().showMessage(viewDetail);
                return;
            }
        }
        ClientController.getInstance().getCurrentMenu().showMessage("there is no user with this username", MessageKind.ErrorWithoutBack);
    }

    public void deleteUser(String username) {
        if (ClientController.getInstance().getCurrentUser().getUsername().equals(username)) {
            ClientController.getInstance().getCurrentMenu().showMessage("you can't delete yourself", MessageKind.ErrorWithoutBack);
            return;
        }
        for (Customer customer : allCustomers) {
            if (customer.getUsername().equals(username)) {
                ClientController.getInstance().sendMessageToServer("@deleteCustomer@" + username);
                allCustomers.remove(customer);
                return;
            }
        }
        for (Seller seller : allSellers) {
            if (seller.getUsername().equals(username)) {
                ClientController.getInstance().sendMessageToServer("@deleteSeller@" + username);
                allSellers.remove(seller);
                return;
            }
        }
        for (Manager manager : allManagers) {
            if (manager.getUsername().equals(username)) {
                ClientController.getInstance().sendMessageToServer("@deleteManager@" + username);
                allManagers.remove(manager);
                return;
            }
        }
        ClientController.getInstance().getCurrentMenu().showMessage("there is no user with this user", MessageKind.ErrorWithoutBack);
    }


    public boolean isThereCustomerWithThisUsername(String username) {
        for (Customer customer : allCustomers) {
            if (customer.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void createManagerProfile(Manager manager) {
        ClientController.getInstance().sendMessageToServer("@createManagerProfile@" + new Gson().toJson(manager));
    }
}