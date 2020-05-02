package Controller.Server;

import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import Models.UserAccount.UserAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataBase {
    private static DataBase dataBase;
    private String lastProductId;

    private DataBase() {

    }

    public static DataBase getIncstance() {
        if (dataBase == null) {
            dataBase = new DataBase();
        }
        return dataBase;
    }

    public void updateAllCustomers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allCustomers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateAllProducts(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allProducts.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            FileWriter fileWriter = new FileWriter("lastProductId.txt");
            fileWriter.write(lastProductId);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void updateAllSellers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allSellers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateAllManagers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allManagers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setAllUsersListFromDateBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("allCustomers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                Gson gson = new Gson();
                Type userListType = new TypeToken<ArrayList<Customer>>() {
                }.getType();
                ArrayList<Customer> allUsers = gson.fromJson(json, userListType);
                UserCenter.getIncstance().setAllCustomer(allUsers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileReader = new FileReader("allSellers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                Gson gson = new Gson();
                Type userListType = new TypeToken<ArrayList<Seller>>() {
                }.getType();
                ArrayList<Seller> allUsers = gson.fromJson(json, userListType);
                UserCenter.getIncstance().setAllSeller(allUsers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileReader = new FileReader("allManagers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                Gson gson = new Gson();
                Type userListType = new TypeToken<ArrayList<Manager>>() {
                }.getType();
                ArrayList<Manager> allUsers = gson.fromJson(json, userListType);
                UserCenter.getIncstance().setAllManager(allUsers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
