package Controller.Server;

import Models.*;
import Models.Product.Category;
import Models.Product.Product;
import Models.Product.ProductStatus;
import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import Models.UserAccount.Supporter;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DataBase {
    private static DataBase dataBase;
    private String lastProductId;

    private DataBase() {

    }

    public static DataBase getInstance() {
        if (dataBase == null) {
            synchronized (DataBase.class) {
                if (dataBase == null) {
                    dataBase = new DataBase();
                }
            }
        }
        return dataBase;
    }

    public synchronized void updateAllCustomers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allCustomers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void setWagePercent() {
        try {
            FileWriter fileWriter = new FileWriter("wage.txt");
            fileWriter.write(String.valueOf(CartCenter.getInstance().getWage()));
            fileWriter.close();
        } catch (Exception e) {
        }
    }
    public synchronized void setAtLeastCredit() {
        try {
            FileWriter fileWriter = new FileWriter("atLeastCredit.txt");
            fileWriter.write(String.valueOf(CartCenter.getInstance().getAtLeastAmount()));
            fileWriter.close();
        } catch (Exception e) {
        }
    }
    public synchronized void updateAllSellers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allSellers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void updateAllManagers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allManagers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void updateAllSupporter(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allSupporter.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void setLastAuctionIdFromDataBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("lastAuctionId.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String lastAuctionId = br.readLine().trim();
            AuctionCenter.getInstance().setLastAuctionId(lastAuctionId);
            br.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getWagePercent() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("wage.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String wage = br.readLine().trim();
            CartCenter.getInstance().setWage(Double.parseDouble(wage));
            br.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public synchronized void setLastProductIdFromDataBase() {
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
            System.out.println(e.getMessage());
        }
    }

    public synchronized void setAllCategoriesFormDataBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("allCategories.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String allCategoriesInGsonForm = br.readLine().trim();
            Gson gson = new Gson();
            ArrayList<Category> allCategories = new ArrayList<>();
            Type categoryListType = new TypeToken<ArrayList<Category>>() {
            }.getType();
            allCategories = gson.fromJson(allCategoriesInGsonForm, categoryListType);
            if (allCategories == null) {
                allCategories = new ArrayList<>();
            }
            int i = 0;
            for (Category category : allCategories) {
                if (category.getName().equals("File")) {
                    i++;
                    break;
                }
            }
            if (i == 0) {
                HashMap<String, ArrayList<String>> features = new HashMap<>();
                ArrayList<String> modes = new ArrayList<>();
                modes.add("TextFile");
                modes.add("ImageFile");
                modes.add("VideoFile");
                modes.add("Other");
                features.put("Format", modes);
                allCategories.add(new Category("File", features));
            }
            CategoryCenter.getIncstance().setAllCategories(allCategories, true);
            br.close();
            fileReader.close();
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void updateAllCategories(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allCategories.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void setAllProductsFormDataBase() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM allProducts");
            int column = resultSet.getMetaData().getColumnCount();
            ArrayList<Product> allProducts = new ArrayList<>();
            while (resultSet.next()) {
                String product = "";
                for (int i = 1; i <= column; i++) {
                    product += resultSet.getObject(i) + "&&";
                }
                String[] data = product.split("&&");
                Type features = new TypeToken<HashMap<String, String>>() {
                }.getType();
                Product product1 = new Product(data[3], data[0], data[2], new Gson().fromJson(data[4], Seller.class), Double.parseDouble(data[6]), data[8], data[9],
                        Integer.parseInt(data[11]), new Gson().fromJson(data[12], features));
                if(!data[1].equals("null")){
                  product1.setProductStatus(ProductStatus.valueOf(data[1]));
                }
                if(!data[5].equals("null")){
                  Type scoreType = new TypeToken<ArrayList<Score>>(){
                  }.getType();
                  product1.setAllScores(new Gson().fromJson(data[5], scoreType));
                }
                if(!data[6].equals("null")){
                  product1.setProductCost(Double.parseDouble(data[6]));
                }
                if(!data[7].equals("null")){
                  product1.setCostAfterOff(Double.parseDouble(data[7]));
                }
                if(!data[11].equals("null")){
                    Type commentType = new TypeToken<ArrayList<Comment>>(){
                    }.getType();
                  product1.setCommentList(new Gson().fromJson(data[11], commentType));
                }
                if(!data[14].equals("null")){
                    Type customer = new TypeToken<ArrayList<Customer>>(){
                    }.getType();
                    product1.setAllBuyers(new Gson().fromJson(data[14], customer));
                }
                if(!data[15].equals("null")){
                   Type offerType = new TypeToken<ArrayList<Offer>>(){
                   }.getType();
                   product1.setOffers(new Gson().fromJson(data[15], offerType));
                }
                if(!data[16].equals("null")){
                   product1.setImagePath(data[16]);
                }
                if(!data[17].equals("null")){
                   product1.setVideoPath(data[17]);
                }
                if(!data[18].equals("null")){
                  product1.setFilePath(data[18]);
                }
                allProducts.add(product1);
            }
            ProductCenter.getInstance().setAllProducts(allProducts);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public synchronized void updateAllProducts(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allProducts.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void replaceProductId(String productId) {
        try {
            FileWriter fileWriter = new FileWriter("lastProductId.txt");
            fileWriter.write(productId);
            fileWriter.close();
        } catch (IOException e) {
        }
    }


    public synchronized void replaceAuctionId(String auctionId) {
        try {
            FileWriter fileWriter = new FileWriter("lastAuctionId.txt");
            fileWriter.write(auctionId);
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public synchronized void replaceLogId(String logId) {
        try {
            FileWriter fileWriter = new FileWriter("lastLogId.txt");
            fileWriter.write(logId);
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public void setAllUsersListFromDateBase() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            final String url = "jdbc:ucanaccess://ProjectDatabase.accdb";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM allCustomers");
            int column = resultSet.getMetaData().getColumnCount();
            ArrayList<Customer> allCustomers = new ArrayList<>();
            while (resultSet.next()) {
                String customer = "";
                for (int i = 1; i <= column; i++) {
                    customer += resultSet.getObject(i) + "&&";
                }
                String[] data = customer.split("&&");
                Customer customer1 = new Customer(data[0], data[1], data[2], data[3], data[4], data[5], data[7].equals("null") ? 0 : Double.parseDouble(data[7]));
                if(!data[8].equals("null")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    customer1.setAllDiscountCodes(new Gson().fromJson(data[8], discountType));
                }
                if(!data[9].equals("null")){
                    Type logType = new TypeToken<ArrayList<Log>>(){
                    }.getType();
                    customer1.setHistoryOfTransaction(new Gson().fromJson(data[9], logType));
                }
                if(!data[10].equals("null")){
                    customer1.setTotalBuyAmount(Double.parseDouble(data[10]));
                }
                allCustomers.add(customer1);
            }
            UserCenter.getIncstance().setAllCustomer(allCustomers);
            //
            //
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM allSellers");
            int column1 = resultSet1.getMetaData().getColumnCount();
            ArrayList<Seller> allSeller = new ArrayList<>();
            while (resultSet1.next()) {
                String seller = "";
                for (int i = 1; i <= column1; i++) {
                    seller += resultSet1.getObject(i) + "&&";
                }
                String[] data1 = seller.split("&&");
                Seller seller1 = new Seller(data1[0], data1[1], data1[2], data1[3], data1[4], data1[5], data1[7].equals("null") ? 0 : Double.parseDouble(data1[7]), data1[8], data1[9].equals("true"));
                if(!data1[10].equals("null")){
                  Type allProducts = new TypeToken<ArrayList<Product>>(){
                  }.getType();
                  seller1.setAllProducts(new Gson().fromJson(data1[10], allProducts));
                }
                if(!data1[11].equals("null")){
                    Type allOffers = new TypeToken<ArrayList<Offer>>(){
                    }.getType();
                    seller1.setAllOffer(new Gson().fromJson(data1[11], allOffers));
                }
                if(!data1[12].equals("null")){
                    Type allRequest = new TypeToken<ArrayList<Request>>(){
                    }.getType();
                    seller1.setAllRequests(new Gson().fromJson(data1[12], allRequest));
                }
                if(!data1[13].equals("null")){
                    seller1.setCommercializedProduct(data1[13]);
                }
                if(!data1[14].equals("null")){
                    seller1.setAuction(new Gson().fromJson(data1[14], Auction.class));
                }
                allSeller.add(seller1);
            }
            UserCenter.getIncstance().setAllSeller(allSeller);
            //
            //
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM allManagers");
            int column2 = resultSet2.getMetaData().getColumnCount();
            ArrayList<Manager> allManagers = new ArrayList<>();
            while (resultSet2.next()) {
                String manager = "";
                for (int i = 1; i <= column2; i++) {
                    manager += resultSet2.getObject(i) + "&&";
                }
                String[] data2 = manager.split("&&");
                Manager manager1 = new Manager(data2[0], data2[1], data2[2], data2[3], data2[4], data2[5], data2[7].equals("null") ? 0 : Double.parseDouble(data2[7]));
                if(!data2[8].equals("null")){
                    Type discountType = new TypeToken<ArrayList<DiscountCode>>(){
                    }.getType();
                    manager1.setAllDiscountCodes(new Gson().fromJson(data2[8], discountType));
                }
                if(!data2[9].equals("null")){
                    Type logType = new TypeToken<ArrayList<Log>>(){
                    }.getType();
                    manager1.setHistoryOfTransaction(new Gson().fromJson(data2[9], logType));
                }
                allManagers.add(manager1);
            }
            UserCenter.getIncstance().setAllManager(allManagers);
            resultSet.close();
            resultSet1.close();
            resultSet2.close();
            statement.close();
            statement1.close();
            statement2.close();
            connection.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    public synchronized void updateAllRequests(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allRequests.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void updateAllDiscountCode(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allDiscountCodes.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void setAllRequestsListFromDateBase() {
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

    public synchronized void setAllDiscountCodesListFromDateBase() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("allDiscountCodes.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String json;
            while ((json = br.readLine()) != null) {
                Gson gson = new Gson();
                Type discountCodeListType = new TypeToken<ArrayList<DiscountCode>>() {
                }.getType();
                ArrayList<DiscountCode> allDiscountCodes = gson.fromJson(json, discountCodeListType);
                DiscountCodeCenter.getIncstance().setAllDiscountCodes(allDiscountCodes);
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

    public synchronized void replaceRequestId(String lastRequestId) {
        try {
            FileWriter fileWriter = new FileWriter("lastRequestId.txt");
            fileWriter.write(lastRequestId);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void replaceDiscountCodeId(String lastDiscountCodeId) {
        try {
            FileWriter fileWriter = new FileWriter("lastDiscountCodeId.txt");
            fileWriter.write(lastDiscountCodeId);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void setLastRequestId() {
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

    public synchronized void setLastLogId() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("lastLogID.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String lastLogId;
            while ((lastLogId = br.readLine()) != null) {
                CartCenter.getInstance().setLastLogId(lastLogId);
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

    public synchronized void setLastDiscountCodeId() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("lastDiscountCodeID.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String lastDiscountCode;
            while ((lastDiscountCode = br.readLine()) != null) {
                DiscountCodeCenter.getIncstance().setLastDiscountCodeID(lastDiscountCode);
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

    public void getAllUsersListFromDateBase(DataOutputStream dataOutputStream) {
        System.out.println("yes");
        FileReader fileReader = null;
        BufferedReader br;
        String allJson = "";
        try {
            fileReader = new FileReader("allCustomers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br = new BufferedReader(fileReader);
        try {
            String json;
            int i = 0;
            while ((json = br.readLine()) != null) {
                i++;
                System.out.println("11111");
                if (!json.isEmpty()) {
                    allJson += json + "&";
                } else {
                    allJson += "[]" + "&";
                }
            }
            if (i == 0) {
                allJson += "[]" + "&";
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
            int i = 0;
            while ((json = br.readLine()) != null) {
                i++;
                System.out.println("22222");

                if (!json.isEmpty()) {
                    allJson += json + "&";
                } else {
                    allJson += "[]" + "&";
                }
            }
            if (i == 0) {
                allJson += "[]" + "&";
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
            int i = 0;
            while ((json = br.readLine()) != null) {
                i++;
                System.out.println("33333");
                if (!json.isEmpty()) {
                    allJson += json;
                } else {
                    allJson += "[]";
                }
            }
            if (i == 0) {
                allJson += "[]";
            }
            ServerController.getInstance().sendMessageToClient("@allUsers@" + allJson, dataOutputStream);
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

    public void getAllOffersFromDataBase(DataOutputStream dataOutputStream) {
        FileReader fileReader = null;
        Scanner scanner;
        try {
            fileReader = new FileReader("allOffers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(fileReader);
        try {
            String json = null;
            while (scanner.hasNextLine()) {
                json = scanner.nextLine();
            }
            if (json != null) {
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("getAllOffers", json), dataOutputStream);
            } else {
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Error", "There is no Product"), dataOutputStream);
            }
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }

    public void getAllProductsFromDataBase(DataOutputStream dataOutputStream) {
        FileReader fileReader = null;
        Scanner scanner;
        try {
            fileReader = new FileReader("allProducts.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(fileReader);
        try {
            String json = null;
            while (scanner.hasNextLine()) {
                json = scanner.nextLine();
            }
            if (json != null) {
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("getAllProductsForManager", json), dataOutputStream);
            } else {
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Error", "There is no Product"), dataOutputStream);
            }
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }

    public synchronized void setLastOfferIdFromDataBase() {
        FileReader fileReader = null;
        Scanner scanner = null;
        try {
            fileReader = new FileReader("lastOfferId.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            scanner = new Scanner(fileReader);
        } catch (NullPointerException e) {
            e.getCause();
        }
        try {
            String lastOfferId;
            while (scanner.hasNextLine()) {
                lastOfferId = scanner.nextLine();
                OffCenter.getInstance().setLastOffId(lastOfferId);
            }
        } catch (NullPointerException nullPointer) {
            nullPointer.getCause();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException error) {
                error.getCause();
            }
            try {
                scanner.close();
            } catch (NullPointerException error2) {
                error2.getCause();
            }
        }

    }

    public synchronized void replaceOfferId(String lastOfferId) {
        try {
            FileWriter fileWriter = new FileWriter("lastOfferId.txt");
            fileWriter.write(lastOfferId);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public synchronized void updateAllOffers(String json) {
        try {
            FileWriter fileWriter = new FileWriter("allOffers.txt");
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void setAllOffersFromDatabase() {
        FileReader fileReader = null;
        Scanner scanner = null;
        try {
            fileReader = new FileReader("allOffers.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            scanner = new Scanner(fileReader);
        } catch (NullPointerException nullPointer) {
            nullPointer.getCause();
        }
        try {
            String json;
            while (scanner.hasNextLine()) {
                String read = scanner.nextLine();
                Gson gson = new Gson();
                Type allOffersList = new TypeToken<ArrayList<Offer>>() {
                }.getType();
                ArrayList<Offer> allOffers = gson.fromJson(read, allOffersList);
                OffCenter.getInstance().setAllOffers(allOffers);
            }
        } catch (NullPointerException e) {
            e.getCause();
        } finally {
            try {
                fileReader.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
            try {
                scanner.close();
            } catch (NullPointerException e) {
                e.getCause();
            }
        }
    }
}
