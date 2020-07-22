package Controller.Client;

import Models.ChatMessage;
import Models.Log;
import Models.Product.Product;
import Models.UserAccount.*;
import View.ChatSupporterMenu;
import View.CustomerChatMenu;
import View.MessageKind;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class UserController {
    private static UserController managerController;
    private ArrayList<Seller> allSellers = new ArrayList<>();
    private ArrayList<Customer> allCustomers = new ArrayList<>();
    private ArrayList<Manager> allManagers = new ArrayList<>() ;
    private HashMap<String, ArrayList<ChatMessage>> chats = new HashMap<>();
    private HashMap<String,Integer> onlineUsers = new HashMap<>();
    private ArrayList<String> onlineClients = new ArrayList<>();
    private HashMap<String, Socket> customerDataStreams=new HashMap<>();
    private String currentChatUser;
    private ArrayList<Log> orders = new ArrayList<>();
    private int managerCount = 0;

    public HashMap<String, Socket> getCustomerDataStreams() {
        return customerDataStreams;
    }

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

    public void getCountOfManagerUsers() {
        ClientController.getInstance().sendMessageToServer("@getManagerCount@");
    }

    public void setOnlineUsers(String json) {
        Type userListType = new TypeToken<HashMap<String,Integer>>() {
        }.getType();
        this.onlineUsers =  new Gson().fromJson(json, userListType);
    }

    public ArrayList<String> getOnlineClients() {
        return onlineClients;
    }

    public void setOnlineClients(String json) {
        Type userListType = new TypeToken<ArrayList<String>>() {
        }.getType();
        this.onlineClients =  new Gson().fromJson(json, userListType);
    }
    public void setCurrentChatUser(String currentChatUser) {
        this.currentChatUser = currentChatUser;
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

    public HashMap<String, Integer> getOnlineUsers() {
        return onlineUsers;
    }

    public void getAllOnilneUSerFromServer() {
        ClientController.getInstance().sendMessageToServer("@getOnlineSupporter@");
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
        System.out.println("setting...");
        Type userListType = new TypeToken<ArrayList<Manager>>() {
        }.getType();
        System.out.println("qqqqq" + json);;
        ArrayList<Manager> managers = new Gson().fromJson(json, userListType);
        this.allManagers = managers;
        System.out.println(allManagers);

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

    public HashMap<String, ArrayList<ChatMessage>> getChats() {
        return chats;
    }

    public void createManagerProfile(Manager manager) {
        ClientController.getInstance().sendMessageToServer("@createManagerProfile@" + new Gson().toJson(manager));
    }

    public void createSupporterProfile(Supporter supporter) {
        ClientController.getInstance().sendMessageToServer("@createSupporterProfile@" + new Gson().toJson(supporter));
    }

    public void getChatMessage(String json) {
        System.out.println("get message  first");
        ChatMessage message = new Gson().fromJson(json, ChatMessage.class);
        if (chats.containsKey(message.getUsername())) {
            chats.get(message.getUsername()).add(message);
        } else {
            ArrayList<ChatMessage> chatMessageArrayList = new ArrayList<>();
            chatMessageArrayList.add(message);
            chats.put(message.getUsername(), chatMessageArrayList);
        }
        System.out.println("get message  second");
        if (ClientController.getInstance().getCurrentMenu() instanceof ChatSupporterMenu) {
            System.out.println("refreshing support");
            ((ChatSupporterMenu) ClientController.getInstance().getCurrentMenu()).setNewMessage();
        }
        if (ClientController.getInstance().getCurrentMenu() instanceof CustomerChatMenu) {
            System.out.println("refreshing customer");
            ((CustomerChatMenu) ClientController.getInstance().getCurrentMenu()).setNewMessage();
        }
    }

    public void sendChatMessage(String content) {
        ChatMessage message = new ChatMessage(ClientController.getInstance().getCurrentUser().getUsername(),currentChatUser,content);
        try {
            DataOutputStream dataOutputStream=new DataOutputStream(ClientController.getInstance().getCustomerSocket().getOutputStream());
            dataOutputStream.writeUTF(new Gson().toJson(message));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (chats.containsKey(currentChatUser)) {
            chats.get(currentChatUser).add(message);
        } else {
            ArrayList<ChatMessage> chatMessageArrayList = new ArrayList<>();
            chatMessageArrayList.add(message);
            chats.put(currentChatUser, chatMessageArrayList);
        }
        if (ClientController.getInstance().getCurrentMenu() instanceof CustomerChatMenu) {
            ((CustomerChatMenu) ClientController.getInstance().getCurrentMenu()).setNewMessage();
        }
        if (ClientController.getInstance().getCurrentMenu() instanceof ChatSupporterMenu)
            ((ChatSupporterMenu) ClientController.getInstance().getCurrentMenu()).setNewMessage();
    }

    public String getCurrentChatUser() {
        return currentChatUser;
    }

    public void setAllOrders(String json) {
        Type orderListType = new TypeToken<ArrayList<Log>>() {
        }.getType();
        this.orders =  new Gson().fromJson(json, orderListType);
    }

    public ArrayList<Log> getOrders() {
        return orders;
    }

    public void setManagersCount(int parseInt) {
        managerCount = parseInt;
    }

    public int getManagerCount() {
        return managerCount;
    }
}
