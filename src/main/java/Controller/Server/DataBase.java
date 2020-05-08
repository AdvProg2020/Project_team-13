package Controller.Server;

import Models.Request;
import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import Models.UserAccount.UserAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

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
            FileWriter fileWriter = new FileWriter("lastProductId.txt.txt");
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

    public void setLastProductIdFromDataBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("lastProductId.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String lastProductId = br.readLine().trim();
            ProductCenter.getInstance().setLastProductId(lastProductId);
            br.close();
            fileReader.close();
        } catch (IOException e) {

        }
    }


    public void replaceProductId(String productId) {
        try {
            FileWriter fileWriter = new FileWriter("lastProductId.txt.txt");
            fileWriter.write(productId);
            fileWriter.close();
        } catch (IOException e) {

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
    public void updateAllRequests(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allRequests.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void setAllRequestsListFromDateBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("allRequests.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                Gson gson = new Gson();
                Type requestListType = new TypeToken<ArrayList<Request>>() {
                }.getType();
                ArrayList<Request> allRequests = gson.fromJson(json, requestListType);
                RequestCenter.getIncstance().setAllRequests(allRequests);
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
    public void replaceRequestId(String lastRequestId) {
        try {
            FileWriter fileWriter = new FileWriter("lastRequestId.txt");
            fileWriter.write(lastRequestId);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void setLastRequestId() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("lastRequestID.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String lastRequestId;
            while ((lastRequestId = br.readLine()) != null) {
                RequestCenter.getIncstance().setLastRequestID(lastRequestId);
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
    public void getAllUsersListFromDateBase(){
        FileReader fileReader = null;
        BufferedReader br ;
        try {
            fileReader = new FileReader("allCustomers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br= new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                ServerController.getIncstance().sendMessageToClient("@allCustomers@"+json);
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
        br= new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                ServerController.getIncstance().sendMessageToClient("@allSellers@"+json);
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
        br= new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                ServerController.getIncstance().sendMessageToClient("@allManagers@"+json);
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
    public void getAllProductsFromDataBase(){
        FileReader fileReader=null;
        Scanner scanner=null;
        try{
            fileReader=new FileReader("allProducts.txt");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        scanner=new Scanner(fileReader);
        try{
            String json=null;
            while(scanner.hasNextLine()){
                json=scanner.nextLine();
            }
            if(json!=null) {
                ServerController.getIncstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("@getAllProductsForManager@", json));
            }else{
                ServerController.getIncstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("@Error@", "There is no Product"));
            }
        } finally{
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }
}
